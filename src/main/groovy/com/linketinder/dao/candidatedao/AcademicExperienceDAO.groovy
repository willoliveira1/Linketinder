package com.linketinder.dao.candidatedao

import com.linketinder.database.DBService
import com.linketinder.database.DatabaseFactory
import com.linketinder.model.candidate.CourseStatus
import com.linketinder.model.candidate.AcademicExperience
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class AcademicExperienceDAO {

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()

    private List<AcademicExperience> populateAcademicExperiences(String query) {
        List<AcademicExperience> academicExperiences = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
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

    List<AcademicExperience> getAllAcademicExperiences() {
        List<AcademicExperience> academicExperiences = new ArrayList<>()
        String query = """
            SELECT a.id, a.educational_institution, a.degree_type, a.field_of_study, cs.title
                FROM candidates AS c,
                    academic_experiences AS a,
                    course_status AS cs
                WHERE c.id = a.candidate_id
                AND a.course_status_id = cs.id
        """
        try {
            academicExperiences = populateAcademicExperiences(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return academicExperiences
    }

    List<AcademicExperience> getAcademicExperiencesByCandidateId(int candidateId) {
        List<AcademicExperience> academicExperiences = new ArrayList<>()
        String query = """
            SELECT a.id, a.educational_institution, a.degree_type, a.field_of_study, cs.title
                FROM candidates AS c,
                    academic_experiences AS a,
                    course_status AS cs
                WHERE c.id = a.candidate_id
                AND a.course_status_id = cs.id
                AND c.id = ${candidateId}
        """
        try {
            academicExperiences = populateAcademicExperiences(query)

        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return academicExperiences
    }

    private AcademicExperience populateAcademicExperience(String query) {
        AcademicExperience academicExperience = new AcademicExperience()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            academicExperience.setId(result.getInt("id"))
            academicExperience.setEducationalInstitution(result.getString("educational_institution"))
            academicExperience.setDegreeType(result.getString("degree_type"))
            academicExperience.setFieldOfStudy(result.getString("field_of_study"))
            academicExperience.setStatus(CourseStatus.valueOf(result.getString("title")))
        }
        return academicExperience
    }

    AcademicExperience getAcademicExperienceById(int id) {
        AcademicExperience academicExperience = new AcademicExperience()
        String query = """
            SELECT a.id, a.educational_institution, a.degree_type, a.field_of_study, cs.title
                FROM candidates AS c,
                    academic_experiences AS a,
                    course_status AS cs
                WHERE c.id = a.candidate_id
                AND a.course_status_id = cs.id
                AND a.id = ${id}
        """
        try {
            academicExperience = populateAcademicExperience(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return academicExperience
    }

    void insertAcademicExperience(AcademicExperience academicExperience, int candidateId) {
        String insertAcademicExperience = "INSERT INTO academic_experiences (candidate_id, " +
                "educational_institution, degree_type, field_of_study, course_status_id) VALUES (?,?,?,?,?)"
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(insertAcademicExperience, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, candidateId)
            stmt.setString(2, academicExperience.educationalInstitution)
            stmt.setString(3, academicExperience.degreeType)
            stmt.setString(4, academicExperience.fieldOfStudy)

            int courseStatusId = dbService.idFinder("course_status", "title", academicExperience.getStatus().toString())
            stmt.setInt(5, courseStatusId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateAcademicExperience(AcademicExperience academicExperience, int candidateId) {
        String updateAcademicExperience = """
            UPDATE academic_experiences 
                SET candidate_id=${candidateId}, educational_institution=?, degree_type=?, field_of_study=?, 
                    course_status_id=?
                WHERE id=${academicExperience.id}
        """
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(updateAcademicExperience)
            stmt.setString(1, academicExperience.educationalInstitution)
            stmt.setString(2, academicExperience.degreeType)
            stmt.setString(3, academicExperience.fieldOfStudy)

            int courseStatusId = dbService.idFinder("course_status", "title",
                    academicExperience.getStatus().toString())
            stmt.setInt(4, courseStatusId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteAcademicExperience(int id) {
        AcademicExperience academicExperience = new AcademicExperience()
        String query = "SELECT * FROM academic_experiences WHERE id = ${id};"
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                academicExperience.setId(result.getInt("id"))
            }

            if (academicExperience.id != null) {
                query = "DELETE FROM academic_experiences WHERE id = ${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        println "Experiência acadêmica não encontrada."
    }

}
