package com.linketinder.context.builder.candidate

import com.linketinder.context.builder.interfaces.IDAOBuilder
import com.linketinder.dao.candidatedao.AcademicExperienceDAO
import com.linketinder.dao.candidatedao.interfaces.IAcademicExperienceDAO
import com.linketinder.database.interfaces.*

class AcademicExperienceDAOBuilder implements IDAOBuilder<IAcademicExperienceDAO> {

    IDBService dbService
    IConnection connectionFactory

    AcademicExperienceDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    AcademicExperienceDAOBuilder withConnection(IConnection connectionFactory) {
        this.connectionFactory = connectionFactory
        return this
    }

    IAcademicExperienceDAO build() {
        return new AcademicExperienceDAO(dbService, connectionFactory)
    }

}
