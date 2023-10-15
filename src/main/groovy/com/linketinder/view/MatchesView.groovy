package com.linketinder.view

import com.linketinder.model.match.Match
import com.linketinder.service.interfaces.IMatchService
import com.linketinder.validation.MatchValidation
import com.linketinder.view.interfaces.ICandidatesView
import com.linketinder.view.interfaces.IJobVacanciesView
import com.linketinder.view.interfaces.IMatchesView

class MatchesView implements IMatchesView {

    MatchValidation validation = new MatchValidation()
    ICandidatesView candidateView
    IJobVacanciesView jobVacancyView
    IMatchService matchService

    MatchesView(ICandidatesView candidateView, IJobVacanciesView jobVacancyView, IMatchService matchService) {
        this.candidateView = candidateView
        this.jobVacancyView = jobVacancyView
        this.matchService = matchService
    }

    void getAllMatches() {
        println "Listagem de Matches:"
        List<Match> matches = matchService.getAllMatches()
        matches.each {println it}
    }

    void getAllMatchesByCandidateId() {
        println "Qual Id do candidato?"
        Integer candidateId = validation.validateId()
        println "Listagem de Matches do Candidato Id ${candidateId}:"
        List<Match> matches = matchService.getAllMatchesByCandidateId(candidateId)
        matches.each {println it}
    }

    void getAllMatchesByCompanyId() {
        println "Qual Id da empresa?"
        Integer companyId = validation.validateId()
        println "Listagem de Matches da Empresa Id ${companyId}:"
        List<Match> matches = matchService.getAllMatchesByCompanyId(companyId)
        matches.each {println it}
    }

    void likeJobVacancy() {
        println "Qual o id do candidato?"
        int candidateId = validation.validateId()
        jobVacancyView.getAllJobVacancies()
        println "\nQual o id da vaga?"
        int jobVacancyId = validation.validateId()
        matchService.likeJobVacancy(candidateId, jobVacancyId)
    }

    void likeCandidate() {
        println "Qual o id da empresa?"
        int companyId = validation.validateId()
        candidateView.getAllCandidates()
        println "\nQual o id do candidato?"
        int candidateId = validation.validateId()
        matchService.likeCandidate(companyId, candidateId)
    }

}
