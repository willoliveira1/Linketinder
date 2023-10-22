package com.linketinder.context.builders.company

import com.linketinder.context.builders.interfaces.IDAOBuilder
import com.linketinder.dao.companydao.RequiredSkillDAO
import com.linketinder.dao.companydao.interfaces.IRequiredSkillDAO
import com.linketinder.database.interfaces.*

class RequiredSkillDAOBuilder implements IDAOBuilder<IRequiredSkillDAO> {

    IConnection connection
    IDBService dbService

    @Override
    RequiredSkillDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    @Override
    RequiredSkillDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    IRequiredSkillDAO build() {
        return new RequiredSkillDAO(this.dbService, this.connection)
    }

}
