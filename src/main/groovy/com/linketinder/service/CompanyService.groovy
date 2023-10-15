package com.linketinder.service

import com.linketinder.dao.companydao.CompanyDAO
import com.linketinder.model.company.Company
import com.linketinder.service.interfaces.ICompanyService

class CompanyService implements ICompanyService {

    CompanyDAO companyDAO = new CompanyDAO()

    List<Company> getAll() {
        return companyDAO.getAllCompanies()
    }

    Company getById(Integer id) {
        return companyDAO.getCompanyById(id)
    }

    void add(Company company) {
        companyDAO.insertCompany(company)
    }

    void update(Integer id, Company company) {
        companyDAO.updateCompany(id, company)
    }

    void delete(Integer id) {
        companyDAO.deleteCompanyById(id)
    }

}
