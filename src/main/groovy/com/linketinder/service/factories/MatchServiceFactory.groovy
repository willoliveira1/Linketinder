package com.linketinder.service.factories

import com.linketinder.builders.match.CandidateMatchDAOBuilder
import com.linketinder.builders.match.CompanyMatchDAOBuilder
import com.linketinder.builders.match.MatchDAOBuilder
import com.linketinder.dao.matchdao.interfaces.*
import com.linketinder.database.ConnectionFactory
import com.linketinder.database.interfaces.IConnection
import com.linketinder.service.MatchService
import com.linketinder.service.interfaces.IMatchService

class MatchServiceFactory {

    static IMatchService createMatchService() {

        IConnection connection = ConnectionFactory.createConnection("POSTGRESQL")

        IMatchDAO matchDAO = new MatchDAOBuilder()
            .withConnection(connection)
            .build()

        ICandidateMatchDAO candidateMatchDAO = new CandidateMatchDAOBuilder()
            .withConnection(connection)
            .withMatchDAO(matchDAO)
            .build()

        ICompanyMatchDAO companyMatchDAO = new CompanyMatchDAOBuilder()
            .withConnection(connection)
            .withMatchDAO(matchDAO)
            .build()

        return new MatchService(matchDAO, candidateMatchDAO, companyMatchDAO)
    }

}
