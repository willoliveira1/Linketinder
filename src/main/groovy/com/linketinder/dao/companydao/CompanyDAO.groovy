package com.linketinder.dao.companydao

import com.linketinder.database.DBService
import com.linketinder.database.DatabaseFactory
import com.linketinder.domain.company.Benefit
import com.linketinder.domain.company.Company
import com.linketinder.domain.jobvacancy.JobVacancy
import com.linketinder.domain.shared.Person
import com.linketinder.domain.shared.State

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

    List<Company> getAllCompany() {
        List<Company> companies = new ArrayList<>()

        try {
            String query = """
                SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cnpj
                    FROM companies AS c,
                         states AS s
                    WHERE c.state_id = s.id
            """
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

                company.setJobVacancies(jobVacancyDAO.getJobVacancyByCompanyId(company.id))

                companies.add(company)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return companies
    }

    Company getCompanyById(int id) {
        Person company = new Company()

        try {
            String query = """
                SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cnpj
                    FROM companies AS c,
                         states AS s
                    WHERE c.state_id = s.id
                    AND c.id=${id}
            """
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

                company.setBenefits(benefitDAO.getBenefitsByCompanyId(id))
                company.setJobVacancies(jobVacancyDAO.getJobVacancyByCompanyId(id))
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return company
    }

    void insertCompany(Company company) {
        try {
            String insertCompany = "INSERT INTO companies (name, email, city, state_id, country, cep, description, " +
                    "cnpj) VALUES (?,?,?,?,?,?,?,?)"
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

            int companyId = -1
            ResultSet getCompanyId = stmt.getGeneratedKeys()
            while (stmt.getGeneratedKeys().next()) {
                companyId = getCompanyId.getInt(1)
            }

            if (companyId != -1) {
                for (JobVacancy jobVacancy in company.jobVacancies) {
                    jobVacancyDAO.insertJobVacancy(jobVacancy, companyId)
                }
                for (Benefit benefit in company.benefits) {
                    benefitDAO.insertBenefit(benefit, companyId)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateCompany(int id, Company company) {
        try {
            String updateCompany = """
                UPDATE companies
                    SET name=?, email=?, city=?, state_id=?, country=?, cep=?, description=?, cnpj=?
                    WHERE id=${id}
            """
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

            updateCompanyJobVacancies(id, company)
            updateCompanyBenefits(id, company)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteCompanyById(int id) {
        Person company = new Company()

        try {
            String query = "SELECT * FROM companies WHERE id=${id};"
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                company.setId(result.getInt("id"))
            }

            if (company.id != null) {
                query = "DELETE FROM companies WHERE id=${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
            } else {
                println "[Exclusão] Empresa não encontrada."
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateCompanyJobVacancies(int id, Company company) {
        List<Integer> jobVacanciesIds = new ArrayList<>()
        sql.eachRow("SELECT id FROM job_vacancies WHERE company_id=${id}") {row ->
            jobVacanciesIds << row.getInt("id")
        }

        List<Integer> persistedJobVacancies = jobVacanciesIds.findAll { !company.jobVacancies.contains(it) }
        persistedJobVacancies.each {jobVacanciesId ->
            jobVacancyDAO.deleteJobVacancy(jobVacanciesId)
        }

        company.jobVacancies.each {jobVacancy ->
            if (persistedJobVacancies.contains(jobVacancy.id)) {
                jobVacancyDAO.updateJobVacancy(jobVacancy, id)
            } else {
                jobVacancyDAO.insertJobVacancy(jobVacancy, id)
            }
        }
    }

    void updateCompanyBenefits(int id, Company company) {
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
                benefitDAO.updateBenefit(benefit, id)
            } else {
                benefitDAO.insertBenefit(benefit, id)
            }
        }
    }

}
