package com.linketinder.view

import com.linketinder.controller.interfaces.ICompanyController
import com.linketinder.model.company.*
import com.linketinder.model.shared.State
import com.linketinder.util.viewtexts.CompanyTexts
import com.linketinder.validation.interfaces.ICompanyValidation
import com.linketinder.view.interfaces.ICompaniesView

class CompaniesView implements ICompaniesView {

    ICompanyController controller
    ICompanyValidation validation
    Readable reader = System.in.newReader()

    CompaniesView(ICompanyController controller, ICompanyValidation validation) {
        this.controller = controller
        this.validation = validation
    }

    void getAllCompanies() {
        println CompanyTexts.ALL_COMPANIES_TEXT
        List<Company> companies = this.controller.getAll()
        companies.each {println it}
    }

    void getCompanyById() {
        Integer id = this.validation.validateId()
        println this.controller.getById(id)
    }

    private List<Benefit> addBenefits() {
        println CompanyTexts.ADD_BENEFIT_TEXT
        List<Benefit> benefits = []
        String input = reader.readLine()
        Boolean addMore = this.validation.validateAddMore(input)

        while (addMore) {
            println CompanyTexts.BENEFIT_TEXT
            String title = reader.readLine()

            Benefit benefit = new Benefit(null, title)
            benefits.add(benefit)

            println CompanyTexts.ADD_MORE_BENEFIT_TEXT
            input = reader.readLine()
            addMore = this.validation.validateAddMore(input)
        }
        return benefits
    }

    private Company populateCompany() {
        println CompanyTexts.NAME_TEXT
        String name = reader.readLine()
        String email = this.validation.validateEmail()
        println CompanyTexts.CITY_TEXT
        String city = reader.readLine()
        State state = this.validation.validateState()
        println CompanyTexts.COUNTRY_TEXT
        String country = reader.readLine()
        String cep = this.validation.validateCep()
        println CompanyTexts.DESCRIPTION_TEXT
        String description = reader.readLine()
        String cnpj = this.validation.validateCnpj()
        List<Benefit> benefits = this.addBenefits()

        Company company = new Company(null, name, email, city, state, country, cep, description, cnpj,
                null, benefits)
        return company
    }

    void addCompany() {
        Company company = this.populateCompany()
        this.controller.add(company)
    }

    void updateCompany() {
        Integer id = this.validation.validateId()
        Company updatedCompany = this.populateCompany()
        updatedCompany.id = this.validation.validateId()
        this.controller.update(id, updatedCompany)
    }

    void removeCompany() {
        Integer id = this.validation.validateId()
        this.controller.delete(id)
    }

}
