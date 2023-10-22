package com.linketinder.context.builders.candidate

import com.linketinder.context.builders.interfaces.IDAOBuilder
import com.linketinder.dao.candidatedao.AcademicExperienceDAO
import com.linketinder.dao.candidatedao.interfaces.IAcademicExperienceDAO
import com.linketinder.database.interfaces.*

class AcademicExperienceDAOBuilder implements IDAOBuilder<IAcademicExperienceDAO> {

    IConnection connection
    IDBService dbService

    AcademicExperienceDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    AcademicExperienceDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    IAcademicExperienceDAO build() {
        return new AcademicExperienceDAO(this.dbService, this.connection)
    }

}
