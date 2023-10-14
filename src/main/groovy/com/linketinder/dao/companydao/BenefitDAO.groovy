package com.linketinder.dao.companydao

import com.linketinder.database.DatabaseFactory
import com.linketinder.database.DBService
import com.linketinder.database.IDBService
import com.linketinder.model.company.Benefit
import com.linketinder.util.ErrorText
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class BenefitDAO {

    static final String QUERY_GET_ALL_BENEFITS = "SELECT cb.id, c.id AS company_id, b.title FROM companies AS c, company_benefits AS cb, benefits AS b WHERE c.id = cb.company_id AND b.id = cb.benefit_id"
    static final String QUERY_GET_BENEFITS_BY_COMPANY_ID = "SELECT cb.id, c.id AS company_id, b.title FROM companies AS c, company_benefits AS cb, benefits AS b WHERE c.id = cb.company_id AND b.id = cb.benefit_id AND c.id=?"
    static final String QUERY_GET_COMPANY_BENEFIT_BY_ID = "SELECT * FROM company_benefits WHERE id=?"
    static final String INSERT_BENEFIT = "INSERT INTO company_benefits (company_id, benefit_id) VALUES (?,?)"
    static final String UPDATE_BENEFIT = "UPDATE company_benefits SET company_id=?, benefit_id=? WHERE id=?"
    static final String DELETE_COMPANY_BENEFIT = "DELETE FROM company_benefits WHERE id=?"

    Sql sql = DatabaseFactory.instance()
    IDBService dbService = new DBService()

    private List<Benefit> populateBenefits(String query, Integer... args) {
        List<Benefit> benefits = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        if (args.any()) {
            stmt.setInt(1, args[0])
        }
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
        try {
            benefits = populateBenefits(QUERY_GET_ALL_BENEFITS)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
        return benefits
    }

    List<Benefit> getBenefitsByCompanyId(int companyId) {
        List<Benefit> benefits = new ArrayList<>()
        try {
            benefits = populateBenefits(QUERY_GET_BENEFITS_BY_COMPANY_ID, companyId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
        return benefits
    }

    void insertBenefit(int companyId, Benefit benefit) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_BENEFIT, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, companyId)

            int benefitId = dbService.idFinder("benefits", "title", benefit.getTitle())
            stmt.setInt(2, benefitId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
    }

    void updateBenefit(int companyId, Benefit benefit) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_BENEFIT, companyId, benefit.id)
            int benefitId = dbService.idFinder("benefits", "title", benefit.getTitle())
            stmt.setInt(1, companyId)
            stmt.setInt(2, benefitId)
            stmt.setInt(3, benefit.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
    }

    void deleteBenefit(int id) {
        Benefit benefit = new Benefit()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(QUERY_GET_COMPANY_BENEFIT_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                benefit.setId(result.getInt("id"))
            }
            if (benefit.id != null) {
                stmt = sql.connection.prepareStatement(DELETE_COMPANY_BENEFIT)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
        println "Benefício não encontrado."
    }

}
