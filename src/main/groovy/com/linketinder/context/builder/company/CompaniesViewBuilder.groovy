package com.linketinder.context.builder.company

import com.linketinder.context.builder.interfaces.ICompaniesViewBuilder
import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.database.interfaces.*
import com.linketinder.service.CompanyService
import com.linketinder.service.interfaces.ICompanyService
import com.linketinder.validation.CompanyValidation
import com.linketinder.validation.interfaces.ICompanyValidation
import com.linketinder.view.CompaniesView
import com.linketinder.view.interfaces.ICompaniesView

class CompaniesViewBuilder implements ICompaniesViewBuilder {

    IConnection connectionFactory
    IDBService dbService

    ICompanyDAO companyDAO
    ICompanyValidation companyValidation
    ICompanyService companyService

    CompaniesViewBuilder(IConnection connectionFactory, IDBService dbService) {
        this.connectionFactory = connectionFactory
        this.dbService = dbService
    }

    private void generateCompanyDAO() {
        IRequiredSkillDAO requiredSkillDAO = new RequiredSkillDAOBuilder()
            .withDBService(dbService)
            .withConnection(connectionFactory)
            .build()

        IJobVacancyDAO jobVacancyDAO = new JobVacancyDAOBuilder()
            .withDBService(dbService)
            .withConnection(connectionFactory)
            .withRequiredSkillDAO(requiredSkillDAO)
            .build()

        IBenefitDAO benefitDAO = new BenefitDAOBuilder()
            .withDBService(dbService)
            .withConnection(connectionFactory)
            .build()

        this.companyDAO = new CompanyDAOBuilder()
            .withDBService(dbService)
            .withConnection(connectionFactory)
            .withBenefitDAO(benefitDAO)
            .withJobVacancyDAO(jobVacancyDAO)
            .withRequiredSkillDAO(requiredSkillDAO)
            .build()
    }

    private void generateCompanyValidation() {
        this.companyValidation = new CompanyValidation()
    }

    private void generateCompanyService() {
        this.companyService = new CompanyService(companyDAO)
    }

    ICompaniesView build() {
        this.generateCompanyDAO()
        this.generateCompanyValidation()
        this.generateCompanyService()
        return new CompaniesView(this.companyService, this.companyValidation)
    }

}
