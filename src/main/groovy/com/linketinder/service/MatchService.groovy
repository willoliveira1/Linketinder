package com.linketinder.service

import com.linketinder.dao.matchdao.CandidateMatchDAO
import com.linketinder.dao.matchdao.CompanyMatchDAO
import com.linketinder.dao.matchdao.MatchDAO
import com.linketinder.model.match.Match

class MatchService {

    MatchDAO matchDAO = new MatchDAO()
    CandidateMatchDAO candidateMatchDAO = new CandidateMatchDAO()
    CompanyMatchDAO companyMatchDAO = new CompanyMatchDAO()

    List<Match> getAllMatches() {
        return matchDAO.getAllMatches()
    }

    List<Match> getAllMatchesByCandidateId(int candidateId) {
        return candidateMatchDAO.getAllMatchesByCandidateId(candidateId)
    }

    List<Match> getAllMatchesByCompanyId(int companyId) {
        return companyMatchDAO.getAllMatchesByCompanyId(companyId)
    }

    void likeJobVacancy(int candidateId, int jobVacancyId) {
        candidateMatchDAO.candidateLikeJobVacancy(candidateId, jobVacancyId)
    }

    void likeCandidate(int companyId, int candidateId) {
        companyMatchDAO.companyLikeCandidate(companyId, candidateId)
    }

}
