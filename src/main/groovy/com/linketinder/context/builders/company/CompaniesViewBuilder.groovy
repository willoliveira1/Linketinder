package com.linketinder.context.builders.company

import com.linketinder.context.builders.interfaces.ICompaniesViewBuilder
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
    ICompanyValidation companyValidation
    ICompanyService companyService

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

    private void generateCompanyService() {
        this.companyService = new CompanyService(this.companyDAO)
    }

    ICompaniesView build() {
        this.generateCompanyDAO()
        this.generateCompanyValidation()
        this.generateCompanyService()
        return new CompaniesView(this.companyService, this.companyValidation)
    }

}
