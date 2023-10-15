package com.linketinder.dao.companydao

import com.linketinder.database.DatabaseFactory
import com.linketinder.database.DBService
import com.linketinder.model.company.Benefit
import com.linketinder.model.company.Company
import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.model.shared.Person
import com.linketinder.model.shared.State
import com.linketinder.util.ErrorMessages
import com.linketinder.util.NotFoundMessages
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CompanyDAO {

    private final String GET_ALL_COMPANIES = "SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cnpj FROM companies AS c, states AS s WHERE c.state_id = s.id ORDER BY c.id"
    private final String GET_COMPANY_BY_ID = "SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cnpj FROM companies AS c, states AS s WHERE c.state_id = s.id AND c.id=?"
    private final String GET_COMPANY_BENEFITS_BY_COMPANY_ID = "SELECT id FROM company_benefits WHERE company_id=?"
    private final String INSERT_COMPANY = "INSERT INTO companies (name, email, city, state_id, country, cep, description, cnpj) VALUES (?,?,?,?,?,?,?,?)"
    private final String UPDATE_COMPANY = "UPDATE companies SET name=?, email=?, city=?, state_id=?, country=?, cep=?, description=?, cnpj=? WHERE id=?"
    private final String DELETE_COMPANY = "DELETE FROM companies WHERE id=?"

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()
    JobVacancyDAO jobVacancyDAO = new JobVacancyDAO()
    BenefitDAO benefitDAO = new BenefitDAO()

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
            companies = this.populateCompanies(GET_ALL_COMPANIES)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return companies
    }

    Company getCompanyById(int id) {
        Person company = new Company()
        try {
            company = this.populateCompany(GET_COMPANY_BY_ID, id)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return company
    }

    private setCompanyStatement(PreparedStatement stmt, Company company) {
        int stateId = dbService.idFinder("states", "acronym", company.getState().toString())
        stmt.setString(1, company.name)
        stmt.setString(2, company.getEmail())
        stmt.setString(3, company.getCity())
        stmt.setInt(4, stateId)
        stmt.setString(5, company.getCountry())
        stmt.setString(6, company.getCep())
        stmt.setString(7, company.getDescription())
        stmt.setString(8, company.getCnpj())
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
            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_COMPANY, Statement.RETURN_GENERATED_KEYS)
            stmt = this.setCompanyStatement(stmt, company)
            stmt.executeUpdate()

            ResultSet getCompanyId = stmt.getGeneratedKeys()
            while (stmt.getGeneratedKeys().next()) {
                company.id = getCompanyId.getInt(1)
            }

            this.insertJobVacancies(company)
            this.insertBenefits(company)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    private void updateCompanyBenefits(int id, Company company) {
        List<Integer> benefitsIds = new ArrayList<>()
        sql.eachRow(GET_COMPANY_BENEFITS_BY_COMPANY_ID, [id]) {row ->
            benefitsIds << row.getInt("id")
        }

        List<Integer> persistedCompanyBenefits = benefitsIds.findAll { !company.benefits.contains(it) }
        persistedCompanyBenefits.each {companyBenefitsId ->
            benefitDAO.deleteBenefit(companyBenefitsId)
        }

        company.benefits.each {benefit ->
            if (persistedCompanyBenefits.contains(benefit.id)) {
                benefitDAO.updateBenefit(id, benefit)
            } else {
                benefitDAO.insertBenefit(id, benefit)
            }
        }
    }

    void updateCompany(int id, Company company) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_COMPANY)
            stmt = this.setCompanyStatement(stmt, company)
            stmt.executeUpdate()

            this.updateCompanyBenefits(id, company)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteCompanyById(int id) {
        Person company = new Company()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(GET_COMPANY_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                company.setId(result.getInt("id"))
            }

            if (company.id != null) {
                stmt = sql.connection.prepareStatement(DELETE_COMPANY)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.COMPANY
    }

}
