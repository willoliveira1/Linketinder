package com.linketinder.context.builders.company

import com.linketinder.context.builders.interfaces.IBaseDAOBuilder
import com.linketinder.dao.companydao.BenefitDAO
import com.linketinder.dao.companydao.interfaces.IBenefitDAO
import com.linketinder.database.interfaces.IConnection

class BenefitDAOBuilder implements IBaseDAOBuilder<IBenefitDAO> {

    IConnection connection

    @Override
    BenefitDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    IBenefitDAO build() {
        return new BenefitDAO(this.connection)
    }

}
