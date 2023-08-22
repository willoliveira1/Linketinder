package com.linketinder.fileProcessor

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import com.linketinder.domain.candidate.Candidate
import com.linketinder.util.CandidatesGenerator

class CandidatesProcessor implements Processor<Candidate> {

    String filePath = "../../../resources/candidatos.json"

    void writeFile(List<Candidate> candidates) {
        JsonBuilder candidatesBuilder = new JsonBuilder(candidates)
        new File(filePath).write(candidatesBuilder.toPrettyString())
    }

    Candidate readById(Integer id) {
        List<Candidate> candidates = readFile()
        Candidate candidate = candidates.find {it.id == id}
        return candidate
    }

    List<Candidate> readFile() {
        File file = new File(filePath)
        if (file.exists()) {
            JsonSlurper slurper = new JsonSlurper()
            List<Candidate> candidates = slurper.parse(new File(filePath))
            return candidates
        }

        CandidatesGenerator generator = new CandidatesGenerator()
        generator.runGenerator()
        readFile()
    }

    void add(Candidate candidate) {
        List<Candidate> candidates = readFile()
        candidates.add(candidate)
        writeFile(candidates)
    }

    void update(Integer id, Candidate updatedCandidate) {
        List<Candidate> candidates = readFile()
        Integer index = candidates.indexOf(candidates.find {it.id == id})
        candidates.set(index, updatedCandidate)
        writeFile(candidates)
    }

    void delete(Integer id) {
        List<Candidate> candidates = readFile()
        candidates.removeIf {it.id == id}
        writeFile(candidates)
    }

}
