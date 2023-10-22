package com.linketinder.context.builder.candidate

import com.linketinder.context.builder.interfaces.IDAOBuilder
import com.linketinder.dao.candidatedao.LanguageDAO
import com.linketinder.dao.candidatedao.interfaces.ILanguageDAO
import com.linketinder.database.interfaces.*

class LanguageDAOBuilder implements IDAOBuilder<ILanguageDAO> {

    IDBService dbService
    IConnection connectionFactory

    @Override
    LanguageDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    @Override
    LanguageDAOBuilder withConnection(IConnection connectionFactory) {
        this.connectionFactory = connectionFactory
        return this
    }

    @Override
    ILanguageDAO build() {
        return new LanguageDAO(dbService, connectionFactory)
    }

}
