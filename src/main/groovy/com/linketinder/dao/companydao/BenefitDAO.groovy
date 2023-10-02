package com.linketinder.dao.companydao

import com.linketinder.database.DBService
import com.linketinder.database.DatabaseFactory
import com.linketinder.domain.company.Benefit

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

    List<Benefit> getAllBenefits() {
        List<Benefit> benefits = new ArrayList<>()
        try {
            String query = """
                SELECT cb.id, c.id AS company_id, b.title
                    FROM companies AS c,
                         company_benefits AS cb,
                         benefits AS b
                    WHERE c.id = cb.company_id
                    AND b.id = cb.benefit_id
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                Benefit benefit = populateBenefit(result)
                benefits.add(benefit)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return benefits
    }

    List<Benefit> getBenefitsByCompanyId(int companyId) {
        List<Benefit> benefits = new ArrayList<>()
        try {
            String query = """
                SELECT cb.id, c.id AS company_id, b.title
                    FROM companies AS c,
                         company_benefits AS cb,
                         benefits AS b
                    WHERE c.id = cb.company_id
                    AND b.id = cb.benefit_id
                    AND c.id = ${companyId}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                Benefit benefit = populateBenefit(result)
                benefits.add(benefit)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return benefits
    }

    Benefit getBenefitById(int id) {
        Benefit benefit = new Benefit()
        try {
            String query = """
                SELECT cb.id, c.id AS company_id, b.title
                    FROM companies AS c,
                         company_benefits AS cb,
                         benefits AS b
                    WHERE c.id = cb.company_id
                    AND b.id = cb.benefit_id
                    AND cb.id = ${id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                benefit = populateBenefit(result)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return benefit
    }

    void insertBenefit(Benefit benefit, int companyId) {
        try {
            String insertBenefit = "INSERT INTO company_benefits (company_id, benefit_id) VALUES (?,?)"
            PreparedStatement stmt = sql.connection.prepareStatement(insertBenefit, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, companyId)

            int benefitId = dbService.idFinder("benefits", "title", benefit.getTitle())
            stmt.setInt(2, benefitId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateBenefit(Benefit benefit, int companyId) {
        try {
            String updateLanguage = """
                UPDATE company_benefits
                    SET company_id=${companyId}, benefit_id=?
                    WHERE id=${benefit.id}
            """
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
        try {
            String query = "SELECT * FROM company_benefits WHERE id=${id};"
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                benefit.setId(result.getInt("id"))
            }

            if (benefit.id != null) {
                query = "DELETE FROM company_benefits WHERE id=${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
            } else {
                println "[Exclusão] Benefício não encontrado."
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    Benefit populateBenefit(ResultSet result) {
        Benefit benefit = new Benefit()
        benefit.setId(result.getInt("id"))
        benefit.setTitle(result.getString("title"))
        return benefit
    }

}
