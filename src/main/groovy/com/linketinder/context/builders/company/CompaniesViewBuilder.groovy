package com.linketinder.context.builders.company

import com.linketinder.context.builders.interfaces.ICompaniesViewBuilder
import com.linketinder.controller.CompanyController
import com.linketinder.controller.interfaces.ICompanyController
import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.database.interfaces.*
import com.linketinder.service.CompanyService
import com.linketinder.service.interfaces.ICompanyService
import com.linketinder.validation.CompanyValidation
import com.linketinder.validation.interfaces.ICompanyValidation
import com.linketinder.view.CompaniesView
import com.linketinder.view.interfaces.ICompaniesView

class CompaniesViewBuilder implements ICompaniesViewBuilder {

    IConnection connection
    IDBService dbService

    ICompanyDAO companyDAO
    ICompanyController companyController
    ICompanyValidation companyValidation

    CompaniesViewBuilder(IConnection connection, IDBService dbService) {
        this.connection = connection
        this.dbService = dbService
    }

    private void generateCompanyDAO() {
        IRequiredSkillDAO requiredSkillDAO = new RequiredSkillDAOBuilder()
            .withDBService(this.dbService)
            .withConnection(this.connection)
            .build()

        IJobVacancyDAO jobVacancyDAO = new JobVacancyDAOBuilder()
            .withDBService(this.dbService)
            .withConnection(this.connection)
            .withRequiredSkillDAO(requiredSkillDAO)
            .build()

        IBenefitDAO benefitDAO = new BenefitDAOBuilder()
            .withDBService(this.dbService)
            .withConnection(this.connection)
            .build()

        this.companyDAO = new CompanyDAOBuilder()
            .withDBService(this.dbService)
            .withConnection(this.connection)
            .withBenefitDAO(benefitDAO)
            .withJobVacancyDAO(jobVacancyDAO)
            .withRequiredSkillDAO(requiredSkillDAO)
            .build()
    }

    private void generateCompanyValidation() {
        this.companyValidation = new CompanyValidation()
    }

    private void generateCompanyController() {
        ICompanyService companyService = new CompanyService(this.companyDAO)
        this.companyController = new CompanyController(companyService)
    }

    ICompaniesView build() {
        this.generateCompanyDAO()
        this.generateCompanyValidation()
        this.generateCompanyController()
        return new CompaniesView(this.companyController, this.companyValidation)
    }

}
