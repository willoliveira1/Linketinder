package com.linketinder.context.builder.company

import com.linketinder.context.builder.interfaces.IDAOBuilder
import com.linketinder.dao.companydao.BenefitDAO
import com.linketinder.dao.companydao.interfaces.IBenefitDAO
import com.linketinder.database.interfaces.IConnection
import com.linketinder.database.interfaces.IDBService

class BenefitDAOBuilder implements IDAOBuilder<IBenefitDAO> {

    IDBService dbService
    IConnection connectionFactory

    @Override
    BenefitDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    @Override
    BenefitDAOBuilder withConnection(IConnection connectionFactory) {
        this.connectionFactory = connectionFactory
        return this
    }

    @Override
    IBenefitDAO build() {
        return new BenefitDAO(dbService, connectionFactory)
    }

}
