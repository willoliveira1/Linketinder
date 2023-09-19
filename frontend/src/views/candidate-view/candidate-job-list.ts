import $ from "jquery";

import JobVacancy from "../../models/job-vacancy/job-vacancy";
import JobVacancyService from "../../services/job-vacancy-service";

export default class CandidateJobList {

    jobVacancyService: JobVacancyService = new JobVacancyService;

    generateTable(): void {
        document.querySelector("#container")?.remove();

        $("main").append(`
            <div id="container">
                <table id="job-vacancies-table">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Título</th>
                            <th>Contrato</th>
                            <th>Tipo de Vaga</th>
                            <th>Salário</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        `);

        this.populateTable();
    }

    populateTable(): void {
        let jobVacancies: JobVacancy[] = this.jobVacancyService.populateJobVacancies();
        
        jobVacancies.forEach(vacancy => {
            $("tbody").append(`
                <tr class="row visible" id="row-${vacancy.id}">
                    <td class="job-id">${vacancy.id}</td>
                    <td class="job-title">${vacancy.title}</td>
                    <td class="job-contract-type">${vacancy.contractType}</td>
                    <td class="job-location-type">${vacancy.locationType}</td>
                    <td class="job-salary">R$ ${vacancy.salary.toLocaleString("pt-br", {minimumFractionDigits: 2})}</td>
                </tr>
                <tr id="hidden-row-${vacancy.id}" class="hidden-row" style="display: none;">
                    <td class="job-description" colspan="4">${vacancy.description}</td>
                    <td class="job-required-skills" colspan="2">
                        ${this.populateSkills(vacancy)}
                    </td>
                </tr>
            `);

            document.querySelector(`#row-${vacancy.id}`)?.addEventListener("click", function(): void {
                $(`#hidden-row-${vacancy.id}`).toggle();
            });
        });
    }

    populateSkills(jobVacancy: JobVacancy): string {
        let skills: string = "";
        Object.values(jobVacancy.requiredSkills).forEach(skill => {
            skills +=  `<li class="job-required-skill">
                            <ul>
                                <li>${skill.title}:</li>
                                <li>${skill.proficiency}</li>
                            </ul>
                        </li>`
        });
        return skills;
    }

}
