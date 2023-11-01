package com.linketinder.dao.candidatedao

import com.linketinder.dao.candidatedao.interfaces.IAcademicExperienceDAO
import com.linketinder.dao.candidatedao.queries.AcademicExperienceQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.*
import com.linketinder.model.candidate.*
import com.linketinder.util.*
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class AcademicExperienceDAO implements IAcademicExperienceDAO {

    IConnection connection
    IDBService dbService
    Sql sql = connection.instance()

    AcademicExperienceDAO(IDBService dbService, IConnection connection) {
        this.dbService = dbService
        this.connection = connection
    }

    private List<AcademicExperience> populateAcademicExperiences(String query, int id) {
        List<AcademicExperience> academicExperiences = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            AcademicExperience academicExperience = new AcademicExperience()
            academicExperience.setId(result.getInt("id"))
            academicExperience.setEducationalInstitution(result.getString("educational_institution"))
            academicExperience.setDegreeType(result.getString("degree_type"))
            academicExperience.setFieldOfStudy(result.getString("field_of_study"))
            academicExperience.setStatus(CourseStatus.valueOf(result.getString("title")))
            academicExperiences.add(academicExperience)
        }
        return academicExperiences
    }

    List<AcademicExperience> getAcademicExperiencesByCandidateId(int candidateId) {
        List<AcademicExperience> academicExperiences = new ArrayList<>()
        try {
            academicExperiences = this.populateAcademicExperiences(
                    AcademicExperienceQueries.GET_ACADEMIC_EXPERIENCES_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return academicExperiences
    }

    private PreparedStatement setAcademicExperienceStatement(PreparedStatement stmt,
                                                             AcademicExperience academicExperience, int candidateId) {
        int courseStatusId = dbService.idFinder("course_status", "title",
                academicExperience.getStatus().toString())
        stmt.setInt(1, candidateId)
        stmt.setString(2, academicExperience.educationalInstitution)
        stmt.setString(3, academicExperience.degreeType)
        stmt.setString(4, academicExperience.fieldOfStudy)
        stmt.setInt(5, courseStatusId)
        return stmt
    }

    void insertAcademicExperience(AcademicExperience academicExperience, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(
                    AcademicExperienceQueries.INSERT_ACADEMIC_EXPERIENCE, Statement.RETURN_GENERATED_KEYS)
            stmt = this.setAcademicExperienceStatement(stmt, academicExperience, candidateId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateAcademicExperience(AcademicExperience academicExperience, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(AcademicExperienceQueries.UPDATE_ACADEMIC_EXPERIENCE)
            stmt = this.setAcademicExperienceStatement(stmt, academicExperience, candidateId)
            stmt.setInt(6, academicExperience.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteAcademicExperience(int id) {
        AcademicExperience academicExperience = new AcademicExperience()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(
                    AcademicExperienceQueries.GET_ACADEMIC_EXPERIENCE_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                academicExperience.setId(result.getInt("id"))
            }

            if (academicExperience.id != null) {
                stmt = sql.connection.prepareStatement(AcademicExperienceQueries.DELETE_ACADEMIC_EXPERIENCE)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.ACADEMIC_EXPERIENCE
    }

}
