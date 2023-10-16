package com.linketinder.dao.matchdao.interfaces

import com.linketinder.model.match.Match

interface ICompanyMatchDAO {

    void companyLikeCandidate(int companyId, int candidateId)
    List<Match> getAllMatchesByCompanyId(int companyId)

}
