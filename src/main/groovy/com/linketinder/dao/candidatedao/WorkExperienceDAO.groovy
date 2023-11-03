package com.linketinder.dao.candidatedao

import com.linketinder.dao.candidatedao.interfaces.IWorkExperienceDAO
import com.linketinder.dao.candidatedao.queries.WorkExperienceQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.*
import com.linketinder.model.candidate.WorkExperience
import com.linketinder.model.jobvacancy.*
import com.linketinder.model.shared.State
import com.linketinder.util.*
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class WorkExperienceDAO implements IWorkExperienceDAO {

    IConnection connection
    Sql sql = connection.instance()

    WorkExperienceDAO(IConnection connection) {
        this.connection = connection
    }

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
            workExperiences = populateWorkExperiences(WorkExperienceQueries.GET_WORK_EXPERIENCES_BY_CANDIDATE_ID,
                    candidateId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return workExperiences
    }

    private int getStateIdByTitle(String stateTitle) {
        PreparedStatement stmt = sql.connection.prepareStatement(WorkExperienceQueries.GET_STATE_ID_BY_TITLE)
        stmt.setString(1, stateTitle)
        return QueryHelper.idFinder(stmt)
    }

    private int getContractTypeIdByTitle(String contractTypeTitle) {
        PreparedStatement stmt = sql.connection.prepareStatement(WorkExperienceQueries.GET_CONTRACT_TYPE_ID_BY_TITLE)
        stmt.setString(1, contractTypeTitle)
        return QueryHelper.idFinder(stmt)
    }

    private int getLocationTypeIdByTitle(String locationTypeTitle) {
        PreparedStatement stmt = sql.connection.prepareStatement(WorkExperienceQueries.GET_LOCATION_TYPE_ID_BY_TITLE)
        stmt.setString(1, locationTypeTitle)
        return QueryHelper.idFinder(stmt)
    }

    private PreparedStatement setWorkExperienceStatement(PreparedStatement stmt, WorkExperience workExperience,
                                                         int candidateId) {
        int workStateId = this.getStateIdByTitle(workExperience.getState().toString())
        int contractTypeId = this.getContractTypeIdByTitle(workExperience.getContractType().toString())
        int locationTypeId = this.getLocationTypeIdByTitle(workExperience.getLocationType().toString())
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
            PreparedStatement stmt = sql.connection.prepareStatement(WorkExperienceQueries.INSERT_WORK_EXPERIENCE,
                    Statement.RETURN_GENERATED_KEYS)
            stmt = this.setWorkExperienceStatement(stmt, workExperience, candidateId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateWorkExperience(WorkExperience workExperience, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(WorkExperienceQueries.UPDATE_WORK_EXPERIENCE)
            stmt = this.setWorkExperienceStatement(stmt, workExperience, candidateId)
            stmt.setInt(10, workExperience.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteWorkExperience(int id) {
        WorkExperience workExperience = new WorkExperience()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(WorkExperienceQueries.GET_WORK_EXPERIENCES_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                workExperience.setId(result.getInt("id"))
            }

            if (workExperience.id != null) {
                stmt = sql.connection.prepareStatement(WorkExperienceQueries.DELETE_WORK_EXPERIENCE)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.WORK_EXPERIENCE
    }

}
