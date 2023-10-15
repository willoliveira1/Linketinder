package com.linketinder.dao.candidatedao

import com.linketinder.dao.candidatedao.interfaces.IWorkExperienceDAO
import com.linketinder.database.DBService
import com.linketinder.database.DatabaseFactory
import com.linketinder.model.candidate.WorkExperience
import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.LocationType
import com.linketinder.model.shared.State
import com.linketinder.util.ErrorMessages
import com.linketinder.util.NotFoundMessages
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class WorkExperienceDAO implements IWorkExperienceDAO {

    private final String GET_WORK_EXPERIENCES_BY_CANDIDATE_ID = "SELECT we.id, we.title, we.company_name, ct.title AS contract_type_title, lt.title AS location_type_title, we.city, s.acronym, we.currently_work, we.description FROM candidates AS c, work_experiences AS we, states AS s, contract_types AS ct, location_types AS lt WHERE c.id = we.candidate_id AND we.contract_type_id = ct.id AND we.location_id = lt.id AND we.state_id = s.id AND c.id=?"
    private final String GET_WORK_EXPERIENCES_ID = "SELECT * FROM work_experiences WHERE id=?"
    private final String INSERT_WORK_EXPERIENCE = "INSERT INTO work_experiences (candidate_id, title, company_name, city, currently_work, description, state_id, contract_type_id, location_id) VALUES (?,?,?,?,?,?,?,?,?)"
    private final String UPDATE_WORK_EXPERIENCE = "UPDATE work_experiences SET candidate_id=?, title=?, company_name=?, city=?, currently_work=?, description=?, state_id=?, contract_type_id=?, location_id=? WHERE id=?"
    private final String DELETE_WORK_EXPERIENCE = "DELETE FROM work_experiences WHERE id=?"

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()

    private List<WorkExperience> populateWorkExperiences(String query, int id) {
        List<WorkExperience> workExperiences = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
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
            workExperiences.add(workExperience)
        }
        return workExperiences
    }

    List<WorkExperience> getWorkExperiencesByCandidateId(int candidateId) {
        List<WorkExperience> workExperiences = new ArrayList<>()
        try {
            workExperiences = populateWorkExperiences(GET_WORK_EXPERIENCES_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return workExperiences
    }

    private PreparedStatement setWorkExperienceStatement(PreparedStatement stmt, WorkExperience workExperience,
                                                         int candidateId) {
        int workStateId = dbService.idFinder("states", "acronym", workExperience.getState().toString())
        int contractTypeId = dbService.idFinder("contract_types", "title", workExperience.getContractType().toString())
        int locationTypeId = dbService.idFinder("location_types", "title", workExperience.getLocationType().toString())
        stmt.setInt(1, candidateId)
        stmt.setString(2, workExperience.title)
        stmt.setString(3, workExperience.companyName)
        stmt.setString(4, workExperience.city)
        stmt.setBoolean(5, workExperience.currentlyWork)
        stmt.setString(6, workExperience.description)
        stmt.setInt(7, workStateId)
        stmt.setInt(8, contractTypeId)
        stmt.setInt(9, locationTypeId)
        return stmt
    }

    void insertWorkExperience(WorkExperience workExperience, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_WORK_EXPERIENCE, Statement.RETURN_GENERATED_KEYS)
            stmt = this.setWorkExperienceStatement(stmt, workExperience, candidateId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateWorkExperience(WorkExperience workExperience, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_WORK_EXPERIENCE)
            stmt = this.setWorkExperienceStatement(stmt, workExperience, candidateId)
            stmt.setInt(10, workExperience.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteWorkExperience(int id) {
        WorkExperience workExperience = new WorkExperience()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(GET_WORK_EXPERIENCES_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                workExperience.setId(result.getInt("id"))
            }

            if (workExperience.id != null) {
                stmt = sql.connection.prepareStatement(DELETE_WORK_EXPERIENCE)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.WORK_EXPERIENCE
    }

}
