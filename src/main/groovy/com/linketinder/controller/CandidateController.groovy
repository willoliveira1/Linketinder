package com.linketinder.controller

import com.linketinder.controller.interfaces.ICandidateController
import com.linketinder.model.candidate.Candidate
import com.linketinder.service.interfaces.ICandidateService

class CandidateController implements ICandidateController {

    ICandidateService service

    CandidateController(ICandidateService service) {
        this.service = service
    }

    List<Candidate> getAll() {
        List<Candidate> candidates = this.service.getAll()
        return candidates
    }

    Candidate getById(int id) {
        Candidate candidate = this.service.getById(id)
        return candidate
    }

    void add(Candidate candidate) {
        this.service.add(candidate)
    }

    void update(int id, Candidate candidate) {
        this.service.update(id, candidate)
    }

    void delete(int id) {
        this.service.delete(id)
    }

}
