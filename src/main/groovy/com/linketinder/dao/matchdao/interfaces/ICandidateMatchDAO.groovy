package com.linketinder.dao.matchdao.interfaces

import com.linketinder.model.match.Match

interface ICandidateMatchDAO {

    void candidateLikeJobVacancy(int candidateId, int jobVacancyId)
    List<Match> getAllMatchesByCandidateId(int candidateId)

}
