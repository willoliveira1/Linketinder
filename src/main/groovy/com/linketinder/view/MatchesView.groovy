package com.linketinder.view

import com.linketinder.controller.interfaces.IMatchController
import com.linketinder.model.match.Match
import com.linketinder.util.viewtexts.MatchTexts
import com.linketinder.validation.interfaces.IMatchValidation
import com.linketinder.view.interfaces.*

class MatchesView implements IMatchesView {

    ICandidatesView candidateView
    IJobVacanciesView jobVacancyView
    IMatchController matchController
    IMatchValidation validation

    MatchesView(ICandidatesView candidateView, IJobVacanciesView jobVacancyView, IMatchController matchController,
                IMatchValidation validation) {
        this.candidateView = candidateView
        this.jobVacancyView = jobVacancyView
        this.matchController = matchController
        this.validation = validation
    }

    void getAllMatches() {
        println MatchTexts.ALL_MATCHES_TEXT
        List<Match> matches = this.matchController.getAllMatches()
        matches.each {println it}
    }

    void getAllMatchesByCandidateId() {
        println MatchTexts.CANDIDATE_ID_TEXT
        Integer candidateId = this.validation.validateId()
        println MatchTexts.MATCHES_BY_CANDIDATE_ID_TEXT + candidateId + ":"
        List<Match> matches = this.matchController.getAllMatchesByCandidateId(candidateId)
        matches.each {println it}
    }

    void getAllMatchesByCompanyId() {
        println MatchTexts.COMPANY_ID_TEXT
        Integer companyId = this.validation.validateId()
        println MatchTexts.MATCHES_BY_COMPANY_ID_TEXT + companyId+ ":"
        List<Match> matches = this.matchController.getAllMatchesByCompanyId(companyId)
        matches.each {println it}
    }

    void likeJobVacancy() {
        println MatchTexts.CANDIDATE_ID_TEXT
        int candidateId = this.validation.validateId()
        jobVacancyView.getAllJobVacancies()
        println "\n" + MatchTexts.JOB_VACANCY_ID_TEXT
        int jobVacancyId = this.validation.validateId()
        this.matchController.likeJobVacancy(candidateId, jobVacancyId)
    }

    void likeCandidate() {
        println MatchTexts.COMPANY_ID_TEXT
        int companyId = this.validation.validateId()
        this.candidateView.getAllCandidates()
        println "\n" + MatchTexts.CANDIDATE_ID_TEXT
        int candidateId = this.validation.validateId()
        this.matchController.likeCandidate(companyId, candidateId)
    }

}
