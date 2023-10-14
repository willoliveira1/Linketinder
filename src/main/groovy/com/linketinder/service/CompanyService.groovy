package com.linketinder.service

import com.linketinder.dao.companydao.CompanyDAO
import com.linketinder.model.company.Company

class CompanyService implements IBaseService<Company> {

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
