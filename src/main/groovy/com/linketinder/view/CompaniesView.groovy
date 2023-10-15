package com.linketinder.view

import com.linketinder.model.company.Benefit
import com.linketinder.model.company.Company
import com.linketinder.model.shared.State
import com.linketinder.service.CompanyService
import com.linketinder.service.interfaces.IBaseService
import com.linketinder.validation.CompanyValidation
import com.linketinder.validation.IPersonValidation

class CompaniesView {

    IBaseService service = new CompanyService()
    BufferedReader reader = System.in.newReader()
    IPersonValidation validation = new CompanyValidation()

    void getAllCompanies() {
        println "Listagem de Empresas:"
        List<Company> companies = service.getAll()
        companies.each {println it}
    }

    void getCompanyById() {
        Integer id = validation.validateId()
        println service.getById(id)
    }

    List<Benefit> addBenefits() {
        println "Deseja adicionar benefícios? (S/N)"
        List<Benefit> benefits = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println "Qual o benefício?"
            String title = reader.readLine()

            Benefit benefit = new Benefit(null, title)
            benefits.add(benefit)

            println "Deseja adicionar mais benefícios? (S/N)"
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return benefits
    }

    void addCompany() {
        println "Qual o nome da empresa?"
        String name = reader.readLine()
        String email = validation.validateEmail()
        println "Qual a cidade da empresa?"
        String city = reader.readLine()
        State state = validation.validateState()
        println "Qual o país da empresa?"
        String country = reader.readLine()
        println "Qual o cep da empresa?"
        String cep = validation.validateCep()
        println "Quais informações pessoais o empresa quer informar?"
        String description = reader.readLine()
        String cnpj = validation.validateCnpj()
        List<Benefit> benefits = addBenefits()

        Company company = new Company(null, name, email, city, state, country, cep, description, cnpj,
                null, benefits)
        service.add(company)
    }

    void updateCompany() {
        Integer id = validation.validateId()
        println "Qual o nome da empresa?"
        String name = reader.readLine()
        println "Qual o email da empresa?"
        String email = reader.readLine()
        println "Qual a cidade da empresa?"
        String city = reader.readLine()
        State state = validation.validateState()
        println "Qual o país da empresa?"
        String country = reader.readLine()
        String cep = validation.validateCep()
        println "Quais informações sobre a empresa quer informar?"
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
