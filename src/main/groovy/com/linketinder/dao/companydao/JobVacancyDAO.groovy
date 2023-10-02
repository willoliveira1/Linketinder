package com.linketinder.dao.companydao


import com.linketinder.database.DBService
import com.linketinder.database.DatabaseFactory
import com.linketinder.domain.jobvacancy.JobVacancy
import com.linketinder.domain.jobvacancy.ContractType
import com.linketinder.domain.jobvacancy.LocationType
import com.linketinder.domain.shared.Skill

import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class JobVacancyDAO {

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()
    RequiredSkillDAO requiredSkillDAO = new RequiredSkillDAO()

    List<JobVacancy> getAllJobVacancies() {
        List<JobVacancy> jobVacancies = new ArrayList<>()
        try {
            String query = """
                SELECT jv.id, jv.title, jv.description, jv.salary, ct.title AS contract_type, lt.title AS location_type
                    FROM job_vacancies AS jv,
                         companies AS c,
                         contract_types AS ct,
                         location_types AS lt
                    WHERE jv.company_id= c.id
                    AND jv.contract_type_id = ct.id
                    AND jv.location_type_id = lt.id
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
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
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return jobVacancies
    }

    List<JobVacancy> getJobVacancyByCompanyId(int companyId) {
        List<JobVacancy> jobVacancies = new ArrayList<>()
        try {
            String query = """
                SELECT jv.id, jv.title, jv.description, jv.salary, ct.title AS contract_type, lt.title AS location_type
                    FROM job_vacancies AS jv,
                         companies AS c,
                         contract_types AS ct,
                         location_types AS lt
                    WHERE jv.company_id= c.id
                    AND jv.contract_type_id = ct.id
                    AND jv.location_type_id = lt.id
                    AND c.id = ${companyId}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
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
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return jobVacancies
    }

    JobVacancy getJobVacancyById(int id) {
        JobVacancy jobVacancy = new JobVacancy()
        try {
            String query = """
                SELECT jv.id, jv.title, jv.description, jv.salary, ct.title AS contract_type, lt.title AS location_type
                    FROM job_vacancies AS jv,
                         companies AS c,
                         contract_types AS ct,
                         location_types AS lt
                    WHERE jv.company_id= c.id
                    AND jv.contract_type_id = ct.id
                    AND jv.location_type_id = lt.id
                    AND jv.id = ${id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
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
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return jobVacancy
    }

    void insertJobVacancy(JobVacancy jobVacancy, int companyId) {
        try {
            String insertJobVacancy = "INSERT INTO job_vacancies (company_id, title, description, salary, " +
                    "contract_type_id, location_type_id) VALUES (?,?,?,?,?,?)"
            PreparedStatement stmt = sql.connection.prepareStatement(insertJobVacancy, Statement.RETURN_GENERATED_KEYS)
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

            int jobVacancyId = -1
            ResultSet getJobVacancyIdId = stmt.getGeneratedKeys()
            while (stmt.getGeneratedKeys().next()) {
                jobVacancyId = getJobVacancyIdId.getInt(1)
            }

            if (jobVacancyId != -1) {
                for (Skill requiredSkill in jobVacancy.requiredSkills) {
                    requiredSkillDAO.insertSkill(requiredSkill, jobVacancyId)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateJobVacancy(JobVacancy jobVacancy, int companyId) {
        try {
            String updateJobVacancy = """
                UPDATE job_vacancies
                    SET company_id=${companyId}, title=?, description=?, salary=?, contract_type_id=?, location_type_id=?
                    WHERE id=${jobVacancy.id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(updateJobVacancy)
            stmt.setString(1, jobVacancy.title)
            stmt.setString(2, jobVacancy.description)
            stmt.setDouble(3, jobVacancy.salary)

            int contractTypeId = dbService.idFinder("contract_types", "title",
                    jobVacancy.getContractType().toString())
            stmt.setInt(4, contractTypeId)

            int locationTypeId = dbService.idFinder("location_types", "title",
                    jobVacancy.getLocationType().toString())
            stmt.setInt(5, locationTypeId)

            stmt.executeUpdate()

            updateRequiredSkills(jobVacancy.id, jobVacancy)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteJobVacancy(int id) {
        JobVacancy jobVacancy = new JobVacancy()
        try {
            String query = "SELECT * FROM job_vacancies WHERE id = ${id};"
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                jobVacancy.setId(result.getInt("id"))
            }

            if (jobVacancy.id != null) {
                query = "DELETE FROM job_vacancies WHERE id = ${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
            } else {
                println "[Exclusão] Experiência profissional não encontrada."
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateRequiredSkills(int id, JobVacancy jobVacancy) {
        List<Integer> skillsId = new ArrayList<>()
        sql.eachRow("SELECT id FROM job_vacancy_skills WHERE job_vacancy_id=${id}") {row ->
            skillsId << row.getInt("id")
        }

        List<Integer> persistedSkills = skillsId.findAll { !jobVacancy.requiredSkills.contains(it) }
        persistedSkills.each {skillId ->
            requiredSkillDAO.deleteSkill(skillId)
        }

        jobVacancy.requiredSkills.each {skill ->
            if (persistedSkills.contains(skill.id)) {
                requiredSkillDAO.updateSkill(skill, id)
            } else {
                requiredSkillDAO.insertSkill(skill, id)
            }
        }
    }

}
