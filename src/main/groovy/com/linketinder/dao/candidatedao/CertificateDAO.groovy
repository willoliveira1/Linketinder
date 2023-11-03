package com.linketinder.dao.candidatedao

import com.linketinder.dao.candidatedao.interfaces.ICertificateDAO
import com.linketinder.dao.candidatedao.queries.CertificateQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.IConnection
import com.linketinder.model.candidate.Certificate
import com.linketinder.util.*
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CertificateDAO implements ICertificateDAO {

    IConnection connection
    Sql sql = connection.instance()

    CertificateDAO(IConnection connection) {
        this.connection = connection
    }

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
            certificates = populateCertificates(CertificateQueries.GET_CERTIFICATES_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return certificates
    }

    private PreparedStatement setCertificateStatement(PreparedStatement stmt, Certificate certificate, int candidateId) {
        stmt.setInt(1, candidateId)
        stmt.setString(2, certificate.getTitle())
        stmt.setString(3, certificate.getDuration())
        return stmt
    }

    void insertCertificate(Certificate certificate, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CertificateQueries.INSERT_CERTIFICATE,
                    Statement.RETURN_GENERATED_KEYS)
            stmt = this.setCertificateStatement(stmt, certificate, candidateId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateCertificate(Certificate certificate, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CertificateQueries.UPDATE_CERTIFICATE)
            stmt = this.setCertificateStatement(stmt, certificate, candidateId)
            stmt.setInt(4, certificate.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteCertificate(int id) {
        Certificate certificate = new Certificate()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CertificateQueries.GET_CERTIFICATE_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                certificate.setId(result.getInt("id"))
            }

            if (certificate.id != null) {
                stmt = sql.connection.prepareStatement(CertificateQueries.DELETE_CERTIFICATE)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.CERTIFICATE
    }

}
