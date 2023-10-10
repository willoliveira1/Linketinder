package com.linketinder.dao.candidatedao

import com.linketinder.database.DatabaseFactory
import com.linketinder.model.candidate.Certificate
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CertificateDAO {

    Sql sql = DatabaseFactory.instance()

    private List<Certificate> populateCertificates(String query) {
        List<Certificate> certificates = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
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

    List<Certificate> getAllCertificates() {
        List<Certificate> certificates = new ArrayList<>()
        String query = """
            SELECT id, candidate_id, title, duration 
                FROM certificates
                ORDER BY id
        """
        try {
            certificates = populateCertificates(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return certificates
    }

    List<Certificate> getCertificatesByCandidateId(int candidateId) {
        List<Certificate> certificates = new ArrayList<>()
        String query = """
            SELECT id, candidate_id, title, duration 
                FROM certificates
                WHERE candidate_id=${candidateId}
                ORDER BY title
        """
        try {
            certificates = populateCertificates(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return certificates
    }

    private Certificate populateCertificate(String query) {
        Certificate certificate = new Certificate()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            certificate.setId(result.getInt("id"))
            certificate.setTitle(result.getString("title"))
            certificate.setDuration(result.getString("duration"))
        }
        return certificate
    }

    Certificate getCertificateById(int id) {
        Certificate certificate = new Certificate()
        String query = """
            SELECT id, candidate_id, title, duration 
                FROM certificates
                WHERE id=${id}
        """
        try {
            certificate = populateCertificate(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return certificate
    }

    void insertCertificate(Certificate certificate, int candidateId) {
        String insertCertificate = "INSERT INTO certificates (candidate_id, title, duration) VALUES (?,?,?)"
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(insertCertificate,
                    Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, candidateId)
            stmt.setString(2, certificate.getTitle())
            stmt.setString(3, certificate.getDuration())

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateCertificate(Certificate certificate, int candidateId) {
        String updateCertificate = """
            UPDATE certificates
                SET id=${certificate.id}, candidate_id=${candidateId}, title=?, duration=? 
                WHERE id=${certificate.id}
        """
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(updateCertificate)
            stmt.setString(1, certificate.title)
            stmt.setString(2, certificate.duration)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteCertificate(int id) {
        Certificate certificate = new Certificate()
        String query = "SELECT * FROM certificates WHERE id = ${id};"
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                certificate.setId(result.getInt("id"))
            }

            if (certificate.id != null) {
                query = "DELETE FROM certificates WHERE id = ${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        println "Certificado não encontrado."
    }

}
