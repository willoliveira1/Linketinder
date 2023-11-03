package com.linketinder.dao.companydao

import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.dao.companydao.queries.JobVacancyQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.*
import com.linketinder.model.jobvacancy.*
import com.linketinder.model.shared.Skill
import com.linketinder.util.*
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class JobVacancyDAO implements IJobVacancyDAO {

    IConnection connection
    IRequiredSkillDAO requiredSkillDAO
    Sql sql = connection.instance()

    JobVacancyDAO(IConnection connection, IRequiredSkillDAO requiredSkillDAO) {
        this.connection = connection
        this.requiredSkillDAO = requiredSkillDAO
    }

    private JobVacancy createJobVacancy(ResultSet result) {
        JobVacancy jobVacancy = new JobVacancy()
        jobVacancy.setId(result.getInt("id"))
        jobVacancy.setTitle(result.getString("title"))
        jobVacancy.setDescription(result.getString("description"))
        jobVacancy.setSalary(result.getDouble("salary"))
        jobVacancy.setContractType(ContractType.valueOf(result.getString("contract_type")))
        jobVacancy.setLocationType(LocationType.valueOf(result.getString("location_type")))
        jobVacancy.setRequiredSkills(requiredSkillDAO.getSkillsByJobVacancyId(jobVacancy.id))
        return jobVacancy
    }

    private List<JobVacancy> populateJobVacancies(String query, Integer... args) {
        List<JobVacancy> jobVacancies = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        if (args.any()) {
            stmt.setInt(1, args[0])
        }
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            JobVacancy jobVacancy = this.createJobVacancy(result)
            jobVacancies.add(jobVacancy)
        }
        return jobVacancies
    }

    private JobVacancy populateJobVacancy(String query, int id) {
        JobVacancy jobVacancy = new JobVacancy()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            jobVacancy = this.createJobVacancy(result)
        }
        return jobVacancy
    }

    List<JobVacancy> getAllJobVacancies() {
        List<JobVacancy> jobVacancies = new ArrayList<>()
        try {
            jobVacancies = this.populateJobVacancies(JobVacancyQueries.GET_ALL_JOB_VACANCIES)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
        return jobVacancies
    }

    List<JobVacancy> getJobVacancyByCompanyId(int companyId) {
        List<JobVacancy> jobVacancies = new ArrayList<>()
        try {
            jobVacancies = this.populateJobVacancies(JobVacancyQueries.GET_JOB_VACANCY_BY_COMPANY_ID, companyId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
        return jobVacancies
    }

    JobVacancy getJobVacancyById(int id) {
        JobVacancy jobVacancy = new JobVacancy()
        try {
            jobVacancy = this.populateJobVacancy(JobVacancyQueries.GET_JOB_VACANCY_BY_ID, id)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
        return jobVacancy
    }

    private void insertRequiredSkills(JobVacancy jobVacancy) {
        for (Skill requiredSkill in jobVacancy.requiredSkills) {
            requiredSkillDAO.insertSkill(requiredSkill, jobVacancy.id)
        }
    }

    private int getContractTypeIdByTitle(String contractTypeTitle) {
        PreparedStatement stmt = sql.connection.prepareStatement(JobVacancyQueries.GET_CONTRACT_TYPE_ID_BY_TITLE)
        stmt.setString(1, contractTypeTitle)
        return QueryHelper.idFinder(stmt)
    }

    private int getLocationTypeIdByTitle(String locationTypeTitle) {
        PreparedStatement stmt = sql.connection.prepareStatement(JobVacancyQueries.GET_LOCATION_TYPE_ID_BY_TITLE)
        stmt.setString(1, locationTypeTitle)
        return QueryHelper.idFinder(stmt)
    }

    private PreparedStatement setJobVancancyStatement(PreparedStatement stmt, JobVacancy jobVacancy, int companyId ) {
        int contractTypeId = this.getContractTypeIdByTitle(jobVacancy.getContractType().toString())
        int locationTypeId = this.getLocationTypeIdByTitle(jobVacancy.getLocationType().toString())
        stmt.setInt(1, companyId)
        stmt.setString(2, jobVacancy.title)
        stmt.setString(3, jobVacancy.description)
        stmt.setDouble(4, jobVacancy.salary)
        stmt.setInt(5, contractTypeId)
        stmt.setInt(6, locationTypeId)
        return stmt
    }

    void insertJobVacancy(int companyId, JobVacancy jobVacancy) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(JobVacancyQueries.INSERT_JOB_VACANCY,
                    Statement.RETURN_GENERATED_KEYS)
            stmt = this.setJobVancancyStatement(stmt, jobVacancy, companyId)
            stmt.executeUpdate()

            ResultSet getJobVacancyId = stmt.getGeneratedKeys()
            while (stmt.getGeneratedKeys().next()) {
                jobVacancy.id = getJobVacancyId.getInt(1)
            }

            this.insertRequiredSkills(jobVacancy)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
    }

    private void updateRequiredSkills(JobVacancy jobVacancy) {
        List<Integer> skillsId = new ArrayList<>()
        sql.eachRow(JobVacancyQueries.GET_SKILL_BY_JOB_VACANCY_ID, [jobVacancy.id]) {
            row -> skillsId << row.getInt("id")
        }

        List<Integer> persistedSkills = skillsId.findAll { !jobVacancy.requiredSkills.contains(it) }
        persistedSkills.each {skillId ->
            requiredSkillDAO.deleteSkill(skillId)
        }

        jobVacancy.requiredSkills.each {skill ->
            if (persistedSkills.contains(skill.id)) {
                requiredSkillDAO.updateSkill(skill as Skill, jobVacancy.id)
            } else {
                requiredSkillDAO.insertSkill(skill as Skill, jobVacancy.id)
            }
        }
    }

    private int getCompanyId(JobVacancy jobVacancy) {
        PreparedStatement stmt = sql.connection.prepareStatement(JobVacancyQueries.GET_COMPANY_ID)
        stmt.setInt(1, jobVacancy.id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            return result.getInt("company_id")
        }
    }

    void updateJobVacancy(int id, JobVacancy jobVacancy) {
        jobVacancy.id = id
        int companyId = getCompanyId(jobVacancy)
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(JobVacancyQueries.UPDATE_JOB_VACANCY)
            stmt = this.setJobVancancyStatement(stmt, jobVacancy, companyId)
            stmt.setInt(7, jobVacancy.id)
            stmt.executeUpdate()

            this.updateRequiredSkills(jobVacancy)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
    }

    void deleteJobVacancy(int id) {
        JobVacancy jobVacancy = new JobVacancy()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(JobVacancyQueries.GET_JOB_VACANCY_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                jobVacancy.setId(result.getInt("id"))
            }

            if (jobVacancy.id != null) {
                stmt = sql.connection.prepareStatement(JobVacancyQueries.DELETE_JOB_VACANCY)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
        println NotFoundMessages.JOB_VACANCY
    }

}
