import $ from "jquery";

import CompanyService from "../../services/company-service";
import CandidateReadDto from "./../../dto/candidate-dto/CandidateReadDto";
import CandidateService from "../../services/candidate-service";

export default class CompanyCandidateList {

    companyService: CompanyService = new CompanyService;
    candidateService: CandidateService = new CandidateService;

    generateTable(): void {
        document.querySelector("#container")?.remove();

        $("main").append(`
            <div id="container">
                <table id="candidates-table">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Habilidades</th>
                            <th>Formação</th>
                            <th>Idiomas</th>
                        </tr>
                    </thead>
                    <tbody id="candidate-body"></tbody>
                </table>
            </div>
        `);

        this.populateTable();
    }

    populateTable(): void {
        let candidates: CandidateReadDto[] = this.candidateService.populateCandidates();

        candidates.forEach(candidate => {
            $("tbody").append(`
                <tr class="row">
                    <td class="candidate candidate-id">${candidate.id}</td>
                    <td class="candidate candidate-skills">
                        ${this.populateSkills(candidate)}
                    </td>
                    <td class="candidate candidate-academicExperiences">
                        ${this.populateAcademicExperiences(candidate)}
                    </td>
                    <td class="candidate candidate-languages">
                        ${this.populateLanguages(candidate)}
                    </td>
                </tr>
            `);
        });
    }

    populateSkills(candidate: CandidateReadDto): string {
        let skills: string = "";
        Object.values(candidate.skills).forEach(skill => {
            skills +=  `
                <li class="candidate-skill">
                    <ul>
                        <li>${skill.title}:</li>
                        <li>${skill.proficiency}</li>
                    </ul>
                </li>`
        });
        return skills;
    }

    populateAcademicExperiences(candidate: CandidateReadDto): string {
        let academicExperiences: string = "";
        Object.values(candidate.academicExperiences).forEach(experience => {
            academicExperiences +=  `
            <div class="candidate-academicExperience">
                <ul class="academicExperience" style="display: block;">
                    <li>Instituição: ${experience.educationalInstitution}</li>
                    <li>Tipo: ${experience.degreeType}</li>
                    <li>Curso: ${experience.fieldOfStudy}</li>
                    <li>Status: ${experience.status}</li>
                </ul>
            </div>`
        });
        return academicExperiences;
    }

    populateLanguages(candidate: CandidateReadDto): string {
        let languages: string = "";
        Object.values(candidate.languages).forEach(language => {
            languages +=  `
                <li class="candidate-language">
                    <ul>
                        <li>${language.name}:</li>
                        <li>${language.proficiency}</li>
                    </ul>
                </li>`
        });
        return languages;
    }

}
