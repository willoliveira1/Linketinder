package com.linketinder.context.builder.company

import com.linketinder.context.builder.interfaces.IDAOBuilder
import com.linketinder.dao.companydao.RequiredSkillDAO
import com.linketinder.dao.companydao.interfaces.IRequiredSkillDAO
import com.linketinder.database.interfaces.*

class RequiredSkillDAOBuilder implements IDAOBuilder<IRequiredSkillDAO> {

    IDBService dbService
    IConnection connectionFactory

    @Override
    RequiredSkillDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    @Override
    RequiredSkillDAOBuilder withConnection(IConnection connectionFactory) {
        this.connectionFactory = connectionFactory
        return this
    }

    @Override
    IRequiredSkillDAO build() {
        return new RequiredSkillDAO(dbService, connectionFactory)
    }

}
