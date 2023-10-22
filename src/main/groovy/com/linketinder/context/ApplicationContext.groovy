package com.linketinder.context

import com.linketinder.context.builder.candidate.*
import com.linketinder.context.builder.company.*
import com.linketinder.context.builder.interfaces.*
import com.linketinder.dao.matchdao.*
import com.linketinder.dao.matchdao.interfaces.*
import com.linketinder.database.*
import com.linketinder.database.interfaces.*
import com.linketinder.service.*
import com.linketinder.service.interfaces.*
import com.linketinder.validation.*
import com.linketinder.validation.interfaces.*
import com.linketinder.view.*
import com.linketinder.view.interfaces.*

class ApplicationContext {

    IConnection connectionFactory = ConnectionFactory.createConnection("POSTGRESQL")
    IDBService dbService = new DBService(connectionFactory)

    ICandidatesViewBuilder candidateViewBuilder = new CandidatesViewBuilder(connectionFactory, dbService)
    ICandidatesView candidatesView = candidateViewBuilder.build()

    IJobVacanciesViewBuilder jobVacancyViewBuilder = new JobVacanciesViewBuilder(connectionFactory, dbService)
    IJobVacanciesView jobVacanciesView = jobVacancyViewBuilder.build()

    ICompaniesViewBuilder companyViewBuilder = new CompaniesViewBuilder(connectionFactory, dbService)
    ICompaniesView companiesView = companyViewBuilder.build()

    IMatchDAO matchDAO = new MatchDAO(connectionFactory)
    IMatchValidation matchValidation = new MatchValidation()
    ICandidateMatchDAO candidateMatchDAO = new CandidateMatchDAO(matchDAO, connectionFactory)
    ICompanyMatchDAO companyMatchDAO = new CompanyMatchDAO(matchDAO, connectionFactory)
    IMatchService matchService = new MatchService(matchDAO, candidateMatchDAO, companyMatchDAO)
    IMatchesView matchesView = new MatchesView(candidatesView, jobVacanciesView, matchService, matchValidation)

    IApplicationView application = new ApplicationView(
        candidatesView,
        companiesView,
        jobVacanciesView,
        matchesView
    )

    void generate() {
        application.applicationGenerate()
    }

}
