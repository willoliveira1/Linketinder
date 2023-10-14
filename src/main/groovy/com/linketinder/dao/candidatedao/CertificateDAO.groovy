package com.linketinder.dao.candidatedao

import com.linketinder.database.DatabaseFactory
import com.linketinder.model.candidate.Certificate
import com.linketinder.util.ErrorMessages
import com.linketinder.util.NotFoundMessages
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CertificateDAO {

    private final String QUERY_GET_CERTIFICATES_BY_CANDIDATE_ID = "SELECT id, candidate_id, title, duration FROM certificates WHERE candidate_id=? ORDER BY title"
    private final String QUERY_GET_CERTIFICATE_BY_ID = "SELECT * FROM certificates WHERE id=?"
    private final String INSERT_CERTIFICATE = "INSERT INTO certificates (candidate_id, title, duration) VALUES (?,?,?)"
    private final String UPDATE_CERTIFICATE = "UPDATE certificates SET id=?, candidate_id=?, title=?, duration=? WHERE id=?"
    private final String DELETE_CERTIFICATE = "DELETE FROM certificates WHERE id=?"

    Sql sql = DatabaseFactory.instance()

    private List<Certificate> populateCertificates(String query, int id) {
        List<Certificate> certificates = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            Certificate certificate = new Certificate()
            certificate.setId(result.getInt("id"))
            certificate.setTitle(result.getString("title"))
            certificate.setDuration(result.getString("duration"))
            certificates.add(certificate)
        }
        return certificates
    }

    List<Certificate> getCertificatesByCandidateId(int candidateId) {
        List<Certificate> certificates = new ArrayList<>()
        try {
            certificates = populateCertificates(QUERY_GET_CERTIFICATES_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
        return certificates
    }

    void insertCertificate(Certificate certificate, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, candidateId)
            stmt.setString(2, certificate.getTitle())
            stmt.setString(3, certificate.getDuration())

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
    }

    void updateCertificate(Certificate certificate, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_CERTIFICATE)
            stmt.setInt(1, certificate.id)
            stmt.setInt(2, candidateId)
            stmt.setString(3, certificate.title)
            stmt.setString(4, certificate.duration)
            stmt.setInt(5, certificate.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
    }

    void deleteCertificate(int id) {
        Certificate certificate = new Certificate()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(QUERY_GET_CERTIFICATE_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                certificate.setId(result.getInt("id"))
            }

            if (certificate.id != null) {
                stmt = sql.connection.prepareStatement(DELETE_CERTIFICATE)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
        println NotFoundMessages.CERTIFICATE
    }

}
