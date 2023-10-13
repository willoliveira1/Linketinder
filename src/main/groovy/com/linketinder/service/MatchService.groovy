package com.linketinder.service

import com.linketinder.dao.matchdao.MatchDAO
import com.linketinder.model.match.Match

class MatchService {

    MatchDAO matchDAO = new MatchDAO()

    List<Match> getAllMatches() {
        return matchDAO.getAllMatches()
    }

    List<Match> getAllMatchesByCandidateId(int candidateId) {
        return matchDAO.getAllMatchesByCandidateId(candidateId)
    }

    List<Match> getAllMatchesByCompanyId(int companyId) {
        return matchDAO.getAllMatchesByCompanyId(companyId)
    }

    void likeJobVacancy(int candidateId, int jobVacancyId) {
        matchDAO.candidateLikeJobVacancy(candidateId, jobVacancyId)
    }

    void likeCandidate(int companyId, int candidateId) {
        matchDAO.companyLikeCandidate(companyId, candidateId)
    }

}
