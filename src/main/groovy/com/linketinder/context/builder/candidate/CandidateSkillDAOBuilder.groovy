package com.linketinder.context.builder.candidate

import com.linketinder.context.builder.interfaces.IDAOBuilder
import com.linketinder.dao.candidatedao.CandidateSkillDAO
import com.linketinder.dao.candidatedao.interfaces.ICandidateSkillDAO
import com.linketinder.database.interfaces.*

class CandidateSkillDAOBuilder implements IDAOBuilder<ICandidateSkillDAO> {

    IDBService dbService
    IConnection connectionFactory

    @Override
    CandidateSkillDAOBuilder withDBService(IDBService dbService) {
        this.dbService = dbService
        return this
    }

    @Override
    CandidateSkillDAOBuilder withConnection(IConnection connectionFactory) {
        this.connectionFactory = connectionFactory
        return this
    }

    @Override
    ICandidateSkillDAO build() {
        return new CandidateSkillDAO(dbService, connectionFactory)
    }

}
