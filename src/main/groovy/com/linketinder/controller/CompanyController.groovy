package com.linketinder.controller

import com.linketinder.controller.interfaces.ICompanyController
import com.linketinder.model.company.Company
import com.linketinder.service.interfaces.ICompanyService

class CompanyController implements ICompanyController {

    ICompanyService service

    CompanyController(ICompanyService service) {
        this.service = service
    }

    List<Company> getAll() {
        List<Company> companies = this.service.getAll()
        return companies
    }

    Company getById(int id) {
        Company company = this.service.getById(id)
        return company
    }

    void add(Company company) {
        this.service.add(company)
    }

    void update(int id, Company company) {
        this.service.update(id, company)
    }

    void delete(int id) {
        this.service.delete(id)
    }

}
