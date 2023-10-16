package com.linketinder.service

import com.linketinder.dao.candidatedao.interfaces.ICandidateDAO
import com.linketinder.model.candidate.Candidate
import com.linketinder.service.interfaces.ICandidateService

class CandidateService implements ICandidateService {

    ICandidateDAO candidateDAO

    CandidateService(ICandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO
    }

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
