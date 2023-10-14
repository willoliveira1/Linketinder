package com.linketinder.dao.companydao

import com.linketinder.database.DatabaseFactory
import com.linketinder.database.DBService
import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.LocationType
import com.linketinder.model.shared.Skill
import com.linketinder.util.ErrorMessages
import com.linketinder.util.NotFoundMessages
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class JobVacancyDAO {

    private final String QUERY_GET_ALL_JOB_VACANCIES = "SELECT jv.id, jv.title, jv.description, jv.salary, ct.title AS contract_type, lt.title AS location_type FROM job_vacancies AS jv, companies AS c, contract_types AS ct, location_types AS lt WHERE jv.company_id= c.id AND jv.contract_type_id = ct.id AND jv.location_type_id = lt.id"
    private final String QUERY_GET_JOB_VACANCY_BY_COMPANY_ID = "SELECT jv.id, jv.title, jv.description, jv.salary, ct.title AS contract_type, lt.title AS location_type FROM job_vacancies AS jv, companies AS c, contract_types AS ct, location_types AS lt WHERE jv.company_id= c.id AND jv.contract_type_id = ct.id AND jv.location_type_id = lt.id AND c.id=?"
    private final String QUERY_GET_JOB_VACANCY_BY_ID = "SELECT jv.id, jv.title, jv.description, jv.salary, ct.title AS contract_type, lt.title AS location_type FROM job_vacancies AS jv, companies AS c, contract_types AS ct, location_types AS lt WHERE jv.company_id= c.id AND jv.contract_type_id = ct.id AND jv.location_type_id = lt.id AND jv.id=?"
    private final String QUERY_GET_SKILL_BY_JOB_VACANCY_ID = "SELECT id FROM job_vacancy_skills WHERE job_vacancy_id=?"
    private final String QUERY_GET_COMPANY_ID = "SELECT company_id FROM job_vacancies WHERE id=?"
    private final String INSERT_JOB_VACANCY = "INSERT INTO job_vacancies (company_id, title, description, salary, contract_type_id, location_type_id) VALUES (?,?,?,?,?,?)"
    private final String UPDATE_JOB_VACANCY = "UPDATE job_vacancies SET company_id=?, title=?, description=?, salary=?, contract_type_id=?, location_type_id=? WHERE id=?"
    private final String DELETE_JOB_VACANCY = "DELETE FROM job_vacancies WHERE id=?"

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()
    RequiredSkillDAO requiredSkillDAO = new RequiredSkillDAO()

    private List<JobVacancy> populateJobVacancies(String query, Integer... args) {
        List<JobVacancy> jobVacancies = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        if (args.any()) {
            stmt.setInt(1, args[0])
        }
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            JobVacancy jobVacancy = new JobVacancy()
            jobVacancy.setId(result.getInt("id"))
            jobVacancy.setTitle(result.getString("title"))
            jobVacancy.setDescription(result.getString("description"))
            jobVacancy.setSalary(result.getDouble("salary"))
            jobVacancy.setContractType(ContractType.valueOf(result.getString("contract_type")))
            jobVacancy.setLocationType(LocationType.valueOf(result.getString("location_type")))
            jobVacancy.setRequiredSkills(requiredSkillDAO.getSkillsByJobVacancyId(jobVacancy.id))
            jobVacancies.add(jobVacancy)
        }
        return jobVacancies
    }

    List<JobVacancy> getAllJobVacancies() {
        List<JobVacancy> jobVacancies = new ArrayList<>()
        try {
            jobVacancies = populateJobVacancies(QUERY_GET_ALL_JOB_VACANCIES)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
        return jobVacancies
    }

    List<JobVacancy> getJobVacancyByCompanyId(int companyId) {
        List<JobVacancy> jobVacancies = new ArrayList<>()
        try {
            jobVacancies = populateJobVacancies(QUERY_GET_JOB_VACANCY_BY_COMPANY_ID, companyId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
        return jobVacancies
    }

    private JobVacancy populateJobVacancy(String query, int id) {
        JobVacancy jobVacancy = new JobVacancy()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            jobVacancy.setId(result.getInt("id"))
            jobVacancy.setTitle(result.getString("title"))
            jobVacancy.setDescription(result.getString("description"))
            jobVacancy.setSalary(result.getDouble("salary"))
            jobVacancy.setContractType(ContractType.valueOf(result.getString("contract_type")))
            jobVacancy.setLocationType(LocationType.valueOf(result.getString("location_type")))
            jobVacancy.setRequiredSkills(requiredSkillDAO.getSkillsByJobVacancyId(jobVacancy.id))
        }
        return jobVacancy
    }

    JobVacancy getJobVacancyById(int id) {
        JobVacancy jobVacancy = new JobVacancy()
        try {
            jobVacancy = populateJobVacancy(QUERY_GET_JOB_VACANCY_BY_ID, id)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
        return jobVacancy
    }

    private void insertRequiredSkills(JobVacancy jobVacancy) {
        for (Skill requiredSkill in jobVacancy.requiredSkills) {
            requiredSkillDAO.insertSkill(requiredSkill, jobVacancy.id)
        }
    }

    void insertJobVacancy(int companyId, JobVacancy jobVacancy) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_JOB_VACANCY, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, companyId)
            stmt.setString(2, jobVacancy.title)
            stmt.setString(3, jobVacancy.description)
            stmt.setDouble(4, jobVacancy.salary)

            int contractTypeId = dbService.idFinder("contract_types", "title",
                    jobVacancy.getContractType().toString())
            stmt.setInt(5, contractTypeId)

            int locationTypeId = dbService.idFinder("location_types", "title",
                    jobVacancy.getLocationType().toString())
            stmt.setInt(6, locationTypeId)

            stmt.executeUpdate()

            ResultSet getJobVacancyId = stmt.getGeneratedKeys()
            while (stmt.getGeneratedKeys().next()) {
                jobVacancy.id = getJobVacancyId.getInt(1)
            }

            insertRequiredSkills(jobVacancy)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
    }

    private void updateRequiredSkills(JobVacancy jobVacancy) {
        List<Integer> skillsId = new ArrayList<>()
        sql.eachRow(QUERY_GET_SKILL_BY_JOB_VACANCY_ID, [jobVacancy.id]) {
            row -> skillsId << row.getInt("id")
        }

        List<Integer> persistedSkills = skillsId.findAll { !jobVacancy.requiredSkills.contains(it) }
        persistedSkills.each {skillId ->
            requiredSkillDAO.deleteSkill(skillId)
        }

        jobVacancy.requiredSkills.each {skill ->
            if (persistedSkills.contains(skill.id)) {
                requiredSkillDAO.updateSkill(skill, jobVacancy.id)
            } else {
                requiredSkillDAO.insertSkill(skill, jobVacancy.id)
            }
        }
    }

    private int getCompanyId(JobVacancy jobVacancy) {
        PreparedStatement stmt = sql.connection.prepareStatement(QUERY_GET_COMPANY_ID)
        stmt.setInt(1, jobVacancy.id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            return result.getInt("company_id")
        }
    }

    void updateJobVacancy(JobVacancy jobVacancy) {
        int companyId = getCompanyId(jobVacancy)
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_JOB_VACANCY)
            stmt.setInt(1, companyId)
            stmt.setString(2, jobVacancy.title)
            stmt.setString(3, jobVacancy.description)
            stmt.setDouble(4, jobVacancy.salary)

            int contractTypeId = dbService.idFinder("contract_types", "title",
                    jobVacancy.getContractType().toString())
            stmt.setInt(5, contractTypeId)

            int locationTypeId = dbService.idFinder("location_types", "title",
                    jobVacancy.getLocationType().toString())
            stmt.setInt(6, locationTypeId)
            stmt.setInt(7, jobVacancy.id)

            stmt.executeUpdate()

            updateRequiredSkills(jobVacancy)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
    }

    void deleteJobVacancy(int id) {
        JobVacancy jobVacancy = new JobVacancy()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(QUERY_GET_JOB_VACANCY_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                jobVacancy.setId(result.getInt("id"))
            }

            if (jobVacancy.id != null) {
                stmt = sql.connection.prepareStatement(DELETE_JOB_VACANCY)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
        println NotFoundMessages.JOB_VACANCY
    }

}
