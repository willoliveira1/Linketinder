package com.linketinder.context.builder.company

import com.linketinder.context.builder.interfaces.IDAOBuilder
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
