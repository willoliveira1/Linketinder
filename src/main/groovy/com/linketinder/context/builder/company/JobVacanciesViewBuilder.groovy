package com.linketinder.context.builder.company

import com.linketinder.context.builder.interfaces.IJobVacanciesViewBuilder
import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.database.interfaces.*
import com.linketinder.service.JobVacancyService
import com.linketinder.service.interfaces.IJobVacancyService
import com.linketinder.validation.JobVacancyValidation
import com.linketinder.validation.interfaces.IJobVacancyValidation
import com.linketinder.view.JobVacanciesView
import com.linketinder.view.interfaces.IJobVacanciesView

class JobVacanciesViewBuilder implements IJobVacanciesViewBuilder {

    IConnection connectionFactory
    IDBService dbService

    IJobVacancyDAO jobVacancyDAO
    IJobVacancyValidation jobVacancyValidation
    IJobVacancyService jobVacancyService

    JobVacanciesViewBuilder(IConnection connectionFactory, IDBService dbService) {
        this.connectionFactory = connectionFactory
        this.dbService = dbService
    }

    private void generateJobVacancyDAO() {
        IRequiredSkillDAO requiredSkillDAO = new RequiredSkillDAOBuilder()
            .withDBService(dbService)
            .withConnection(connectionFactory)
            .build()

        this.jobVacancyDAO = new JobVacancyDAOBuilder()
            .withDBService(dbService)
            .withConnection(connectionFactory)
            .withRequiredSkillDAO(requiredSkillDAO)
            .build()
    }

    private void generateJobVacancyValidation() {
        this.jobVacancyValidation = new JobVacancyValidation()
    }

    private void generateJobVacancyService() {
        this.jobVacancyService = new JobVacancyService(jobVacancyDAO)
    }

    IJobVacanciesView build() {
        this.generateJobVacancyDAO()
        this.generateJobVacancyValidation()
        this.generateJobVacancyService()
        return new JobVacanciesView(this.jobVacancyService, this.jobVacancyValidation)
    }

}
