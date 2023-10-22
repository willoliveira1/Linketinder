package com.linketinder.context.builder.candidate

import com.linketinder.context.builder.interfaces.IDAOBuilder
import com.linketinder.dao.candidatedao.WorkExperienceDAO
import com.linketinder.dao.candidatedao.interfaces.IWorkExperienceDAO
import com.linketinder.database.interfaces.*

class WorkExperienceDAOBuilder implements IDAOBuilder<IWorkExperienceDAO> {

    IDBService dbService
    IConnection connectionFactory

    @Override
    WorkExperienceDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    @Override
    WorkExperienceDAOBuilder withConnection(IConnection connectionFactory) {
        this.connectionFactory = connectionFactory
        return this
    }

    @Override
    IWorkExperienceDAO build() {
        return new WorkExperienceDAO(dbService, connectionFactory)
    }

}
