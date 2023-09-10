package com.linketinder.view

import com.linketinder.domain.company.Benefit
import com.linketinder.domain.company.Company
import com.linketinder.domain.shared.State
import com.linketinder.service.CompanyService
import com.linketinder.util.ObjectHandler

class CompaniesView {

    CompanyService service = new CompanyService()
    BufferedReader reader = System.in.newReader()

    void getAllCompanies() {
        println "Listagem de Empresas:"
        List<Company> companies = service.getAll()
        companies.each {println it}
    }

    void getCompanyById() {
        println "Qual id da empresa que deseja ver o detalhamento?"
        Integer id
        try {
            id = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "Argumento inválido."
            getCompanyById()
        }
        Company company = service.getById(id)
        if (company == null) {
            println "Empresa não encontrado."
            return
        }
        println company
    }

    void addCompany() {
        List<Company> companies = service.getAll()
        Integer id
        if (companies.isEmpty()) {
            id = 1
        } else {
            id = ObjectHandler.getNextId(companies)
        }
        println "Qual o nome da empresa?"
        String name = reader.readLine()
        println "Qual o email da empresa?"
        String email = reader.readLine()
        println "Qual a cidade da empresa?"
        String city = reader.readLine()
        println "Qual a sigla do estado da empresa?"
        State state
        try {
            String input = reader.readLine()
            state = State.valueOf(input.toUpperCase())
        } catch (IllegalArgumentException e) {
            println "Estado ${state} é inválido."
            return
        }
        println "Qual o país da empresa?"
        String country = reader.readLine()
        println "Qual o cep da empresa?"
        String cep = reader.readLine()
        if (cep.length() < 8 || cep.length() > 10) {
            println "Cep ${cep} é inválido."
            return
        }
        println "Quais informações pessoais o empresa quer informar?"
        String description = reader.readLine()
        println "Qual CNPJ do empresa?"
        String cnpj = reader.readLine()
        if (cnpj.length() < 14 || cnpj.length() > 18) {
            println "Tamanho de CNPJ inválido."
            return
        }
        println "Deseja adicionar benefícios? (S/N)"
        String input = reader.readLine()
        List<Benefit> benefits = []
        if (input.toUpperCase().equals("S")) {
            benefits = addBenefits()
        }

        Company company = new Company(id, name, email, city, state, country, cep, description, cnpj, null, benefits)
        service.add(company)
    }

    void removeCompany() {
        println "Qual o id da empresa a ser removida?"
        Integer id
        try {
            id = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "Argumento inválido."
            removeCompany()
        }
        Company company = service.getById(id)
        if (company == null) {
            println "Empresa não encontrada."
            return
        }
        service.delete(id)
        println "Empresa removida"
    }

    void updateCompany() {
        println "Qual o id da empresa que deseja atualizar"
        Integer id
        try {
            id = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "id inválido."
            return
        }
        service.getById(id)

        println "Qual o nome da empresa?"
        String name = reader.readLine()
        println "Qual o email da empresa?"
        String email = reader.readLine()
        println "Qual a cidade da empresa?"
        String city = reader.readLine()
        println "Qual a sigla do estado da empresa?"
        State state
        try {
            String input = reader.readLine()
            state = State.valueOf(input.toUpperCase())
        } catch (IllegalArgumentException e) {
            println "Estado ${state} é inválido."
            return
        }
        println "Qual o país da empresa?"
        String country = reader.readLine()
        println "Qual o cep da empresa?"
        String cep = reader.readLine()
        if (cep.length() < 8 || cep.length() > 10) {
            println "Cep ${cep} é inválido."
            return
        }
        println "Quais informações pessoais o empresa quer informar?"
        String description = reader.readLine()
        println "Qual CNPJ do empresa?"
        String cnpj = reader.readLine()
        if (cnpj.length() < 14 || cnpj.length() > 18) {
            println "Tamanho de CNPJ inválido."
            return
        }
        println "Deseja adicionar benefícios? (S/N)"
        String input = reader.readLine()
        List<Benefit> benefits = []
        if (input.toUpperCase().equals("S")) {
            benefits = addBenefits()
        }

        Company updatedCompany = new Company(id, name, email, city, state, country, cep, description, cnpj, null, benefits)
        service.update(id, updatedCompany)
    }

    List<Benefit> addBenefits() {
        List<Benefit> benefits = []
        Integer addMore = 1

        while (addMore) {
            Integer id
            benefits.size() == 0 ? 1 : (benefits.size() + 1)
            println "Qual o benefício?"
            String title = reader.readLine()

            Benefit benefit = new Benefit(id, title)
            benefits.add(benefit)

            println "Deseja adicionar mais benefícios? (S/N)"
            String input = reader.readLine()
            if (input.toUpperCase().equals("S")) {
                addMore = 1
            } else {
                addMore = 0
            }
        }
        return benefits
    }

}
