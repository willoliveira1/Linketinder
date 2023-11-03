package com.linketinder.dao.companydao

import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.dao.companydao.queries.CompanyQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.*
import com.linketinder.model.company.*
import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.model.shared.*
import com.linketinder.util.*
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLDataException
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CompanyDAO implements ICompanyDAO {

    IDBService dbService
    IConnection connection
    IBenefitDAO benefitDAO
    IJobVacancyDAO jobVacancyDAO
    Sql sql = connection.instance()

    CompanyDAO(IDBService dbService, IConnection connection, IBenefitDAO benefitDAO,
               IJobVacancyDAO jobVacancyDAO) {
        this.dbService = dbService
        this.connection = connection
        this.benefitDAO = benefitDAO
        this.jobVacancyDAO = jobVacancyDAO
    }

    private Company createCompany(ResultSet result) {
        Person company = new Company()
        company.setId(result.getInt("id"))
        company.setName(result.getString("name"))
        company.setEmail(result.getString("email"))
        company.setCity(result.getString("city"))
        company.setState(State.valueOf(result.getString("state")))
        company.setCountry(result.getString("country"))
        company.setCep(result.getString("cep"))
        company.setDescription(result.getString("description"))
        company.setCnpj(result.getString("cnpj"))
        company.setBenefits(benefitDAO.getBenefitsByCompanyId(company.id))
        company.setJobVacancies(jobVacancyDAO.getJobVacancyByCompanyId(company.id))
        return company
    }

    private List<Company> populateCompanies(String query) {
        List<Company> companies = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            Person company = this.createCompany(result)
            companies.add(company)
        }
        return companies
    }

    private Company populateCompany(String query, int id) {
        Person company = new Company()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            company = this.createCompany(result)
        }
        return company
    }

    List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>()
        try {
            companies = this.populateCompanies(CompanyQueries.GET_ALL_COMPANIES)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return companies
    }

    Company getCompanyById(int id) {
        Person company = new Company()
        try {
            company = this.populateCompany(CompanyQueries.GET_COMPANY_BY_ID, id)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return company
    }

    private int getStateIdByTitle(String stateTitle) {
        PreparedStatement stmt = sql.connection.prepareStatement(CompanyQueries.GET_STATE_ID_BY_TITLE)
        stmt.setString(1, stateTitle)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            return result.getInt("id")
        }
        throw new SQLDataException("Id não encontrado.")
    }

    private setCompanyStatement(PreparedStatement stmt, Company company, boolean isUpdate) {
        int stateId = this.getStateIdByTitle(company.getState().toString())
        stmt.setString(1, company.name)
        stmt.setString(2, company.getEmail())
        stmt.setString(3, company.getCity())
        stmt.setInt(4, stateId)
        stmt.setString(5, company.getCountry())
        stmt.setString(6, company.getCep())
        stmt.setString(7, company.getDescription())
        stmt.setString(8, company.getCnpj())
        if (isUpdate) {
            stmt.setInt(9, company.getId())
        }
        return stmt
    }

    private void insertJobVacancies(Company company) {
        for (JobVacancy jobVacancy in company.jobVacancies) {
            jobVacancyDAO.insertJobVacancy(company.id, jobVacancy)
        }
    }

    private void insertBenefits(Company company) {
        for (Benefit benefit in company.benefits) {
            benefitDAO.insertBenefit(company.id, benefit)
        }
    }

    void insertCompany(Company company) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CompanyQueries.INSERT_COMPANY,
                    Statement.RETURN_GENERATED_KEYS)
            stmt = this.setCompanyStatement(stmt, company, false)
            stmt.executeUpdate()

            ResultSet getCompanyId = stmt.getGeneratedKeys()
            while (stmt.getGeneratedKeys().next()) {
                company.id = getCompanyId.getInt(1)
            }

            this.insertJobVacancies(company)
            this.insertBenefits(company)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    private void updateCompanyBenefits(int id, Company company) {
        List<Integer> benefitsIds = new ArrayList<>()
        sql.eachRow(CompanyQueries.GET_COMPANY_BENEFITS_BY_COMPANY_ID, [id]) {row ->
            benefitsIds << row.getInt("id")
        }

        List<Integer> persistedCompanyBenefits = benefitsIds.findAll { !company.benefits.contains(it) }
        persistedCompanyBenefits.each {companyBenefitsId ->
            benefitDAO.deleteBenefit(companyBenefitsId)
        }

        company.benefits.each {benefit ->
            if (persistedCompanyBenefits.contains(benefit.id)) {
                benefitDAO.updateBenefit(id, benefit as Benefit)
            } else {
                benefitDAO.insertBenefit(id, benefit as Benefit)
            }
        }
    }

    void updateCompany(int id, Company company) {
        company.id = id
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CompanyQueries.UPDATE_COMPANY)
            stmt = this.setCompanyStatement(stmt, company, true)
            stmt.executeUpdate()

            this.updateCompanyBenefits(id, company)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteCompanyById(int id) {
        Person company = new Company()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CompanyQueries.GET_COMPANY_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                company.setId(result.getInt("id"))
            }

            if (company.id != null) {
                stmt = sql.connection.prepareStatement(CompanyQueries.DELETE_COMPANY)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.COMPANY
    }

}
