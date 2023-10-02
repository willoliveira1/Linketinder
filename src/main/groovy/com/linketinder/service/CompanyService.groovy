package com.linketinder.service

import com.linketinder.dao.companydao.CompanyDAO
import com.linketinder.domain.company.Company

class CompanyService implements IBaseService<Company> {

    CompanyDAO companyDAO = new CompanyDAO()

    List<Company> getAll() {
        return companyDAO.getAllCompany()
    }

    Company getById(Integer id) {
        return companyDAO.getCompanyById(id)
    }

    void add(Company company) {
        companyDAO.insertCompany(company)
    }

    void update(Integer id, Company company) {
        companyDAO.insertCompany(company)
    }

    void delete(Integer id) {
        companyDAO.deleteCompanyById(id)
    }

}
