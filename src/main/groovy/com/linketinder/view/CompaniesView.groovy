package com.linketinder.view

import com.linketinder.model.company.Benefit
import com.linketinder.model.company.Company
import com.linketinder.model.shared.State
import com.linketinder.service.interfaces.ICompanyService
import com.linketinder.util.viewtexts.CompanyTexts
import com.linketinder.validation.interfaces.ICompanyValidation
import com.linketinder.view.interfaces.ICompaniesView

class CompaniesView implements ICompaniesView {

    ICompanyService service
    ICompanyValidation validation
    Readable reader = System.in.newReader()

    CompaniesView(ICompanyService service, ICompanyValidation validation) {
        this.service = service
        this.validation = validation
    }

    void getAllCompanies() {
        println CompanyTexts.ALL_COMPANIES_TEXT
        List<Company> companies = service.getAll()
        companies.each {println it}
    }

    void getCompanyById() {
        Integer id = validation.validateId()
        println service.getById(id)
    }

    List<Benefit> addBenefits() {
        println CompanyTexts.ADD_BENEFIT_TEXT
        List<Benefit> benefits = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println CompanyTexts.BENEFIT_TEXT
            String title = reader.readLine()

            Benefit benefit = new Benefit(null, title)
            benefits.add(benefit)

            println CompanyTexts.ADD_MORE_BENEFIT_TEXT
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return benefits
    }

    void addCompany() {
        println CompanyTexts.NAME_TEXT
        String name = reader.readLine()
        String email = validation.validateEmail()
        println CompanyTexts.CITY_TEXT
        String city = reader.readLine()
        State state = validation.validateState()
        println CompanyTexts.COUNTRY_TEXT
        String country = reader.readLine()
        String cep = validation.validateCep()
        println CompanyTexts.DESCRIPTION_TEXT
        String description = reader.readLine()
        String cnpj = validation.validateCnpj()
        List<Benefit> benefits = addBenefits()

        Company company = new Company(null, name, email, city, state, country, cep, description, cnpj,
                null, benefits)
        service.add(company)
    }

    void updateCompany() {
        Integer id = validation.validateId()
        println CompanyTexts.NAME_TEXT
        String name = reader.readLine()
        String email = validation.validateEmail()
        println CompanyTexts.CITY_TEXT
        String city = reader.readLine()
        State state = validation.validateState()
        println CompanyTexts.COUNTRY_TEXT
        String country = reader.readLine()
        String cep = validation.validateCep()
        println CompanyTexts.DESCRIPTION_TEXT
        String description = reader.readLine()
        String cnpj = validation.validateCnpj()
        List<Benefit> benefits = addBenefits()

        Company updatedCompany = new Company(id, name, email, city, state, country, cep, description, cnpj,
                null, benefits)
        service.update(id, updatedCompany)
    }

    void removeCompany() {
        Integer id = validation.validateId()
        service.delete(id)
    }

}
