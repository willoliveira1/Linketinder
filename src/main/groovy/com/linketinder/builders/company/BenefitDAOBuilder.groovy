package com.linketinder.builders.company


import com.linketinder.dao.companydao.BenefitDAO
import com.linketinder.dao.companydao.interfaces.IBenefitDAO
import com.linketinder.database.interfaces.IConnection

class BenefitDAOBuilder implements com.linketinder.builders.interfaces.IBaseDAOBuilder<IBenefitDAO> {

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
