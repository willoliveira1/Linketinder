package com.linketinder.fileprocessor

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import com.linketinder.domain.company.Company
import com.linketinder.util.CompaniesGenerator

class CompaniesProcessor implements Processor<Company> {

    String filePath = "../../../resources/empresas.json"

    void writeFile(List<Company> companies) {
        JsonBuilder companiesBuilder = new JsonBuilder(companies)
        new File(filePath).write(companiesBuilder.toPrettyString())
    }

    Company readById(Integer id) {
        List<Company> companies = readFile()
        Company company = companies.find {it.id == id}
        return company
    }

    List<Company> readFile() {
        File file = new File(filePath)
        if (file.exists()) {
            JsonSlurper slurper = new JsonSlurper()
            List<Company> companies = slurper.parse(new File(filePath))
            return companies
        }

        CompaniesGenerator generator = new CompaniesGenerator()
        generator.runGenerator()
        readFile()
    }

    void add(Company company) {
        List<Company> companies = readFile()
        companies.add(company)
        writeFile(companies)
    }

    void update(Integer id, Company updatedCompany) {
        List<Company> companies = readFile()
        Integer index = companies.indexOf(companies.find {it.id == id})
        companies.set(index, updatedCompany)
        writeFile(companies)
    }

    void delete(Integer id) {
        List<Company> companies = readFile()
        companies.removeIf {it.id == id}
        writeFile(companies)
    }

}
