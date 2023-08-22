package com.linketinder.service

import com.linketinder.domain.candidate.Candidate
import com.linketinder.fileProcessor.CandidatesProcessor
import com.linketinder.fileProcessor.Processor

class CandidateService implements BaseService<Candidate> {

    Processor<Candidate> processor = new CandidatesProcessor()

    List<Candidate> getAll() {
        return processor.readFile()
    }

    Candidate getById(Integer id) {
        return processor.readById(id)
    }

    void add(Candidate candidate) {
        processor.add(candidate)
    }

    void update(Integer id, Candidate candidate) {
        processor.update(id, candidate)
    }

    void delete(int id) {
        processor.delete(id)
    }

}
