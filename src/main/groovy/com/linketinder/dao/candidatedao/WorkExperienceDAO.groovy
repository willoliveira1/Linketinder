package com.linketinder.dao.candidatedao

import com.linketinder.database.DBService
import com.linketinder.database.DatabaseFactory
import com.linketinder.domain.candidate.WorkExperience
import com.linketinder.domain.shared.ContractType
import com.linketinder.domain.shared.LocationType
import com.linketinder.domain.shared.State

import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class WorkExperienceDAO {

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()

    List<WorkExperience> getAllWorkExperiences() {
        List<WorkExperience> workExperiences = new ArrayList<>()
        try {
            String query = """
                SELECT we.id, c.id AS candidate_id, we.title, we.company_name, ct.title AS contract_type_title, lt.title AS location_type_title, we.city, s.acronym, we.currently_work, we.description
                    FROM candidates AS c,
                         work_experiences AS we,
                         states AS s,
                         contract_types AS ct,
                         location_types AS lt
                    WHERE c.id = we.candidate_id
                    AND we.contract_type_id = ct.id
                    AND we.location_id = lt.id
                    AND we.state_id = s.id
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                WorkExperience workExperience = populateWorkExperience(result)
                workExperiences.add(workExperience)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return workExperiences
    }



    List<WorkExperience> getWorkExperiencesByCandidateId(int candidateId) {
        List<WorkExperience> workExperiences = new ArrayList<>()
        try {
            String query = """
                SELECT we.id, we.title, we.company_name, ct.title AS contract_type_title, lt.title AS location_type_title, we.city, s.acronym, we.currently_work, we.description
                    FROM candidates AS c,
                         work_experiences AS we,
                         states AS s,
                         contract_types AS ct,
                         location_types AS lt
                    WHERE c.id = we.candidate_id
                    AND we.contract_type_id = ct.id
                    AND we.location_id = lt.id
                    AND we.state_id = s.id
                    AND c.id = ${candidateId}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                WorkExperience workExperience = populateWorkExperience(result)
                workExperiences.add(workExperience)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return workExperiences
    }

    WorkExperience getWorkExperienceById(int id) {
        WorkExperience workExperience = new WorkExperience()
        try {
            String query = """
                SELECT we.id, we.title, we.company_name, ct.title AS contract_type_title, lt.title AS location_type_title, we.city, s.acronym, we.currently_work, we.description
                    FROM candidates AS c,
                         work_experiences AS we,
                         states AS s,
                         contract_types AS ct,
                         location_types AS lt
                    WHERE c.id = we.candidate_id
                    AND we.contract_type_id = ct.id
                    AND we.location_id = lt.id
                    AND we.state_id = s.id
                    AND we.id = ${id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            workExperience = populateWorkExperience(result)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return workExperience
    }

    void insertWorkExperience(WorkExperience workExperience, int candidateId) {
        try {
            String insertWorkExperience = "INSERT INTO work_experiences (candidate_id, title, company_name, city, " +
                "currently_work, description, state_id, contract_type_id, location_id) VALUES (?,?,?,?,?,?,?,?,?)"
            PreparedStatement stmt = sql.connection.prepareStatement(insertWorkExperience, Statement.RETURN_GENERATED_KEYS)

            stmt.setInt(1, candidateId)
            stmt.setString(2, workExperience.title)
            stmt.setString(3, workExperience.companyName)
            stmt.setString(4, workExperience.city)
            stmt.setBoolean(5, workExperience.currentlyWork)

            stmt.setString(6, workExperience.description)

            int workStateId = dbService.idFinder("states", "acronym", workExperience.getState().toString())
            stmt.setInt(7, workStateId)

            int contractTypeId = dbService.idFinder("contract_types", "title", workExperience.getContractType().toString())
            stmt.setInt(8, contractTypeId)

            int locationTypeId = dbService.idFinder("location_types", "title", workExperience.getLocationType().toString())
            stmt.setInt(9, locationTypeId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateWorkExperience(WorkExperience workExperience, int candidateId) {
        try {
            String updateWorkExperience = """
                UPDATE work_experiences
                    SET candidate_id=${candidateId}, title=?, company_name=?, city=?, currently_work=?, description=?, 
                        state_id=?, contract_type_id=?, location_id=?
                    WHERE id=${workExperience.id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(updateWorkExperience)
            stmt.setString(1, workExperience.title)
            stmt.setString(2, workExperience.companyName)
            stmt.setString(3, workExperience.city)
            stmt.setBoolean(4, workExperience.currentlyWork)

            stmt.setString(5, workExperience.description)

            int workStateId = dbService.idFinder("states", "acronym", workExperience.getState().toString())
            stmt.setInt(6, workStateId)

            int contractTypeId = dbService.idFinder("contract_types", "title", workExperience.getContractType().toString())
            stmt.setInt(7, contractTypeId)

            int locationTypeId = dbService.idFinder("location_types", "title", workExperience.getLocationType().toString())
            stmt.setInt(8, locationTypeId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteWorkExperience(int id) {
        WorkExperience skill = new WorkExperience()
        try {
            String query = "SELECT * FROM work_experiences WHERE id = ${id};"
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                skill.setId(result.getInt("id"))
            }

            if (skill.id != null) {
                query = "DELETE FROM work_experiences WHERE id = ${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
            } else {
                println "[Exclusão] Experiência profissional não encontrado."
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    WorkExperience populateWorkExperience(ResultSet result) {
        WorkExperience workExperience = new WorkExperience()
        workExperience.setId(result.getInt("id"))
        workExperience.setTitle(result.getString("title"))
        workExperience.setCompanyName(result.getString("company_name"))
        workExperience.setContractType(ContractType.valueOf(result.getString("contract_type_title")))
        workExperience.setLocationType(LocationType.valueOf(result.getString("location_type_title")))
        workExperience.setCity(result.getString("city"))
        workExperience.setState(State.valueOf(result.getString("acronym")))
        workExperience.setCurrentlyWork(result.getBoolean("currently_work"))
        workExperience.setDescription(result.getString("description"))

        return workExperience
    }

}
