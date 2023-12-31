package com.linketinder.builders.company


import com.linketinder.dao.companydao.RequiredSkillDAO
import com.linketinder.dao.companydao.interfaces.IRequiredSkillDAO
import com.linketinder.database.interfaces.*

class RequiredSkillDAOBuilder implements com.linketinder.builders.interfaces.IBaseDAOBuilder<IRequiredSkillDAO> {

    IConnection connection

    @Override
    RequiredSkillDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    IRequiredSkillDAO build() {
        return new RequiredSkillDAO(this.connection)
    }

}
