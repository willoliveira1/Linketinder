package com.linketinder.context

import com.linketinder.context.builder.candidate.*
import com.linketinder.context.builder.company.*
import com.linketinder.context.builder.interfaces.*
import com.linketinder.context.builder.match.MatchesViewBuilder
import com.linketinder.database.*
import com.linketinder.database.interfaces.*
import com.linketinder.view.*
import com.linketinder.view.interfaces.*

class ApplicationContext {

    IConnection connection = ConnectionFactory.createConnection("POSTGRESQL")
    IDBService dbService = new DBService(this.connection)

    ICandidatesViewBuilder candidateViewBuilder = new CandidatesViewBuilder(this.connection, this.dbService)
    ICandidatesView candidatesView = this.candidateViewBuilder.build()

    ICompaniesViewBuilder companyViewBuilder = new CompaniesViewBuilder(this.connection, this.dbService)
    ICompaniesView companiesView = this.companyViewBuilder.build()

    IJobVacanciesViewBuilder jobVacancyViewBuilder = new JobVacanciesViewBuilder(this.connection, this.dbService)
    IJobVacanciesView jobVacanciesView = this.jobVacancyViewBuilder.build()

    IMatchesViewBuilder matchesViewBuilder = new MatchesViewBuilder(this.connection, this.candidatesView,
            this.jobVacanciesView)
    IMatchesView matchesView = this.matchesViewBuilder.build()

    IApplicationView application = new ApplicationView(
        this.candidatesView,
        this.companiesView,
        this.jobVacanciesView,
        this.matchesView
    )

    void generate() {
        this.application.applicationGenerate()
    }

}
