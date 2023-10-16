package com.linketinder.view

import com.linketinder.model.match.Match
import com.linketinder.service.interfaces.IMatchService
import com.linketinder.util.viewtexts.MatchTexts
import com.linketinder.validation.interfaces.IMatchValidation
import com.linketinder.view.interfaces.ICandidatesView
import com.linketinder.view.interfaces.IJobVacanciesView
import com.linketinder.view.interfaces.IMatchesView

class MatchesView implements IMatchesView {

    ICandidatesView candidateView
    IJobVacanciesView jobVacancyView
    IMatchService matchService
    IMatchValidation validation

    MatchesView(ICandidatesView candidateView, IJobVacanciesView jobVacancyView, IMatchService matchService, IMatchValidation validation) {
        this.candidateView = candidateView
        this.jobVacancyView = jobVacancyView
        this.matchService = matchService
        this.validation = validation
    }

    void getAllMatches() {
        println MatchTexts.ALL_MATCHES_TEXT
        List<Match> matches = matchService.getAllMatches()
        matches.each {println it}
    }

    void getAllMatchesByCandidateId() {
        println MatchTexts.CANDIDATE_ID_TEXT
        Integer candidateId = validation.validateId()
        println MatchTexts.MATCHES_BY_CANDIDATE_ID_TEXT + candidateId + ":"
        List<Match> matches = matchService.getAllMatchesByCandidateId(candidateId)
        matches.each {println it}
    }

    void getAllMatchesByCompanyId() {
        println MatchTexts.COMPANY_ID_TEXT
        Integer companyId = validation.validateId()
        println MatchTexts.MATCHES_BY_COMPANY_ID_TEXT + companyId+ ":"
        List<Match> matches = matchService.getAllMatchesByCompanyId(companyId)
        matches.each {println it}
    }

    void likeJobVacancy() {
        println MatchTexts.CANDIDATE_ID_TEXT
        int candidateId = validation.validateId()
        jobVacancyView.getAllJobVacancies()
        println "\n" + MatchTexts.JOB_VACANCY_ID_TEXT
        int jobVacancyId = validation.validateId()
        matchService.likeJobVacancy(candidateId, jobVacancyId)
    }

    void likeCandidate() {
        println MatchTexts.COMPANY_ID_TEXT
        int companyId = validation.validateId()
        candidateView.getAllCandidates()
        println "\n" + MatchTexts.CANDIDATE_ID_TEXT
        int candidateId = validation.validateId()
        matchService.likeCandidate(companyId, candidateId)
    }

}
