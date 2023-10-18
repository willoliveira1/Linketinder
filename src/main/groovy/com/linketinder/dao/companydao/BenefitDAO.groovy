package com.linketinder.dao.companydao

import com.linketinder.dao.companydao.interfaces.IBenefitDAO
import com.linketinder.database.DatabaseConnection
import com.linketinder.database.interfaces.IDBService
import com.linketinder.database.interfaces.IDatabaseConnection
import com.linketinder.model.candidate.Language
import com.linketinder.model.company.Benefit
import com.linketinder.util.ErrorMessages
import com.linketinder.util.NotFoundMessages
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class BenefitDAO implements IBenefitDAO {

    private final String GET_BENEFITS_BY_COMPANY_ID = "SELECT cb.id, c.id AS company_id, b.title FROM companies AS c, company_benefits AS cb, benefits AS b WHERE c.id = cb.company_id AND b.id = cb.benefit_id AND c.id=?"
    private final String GET_COMPANY_BENEFIT_BY_ID = "SELECT * FROM company_benefits WHERE id=?"
    private final String INSERT_COMPANY_BENEFIT = "INSERT INTO company_benefits (company_id, benefit_id) VALUES (?,?)"
    private final String UPDATE_COMPANY_BENEFIT = "UPDATE company_benefits SET company_id=?, benefit_id=? WHERE id=?"
    private final String DELETE_COMPANY_BENEFIT = "DELETE FROM company_benefits WHERE id=?"
    private final String INSERT_BENEFIT = "INSERT INTO benefits (title) VALUES (?)"
    private final String GET_BENEFIT_BY_TITLE = "SELECT * FROM benefits WHERE title=?"

    IDatabaseConnection databaseFactory
    IDBService dbService
    Sql sql = databaseFactory.instance()

    BenefitDAO(IDBService dbService, IDatabaseConnection databaseFactory) {
        this.dbService = dbService
        this.databaseFactory = databaseFactory
    }

    private List<Benefit> populateBenefits(String query, int id) {
        List<Benefit> benefits = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            Benefit benefit = new Benefit()
            benefit.setId(result.getInt("id"))
            benefit.setTitle(result.getString("title"))
            benefits.add(benefit)
        }
        return benefits
    }

    List<Benefit> getBenefitsByCompanyId(int companyId) {
        List<Benefit> benefits = new ArrayList<>()
        try {
            benefits = this.populateBenefits(GET_BENEFITS_BY_COMPANY_ID, companyId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return benefits
    }

    private PreparedStatement setBenefitStatement(PreparedStatement stmt, Benefit benefit, int companyId) {
        int benefitId = dbService.idFinder("benefits", "title", benefit.getTitle())
        stmt.setInt(1, companyId)
        stmt.setInt(2, benefitId)
        return stmt
    }

    private void isNewBenefit(Benefit benefit) {
        PreparedStatement stmt = sql.connection.prepareStatement(GET_BENEFIT_BY_TITLE)
        stmt.setString(1, benefit.title)
        ResultSet result = stmt.executeQuery()

        if (result.next()) {
            return
        }
        stmt = sql.connection.prepareStatement(INSERT_BENEFIT, Statement.RETURN_GENERATED_KEYS)
        stmt.setString(1, benefit.title)
        stmt.executeUpdate()
    }

    void insertBenefit(int companyId, Benefit benefit) {
        try {
            this.isNewBenefit(benefit)

            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_COMPANY_BENEFIT, Statement.RETURN_GENERATED_KEYS)
            stmt = this.setBenefitStatement(stmt, benefit, companyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateBenefit(int companyId, Benefit benefit) {
        try {
            this.isNewBenefit(benefit)

            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_COMPANY_BENEFIT, companyId, benefit.id)
            stmt = this.setBenefitStatement(stmt, benefit, companyId)
            stmt.setInt(3, benefit.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteBenefit(int id) {
        Benefit benefit = new Benefit()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(GET_COMPANY_BENEFIT_BY_ID)
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
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.BENEFIT
    }

}
