package com.linketinder.controller.interfaces

import com.linketinder.model.match.Match

interface IMatchController {

    List<Match> getAllMatches()
    List<Match> getAllMatchesByCandidateId(int candidateId)
    List<Match> getAllMatchesByCompanyId(int companyId)
    void likeJobVacancy(int candidateId, int jobVacancyId)
    void likeCandidate(int companyId, int candidateId)

}
