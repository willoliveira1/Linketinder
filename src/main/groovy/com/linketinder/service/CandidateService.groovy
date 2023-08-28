package com.linketinder.service

import com.linketinder.domain.candidate.Candidate
import com.linketinder.fileprocessor.CandidatesProcessor
import com.linketinder.fileprocessor.Processor

class CandidateService implements IBaseService<Candidate> {

    Processor processor = new CandidatesProcessor()

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
