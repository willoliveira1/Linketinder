package com.linketinder.context.builders.candidate

import com.linketinder.context.builders.interfaces.IBaseDAOBuilder
import com.linketinder.dao.candidatedao.LanguageDAO
import com.linketinder.dao.candidatedao.interfaces.ILanguageDAO
import com.linketinder.database.interfaces.*

class LanguageDAOBuilder implements IBaseDAOBuilder<ILanguageDAO> {

    IConnection connection

    @Override
    LanguageDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    ILanguageDAO build() {
        return new LanguageDAO(this.connection)
    }

}
