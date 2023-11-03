package com.linketinder.dao.companydao

import com.linketinder.dao.companydao.interfaces.IBenefitDAO
import com.linketinder.dao.companydao.queries.BenefitQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.*
import com.linketinder.model.company.Benefit
import com.linketinder.util.*
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLDataException
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class BenefitDAO implements IBenefitDAO {

    IConnection connection
    IDBService dbService
    Sql sql = connection.instance()

    BenefitDAO(IDBService dbService, IConnection connection) {
        this.dbService = dbService
        this.connection = connection
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
            benefits = this.populateBenefits(BenefitQueries.GET_BENEFITS_BY_COMPANY_ID, companyId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return benefits
    }

    private int getBenefitIdByTitle(String benefitTitle) {
        PreparedStatement stmt = sql.connection.prepareStatement(BenefitQueries.GET_BENEFIT_ID_BY_TITLE)
        stmt.setString(1, benefitTitle)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            return result.getInt("id")
        }
        throw new SQLDataException("Id não encontrado.")
    }

    private PreparedStatement setBenefitStatement(PreparedStatement stmt, Benefit benefit, int companyId) {
        int benefitId = this.getBenefitIdByTitle(benefit.getTitle())
        stmt.setInt(1, companyId)
        stmt.setInt(2, benefitId)
        return stmt
    }

    private void isNewBenefit(Benefit benefit) {
        PreparedStatement stmt = sql.connection.prepareStatement(BenefitQueries.GET_BENEFIT_BY_TITLE)
        stmt.setString(1, benefit.title)
        ResultSet result = stmt.executeQuery()

        if (result.next()) {
            return
        }
        stmt = sql.connection.prepareStatement(BenefitQueries.INSERT_BENEFIT, Statement.RETURN_GENERATED_KEYS)
        stmt.setString(1, benefit.title)
        stmt.executeUpdate()
    }

    void insertBenefit(int companyId, Benefit benefit) {
        try {
            this.isNewBenefit(benefit)

            PreparedStatement stmt = sql.connection.prepareStatement(BenefitQueries.INSERT_COMPANY_BENEFIT,
                    Statement.RETURN_GENERATED_KEYS)
            stmt = this.setBenefitStatement(stmt, benefit, companyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateBenefit(int companyId, Benefit benefit) {
        try {
            this.isNewBenefit(benefit)

            PreparedStatement stmt = sql.connection.prepareStatement(BenefitQueries.UPDATE_COMPANY_BENEFIT,
                    companyId, benefit.id)
            stmt = this.setBenefitStatement(stmt, benefit, companyId)
            stmt.setInt(3, benefit.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteBenefit(int id) {
        Benefit benefit = new Benefit()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(BenefitQueries.GET_COMPANY_BENEFIT_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                benefit.setId(result.getInt("id"))
            }
            if (benefit.id != null) {
                stmt = sql.connection.prepareStatement(BenefitQueries.DELETE_COMPANY_BENEFIT)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.BENEFIT
    }

}
