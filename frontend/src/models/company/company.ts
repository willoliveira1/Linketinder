import { State } from "../enums/state";
import Person from "../interface/person";
import JobVacancy from "../job-vacancy/job-vacancy";
import Benefit from "./benefit";

export default class Company implements Person {

    id: number;
    name: string;
    email: string;
    city: string;
    state: State;
    cep: string;
    description: string;

    cnpj: string;
    jobVacancies: JobVacancy[];
    benefits: Benefit[];

    constructor(
        id: number,
        name: string,
        email: string,
        city: string,
        state: State,
        cep: string,
        description: string,
        cnpj: string,
        jobVacancies: JobVacancy[],
        benefits: Benefit[]
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.city = city;
        this.state = state;
        this.cep = cep;
        this.description = description;
        this.cnpj = cnpj;
        this.jobVacancies = jobVacancies;
        this.benefits = benefits;
    }

}
