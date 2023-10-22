package com.linketinder.context.builder.candidate

import com.linketinder.context.builder.interfaces.IDAOBuilder
import com.linketinder.dao.candidatedao.CandidateSkillDAO
import com.linketinder.dao.candidatedao.interfaces.ICandidateSkillDAO
import com.linketinder.database.interfaces.*

class CandidateSkillDAOBuilder implements IDAOBuilder<ICandidateSkillDAO> {

    IConnection connection
    IDBService dbService

    @Override
    CandidateSkillDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    @Override
    CandidateSkillDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    ICandidateSkillDAO build() {
        return new CandidateSkillDAO(this.dbService, this.connection)
    }

}
