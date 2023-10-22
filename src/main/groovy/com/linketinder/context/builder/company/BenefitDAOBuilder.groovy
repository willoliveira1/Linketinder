package com.linketinder.context.builder.company

import com.linketinder.context.builder.interfaces.IDAOBuilder
import com.linketinder.dao.companydao.BenefitDAO
import com.linketinder.dao.companydao.interfaces.IBenefitDAO
import com.linketinder.database.interfaces.IConnection
import com.linketinder.database.interfaces.IDBService

class BenefitDAOBuilder implements IDAOBuilder<IBenefitDAO> {

    IConnection connection
    IDBService dbService

    @Override
    BenefitDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    @Override
    BenefitDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    IBenefitDAO build() {
        return new BenefitDAO(this.dbService, this.connection)
    }

}
