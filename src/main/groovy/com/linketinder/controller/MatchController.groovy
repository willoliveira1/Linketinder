package com.linketinder.controller

import com.linketinder.controller.interfaces.IMatchController
import com.linketinder.model.match.Match
import com.linketinder.service.interfaces.IMatchService

class MatchController implements IMatchController {

    IMatchService service

    MatchController(IMatchService service) {
        this.service = service
    }

    List<Match> getAllMatches() {
        List<Match> matches = this.service.getAllMatches()
        return matches
    }

    List<Match> getAllMatchesByCandidateId(int candidateId) {
        List<Match> matches = this.service.getAllMatchesByCandidateId(candidateId)
        return matches
    }

    List<Match> getAllMatchesByCompanyId(int companyId) {
        List<Match> matches = this.service.getAllMatchesByCompanyId(companyId)
        return matches
    }

    void likeJobVacancy(int candidateId, int jobVacancyId) {
        this.service.likeJobVacancy(candidateId, jobVacancyId)
    }

    void likeCandidate(int companyId, int candidateId) {
        this.service.likeCandidate(companyId, candidateId)
    }

}
