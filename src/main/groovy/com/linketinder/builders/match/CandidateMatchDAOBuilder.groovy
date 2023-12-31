package com.linketinder.builders.match


import com.linketinder.dao.matchdao.CandidateMatchDAO
import com.linketinder.dao.matchdao.interfaces.*
import com.linketinder.database.interfaces.IConnection

class CandidateMatchDAOBuilder implements com.linketinder.builders.interfaces.ICandidateMatchDAOBuilder {

    IConnection connection
    IMatchDAO matchDAO

    @Override
    CandidateMatchDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    CandidateMatchDAOBuilder withMatchDAO(IMatchDAO matchDAO) {
        this.matchDAO = matchDAO
        return this
    }

    @Override
    ICandidateMatchDAO build() {
        return new CandidateMatchDAO(this.matchDAO, this.connection)
    }

}
