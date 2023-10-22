package com.linketinder.context.builder.company

import com.linketinder.context.builder.interfaces.*
import com.linketinder.dao.companydao.JobVacancyDAO
import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.database.interfaces.*

class JobVacancyDAOBuilder implements IJobVacancyDAOBuilder {

    IDBService dbService
    IConnection connectionFactory
    IRequiredSkillDAO requiredSkillDAO

    @Override
    JobVacancyDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    @Override
    JobVacancyDAOBuilder withConnection(IConnection connectionFactory) {
        this.connectionFactory = connectionFactory
        return this
    }

    @Override
    JobVacancyDAOBuilder withRequiredSkillDAO(IRequiredSkillDAO requiredSkillDAO) {
        this.requiredSkillDAO = requiredSkillDAO
        return this
    }

    @Override
    IJobVacancyDAO build() {
        return new JobVacancyDAO(dbService, connectionFactory, requiredSkillDAO)
    }
}
