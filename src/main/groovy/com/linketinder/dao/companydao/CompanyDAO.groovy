package com.linketinder.dao.companydao

import com.linketinder.database.DatabaseFactory
import com.linketinder.database.DBService
import com.linketinder.model.company.Benefit
import com.linketinder.model.company.Company
import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.model.shared.Person
import com.linketinder.model.shared.State
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CompanyDAO {

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()
    JobVacancyDAO jobVacancyDAO = new JobVacancyDAO()
    BenefitDAO benefitDAO = new BenefitDAO()

    private List<Company> populateCompanies(String query) {
        List<Company> companies = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
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
            companies.add(company)
        }
        return companies
    }

    List<Company> getAllCompany() {
        List<Company> companies = new ArrayList<>()
        String query = """
            SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cnpj
                FROM companies AS c,
                     states AS s
                WHERE c.state_id = s.id
                ORDER BY c.id
        """
        try {
            companies = populateCompanies(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return companies
    }

    private Company populateCompany(String query) {
        Person company = new Company()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
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
        }
        return company
    }

    Company getCompanyById(int id) {
        Person company = new Company()
        String query = """
            SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cnpj
                FROM companies AS c,
                     states AS s
                WHERE c.state_id = s.id
                AND c.id=${id}
        """
        try {
            company = populateCompany(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return company
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
        String insertCompany = "INSERT INTO companies (name, email, city, state_id, country, cep, description, " +
                "cnpj) VALUES (?,?,?,?,?,?,?,?)"
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(insertCompany, Statement.RETURN_GENERATED_KEYS)
            stmt.setString(1, company.name)
            stmt.setString(2, company.getEmail())
            stmt.setString(3, company.getCity())
            stmt.setString(5, company.getCountry())
            stmt.setString(6, company.getCep())
            stmt.setString(7, company.getDescription())
            stmt.setString(8, company.getCnpj())

            int stateId = dbService.idFinder("states", "acronym", company.getState().toString())
            stmt.setInt(4, stateId)

            stmt.executeUpdate()

            ResultSet getCompanyId = stmt.getGeneratedKeys()
            while (stmt.getGeneratedKeys().next()) {
                company.id = getCompanyId.getInt(1)
            }

            insertJobVacancies(company)
            insertBenefits(company)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    private void updateCompanyBenefits(int id, Company company) {
        List<Integer> benefitsIds = new ArrayList<>()
        sql.eachRow("SELECT id FROM company_benefits WHERE company_id=${id}") {row ->
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
        String updateCompany = """
            UPDATE companies
                SET name=?, email=?, city=?, state_id=?, country=?, cep=?, description=?, cnpj=?
                WHERE id=${id}
        """
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(updateCompany)
            stmt.setString(1, company.name)
            stmt.setString(2, company.getEmail())
            stmt.setString(3, company.getCity())
            stmt.setString(5, company.getCountry())
            stmt.setString(6, company.getCep())
            stmt.setString(7, company.getDescription())
            stmt.setString(8, company.getCnpj())

            int stateId = dbService.idFinder("states", "acronym", company.getState().toString())
            stmt.setInt(4, stateId)

            stmt.executeUpdate()

            updateCompanyBenefits(id, company)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteCompanyById(int id) {
        Person company = new Company()
        String query = "SELECT * FROM companies WHERE id=${id};"
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                company.setId(result.getInt("id"))
            }

            if (company.id != null) {
                query = "DELETE FROM companies WHERE id=${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        println "Empresa não encontrada."
    }

}
