package com.linketinder.context.builders.candidate

import com.linketinder.context.builders.interfaces.IDAOBuilder
import com.linketinder.dao.candidatedao.WorkExperienceDAO
import com.linketinder.dao.candidatedao.interfaces.IWorkExperienceDAO
import com.linketinder.database.interfaces.*

class WorkExperienceDAOBuilder implements IDAOBuilder<IWorkExperienceDAO> {

    IConnection connection
    IDBService dbService

    @Override
    WorkExperienceDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    @Override
    WorkExperienceDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    IWorkExperienceDAO build() {
        return new WorkExperienceDAO(this.dbService, this.connection)
    }

}
