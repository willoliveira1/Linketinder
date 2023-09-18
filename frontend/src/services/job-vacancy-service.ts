import data from "../../../src/main/resources/vagas.json";
import JobVacancy from "../models/job-vacancy/job-vacancy";

export default class JobVacancyService {

    jobVacancies: JobVacancy[] = data;

    populateJobVacancies(): JobVacancy[] {
        return this.jobVacancies;
    }

}
