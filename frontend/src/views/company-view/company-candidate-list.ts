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
                    <tbody></tbody>
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
                        <ul>
                            ${this.populateSkills(candidate)}
                        </ul>
                    </td>
                    <td class="candidate candidate-academicExperiences">
                        <ul>
                            ${this.populateAcademicExperiences(candidate)}
                        </ul>
                    </td>
                    <td class="candidate candidate-languages">
                        <ul>
                            ${this.populateLanguages(candidate)}
                        </ul>
                    </td>
                </tr>
            `);
        });
    }

    populateSkills(candidate: CandidateReadDto): string {
        let skills: string = "";
        Object.values(candidate.skills).forEach(skill => {
            skills +=  `<li class="candidate-skill">
                            <ul>
                                <li>${skill.title}</li>
                                <li>${skill.proficiency}</li>
                            </ul>
                        </li>`
        });
        return skills;
    }

    populateAcademicExperiences(candidate: CandidateReadDto): string {
        let academicExperiences: string = "";
        Object.values(candidate.academicExperiences).forEach(experience => {
            academicExperiences +=  `<li class="candidate-academicExperience">
                            <ul>
                                <li>${experience.educationalInstitution}</li>
                                <li>${experience.degreeType}</li>
                                <li>${experience.fieldOfStudy}</li>
                                <li>${experience.status}</li>
                            </ul>
                        </li>`
        });
        return academicExperiences;
    }

    populateLanguages(candidate: CandidateReadDto): string {
        let languages: string = "";
        Object.values(candidate.languages).forEach(language => {
            languages +=  `<li class="candidate-language">
                            <ul>
                                <li>${language.name}</li>
                                <li>${language.proficiency}</li>
                            </ul>
                        </li>`
        });
        return languages;
    }

}