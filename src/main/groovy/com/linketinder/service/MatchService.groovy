package com.linketinder.service

import com.linketinder.dao.matchdao.interfaces.ICandidateMatchDAO
import com.linketinder.dao.matchdao.interfaces.ICompanyMatchDAO
import com.linketinder.dao.matchdao.interfaces.IMatchDAO
import com.linketinder.model.match.Match
import com.linketinder.service.interfaces.IMatchService

class MatchService implements IMatchService {

    IMatchDAO matchDAO
    ICandidateMatchDAO candidateMatchDAO
    ICompanyMatchDAO companyMatchDAO

    MatchService(IMatchDAO matchDAO, ICandidateMatchDAO candidateMatchDAO, ICompanyMatchDAO companyMatchDAO) {
        this.matchDAO = matchDAO
        this.candidateMatchDAO = candidateMatchDAO
        this.companyMatchDAO = companyMatchDAO
    }

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
