package com.linketinder.service

import com.linketinder.dao.candidatedao.CandidateDAO
import com.linketinder.model.candidate.Candidate

class CandidateService implements IBaseService<Candidate> {

    CandidateDAO candidateDAO = new CandidateDAO()

    List<Candidate> getAll() {
        return candidateDAO.getAllCandidates()
    }

    Candidate getById(Integer id) {
        return candidateDAO.getCandidateById(id)
    }

    void add(Candidate candidate) {
        candidateDAO.insertCandidate(candidate)
    }

    void update(Integer id, Candidate candidate) {
        candidateDAO.updateCandidate(id, candidate)
    }

    void delete(Integer id) {
        candidateDAO.deleteCandidateById(id)
    }

}
