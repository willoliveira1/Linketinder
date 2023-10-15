package com.linketinder.dao.candidatedao.interfaces

import com.linketinder.model.candidate.*

interface ICandidateDAO {

    List<Candidate> getAllCandidates()
    Candidate getCandidateById(int id)
    void insertCandidate(Candidate candidate)
    void updateCandidate(int id, Candidate candidate)
    void deleteCandidateById(int id)

}
