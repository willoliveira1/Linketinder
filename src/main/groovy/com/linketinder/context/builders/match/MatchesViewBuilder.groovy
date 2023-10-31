package com.linketinder.context.builders.match

import com.linketinder.context.builders.interfaces.IMatchesViewBuilder
import com.linketinder.controller.MatchController
import com.linketinder.controller.interfaces.IMatchController
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
    IMatchController matchController
    IMatchValidation matchValidation

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

    private void generateMatchController() {
        this.generateDAOs()
        IMatchService matchService = new MatchService(this.matchDAO, this.candidateMatchDAO, this.companyMatchDAO)
        this.matchController = new MatchController(matchService)
    }

    IMatchesView build() {
        this.generateMatchValidation()
        this.generateMatchController()
        return new MatchesView(
            this.candidatesView,
            this.jobVacanciesView,
            this.matchController,
            this.matchValidation
        )
    }

}
