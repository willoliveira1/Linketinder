package com.linketinder.dao.companydao.interfaces

import com.linketinder.model.company.Company

interface ICompanyDAO {

    List<Company> getAllCompanies()
    Company getCompanyById(int id)
    void insertCompany(Company company)
    void updateCompany(int id, Company company)
    void deleteCompanyById(int id)

}
