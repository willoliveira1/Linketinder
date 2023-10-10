package com.linketinder.dao.companydao

import com.linketinder.database.DatabaseFactory
import com.linketinder.database.DBService
import com.linketinder.model.company.Benefit
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class BenefitDAO {

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()

    private List<Benefit> populateBenefits(String query) {
        List<Benefit> benefits = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            Benefit benefit = new Benefit()
            benefit.setId(result.getInt("id"))
            benefit.setTitle(result.getString("title"))
            benefits.add(benefit)
        }
        return benefits
    }

    List<Benefit> getAllBenefits() {
        List<Benefit> benefits = new ArrayList<>()
        String query = """
            SELECT cb.id, c.id AS company_id, b.title
                FROM companies AS c,
                     company_benefits AS cb,
                     benefits AS b
                WHERE c.id = cb.company_id
                AND b.id = cb.benefit_id
        """
        try {
            benefits = populateBenefits(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return benefits
    }

    List<Benefit> getBenefitsByCompanyId(int companyId) {
        List<Benefit> benefits = new ArrayList<>()
        String query = """
            SELECT cb.id, c.id AS company_id, b.title
                FROM companies AS c,
                     company_benefits AS cb,
                     benefits AS b
                WHERE c.id = cb.company_id
                AND b.id = cb.benefit_id
                AND c.id = ${companyId}
        """
        try {
            benefits = populateBenefits(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return benefits
    }

    private Benefit populateBenefit(String query) {
        Benefit benefit = new Benefit()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            benefit.setId(result.getInt("id"))
            benefit.setTitle(result.getString("title"))
        }
        return benefit
    }

    Benefit getBenefitById(int id) {
        Benefit benefit = new Benefit()
        String query = """
            SELECT cb.id, c.id AS company_id, b.title
                FROM companies AS c,
                     company_benefits AS cb,
                     benefits AS b
                WHERE c.id = cb.company_id
                AND b.id = cb.benefit_id
                AND cb.id = ${id}
        """
        try {
            benefit = populateBenefit(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return benefit
    }

    void insertBenefit(int companyId, Benefit benefit) {
        String insertBenefit = "INSERT INTO company_benefits (company_id, benefit_id) VALUES (?,?)"
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(insertBenefit, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, companyId)

            int benefitId = dbService.idFinder("benefits", "title", benefit.getTitle())
            stmt.setInt(2, benefitId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateBenefit(int companyId, Benefit benefit) {
        String updateLanguage = """
            UPDATE company_benefits
                SET company_id=${companyId}, benefit_id=?
                WHERE id=${benefit.id}
        """
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(updateLanguage)
            int benefitId = dbService.idFinder("benefits", "title", benefit.getTitle())
            stmt.setInt(1, benefitId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteBenefit(int id) {
        Benefit benefit = new Benefit()
        String query = "SELECT * FROM company_benefits WHERE id=${id};"
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                benefit.setId(result.getInt("id"))
            }
            if (benefit.id != null) {
                query = "DELETE FROM company_benefits WHERE id=${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        println "Benefício não encontrado."
    }

}
