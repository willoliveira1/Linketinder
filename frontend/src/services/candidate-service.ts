import data from "../../../src/main/resources/candidatos.json";

import Candidate from "../models/candidate/candidate";

export default class CandidateService {

    initialCandidates: Array<Candidate> = data;

    generateCandidates(): void {
        localStorage.setItem("candidates", JSON.stringify(data))
    }

    generateCandidate(): Candidate {
        if (localStorage.getItem("candidates") == null) {
            this.generateCandidates();
        }

        let candidates = JSON.parse(localStorage.getItem("candidates")!);

        let index: number = 1;
        let candidate: Candidate = new Candidate(
            candidates[index].id,
            candidates[index].name,
            candidates[index].email,
            candidates[index].city,
            candidates[index].state,
            candidates[index].cep,
            candidates[index].description,
            candidates[index].cpf,
            candidates[index].academicExperiences,
            candidates[index].workExperiences,
            candidates[index].languages,
            candidates[index].skills,
            candidates[index].certificates
        );

        return candidate;
    }

    updateCandidate(candidate: Candidate): void {
        let candidates: Candidate[] = JSON.parse(localStorage.getItem("candidates")!);

        let index = candidates.findIndex(c => c.id == candidate.id);
        if (index != -1) {
            candidates[index] = candidate;
        }

        localStorage.setItem("candidates", JSON.stringify(candidates));
    }

    populateCandidates(): Candidate[] {
        let candidates: Candidate[] = JSON.parse(localStorage.getItem("candidates")!);
       return candidates; 
    }

    createCandidate(candidate: Candidate): void {
        let candidates = localStorage.getItem("candidates");

        if (candidates == null) {
            this.generateCandidates();
            candidates = localStorage.getItem("candidates");
        }
        
        let newCandidate: String = JSON.stringify(candidate);
        candidates! += newCandidate;

        localStorage.setItem("candidates", candidates!);
    }
    
}
