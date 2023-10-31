package com.linketinder.context.builders.company

import com.linketinder.context.builders.interfaces.IJobVacanciesViewBuilder
import com.linketinder.controller.JobVacancyController
import com.linketinder.controller.interfaces.IJobVacancyController
import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.database.interfaces.*
import com.linketinder.service.JobVacancyService
import com.linketinder.service.interfaces.IJobVacancyService
import com.linketinder.validation.JobVacancyValidation
import com.linketinder.validation.interfaces.IJobVacancyValidation
import com.linketinder.view.JobVacanciesView
import com.linketinder.view.interfaces.IJobVacanciesView

class JobVacanciesViewBuilder implements IJobVacanciesViewBuilder {

    IConnection connection
    IDBService dbService

    IJobVacancyDAO jobVacancyDAO
    IJobVacancyController jobVacancyController
    IJobVacancyValidation jobVacancyValidation

    JobVacanciesViewBuilder(IConnection connection, IDBService dbService) {
        this.connection = connection
        this.dbService = dbService
    }

    private void generateJobVacancyDAO() {
        IRequiredSkillDAO requiredSkillDAO = new RequiredSkillDAOBuilder()
            .withDBService(this.dbService)
            .withConnection(this.connection)
            .build()

        this.jobVacancyDAO = new JobVacancyDAOBuilder()
            .withDBService(this.dbService)
            .withConnection(this.connection)
            .withRequiredSkillDAO(requiredSkillDAO)
            .build()
    }

    private void generateJobVacancyValidation() {
        this.jobVacancyValidation = new JobVacancyValidation()
    }

    private void generateJobVacancyController() {
        this.generateJobVacancyDAO()
        IJobVacancyService jobVacancyService = new JobVacancyService(this.jobVacancyDAO)
        this.jobVacancyController = new JobVacancyController(jobVacancyService)
    }

    IJobVacanciesView build() {
        this.generateJobVacancyValidation()
        this.generateJobVacancyController()
        return new JobVacanciesView(this.jobVacancyController, this.jobVacancyValidation)
    }

}
