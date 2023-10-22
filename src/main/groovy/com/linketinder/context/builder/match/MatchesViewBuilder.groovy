package com.linketinder.context.builder.match

import com.linketinder.context.builder.interfaces.IMatchesViewBuilder
import com.linketinder.dao.matchdao.interfaces.*
import com.linketinder.database.interfaces.IConnection
import com.linketinder.service.MatchService
import com.linketinder.service.interfaces.IMatchService
import com.linketinder.validation.MatchValidation
import com.linketinder.validation.interfaces.IMatchValidation
import com.linketinder.view.MatchesView
import com.linketinder.view.interfaces.*

class MatchesViewBuilder implements IMatchesViewBuilder {

    IConnection connection
    ICandidatesView candidatesView
    IJobVacanciesView jobVacanciesView

    ICandidateMatchDAO candidateMatchDAO
    ICompanyMatchDAO companyMatchDAO
    IMatchDAO matchDAO
    IMatchValidation matchValidation
    IMatchService matchService

    MatchesViewBuilder(IConnection connection, ICandidatesView candidatesView,
                       IJobVacanciesView jobVacanciesView) {
        this.connection = connection
        this.candidatesView = candidatesView
        this.jobVacanciesView = jobVacanciesView
    }

    private void generateDAOs() {
        this.matchDAO = new MatchDAOBuilder()
            .withConnection(this.connection)
            .build()

        this.candidateMatchDAO = new CandidateMatchDAOBuilder()
            .withConnection(this.connection)
            .withMatchDAO(this.matchDAO)
            .build()

        this.companyMatchDAO = new CompanyMatchDAOBuilder()
            .withConnection(this.connection)
            .withMatchDAO(this.matchDAO)
            .build()
    }

    private void generateMatchValidation() {
        this.matchValidation = new MatchValidation()
    }

    private void generateMatchService() {
        this.matchService = new MatchService(this.matchDAO, this.candidateMatchDAO, this.companyMatchDAO)
    }

    IMatchesView build() {
        this.generateDAOs()
        this.generateMatchValidation()
        this.generateMatchService()
        return new MatchesView(
            this.candidatesView,
            this.jobVacanciesView,
            this.matchService,
            this.matchValidation
        )
    }

}
