package com.linketinder.view.interfaces

import com.linketinder.model.company.Benefit

interface ICompaniesView {

    void getAllCompanies()
    void getCompanyById()
    List<Benefit> addBenefits()
    void addCompany()
    void updateCompany()
    void removeCompany()

}
