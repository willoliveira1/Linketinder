import Person from "../interface/person";
import AcademicExperience from "./academic-experience";
import Language from "../shared/language";
import Skill from "../shared/skill";
import WorkExperience from "./work-experience";
import Certificate from "./certificate";
import { State } from "../enums/state";

export default class Candidate implements Person {
    
    id: number;
    name: string;
    email: string;
    city: string;
    state: State;
    cep: string;
    description: string;
    cpf: string;
    academicExperiences: AcademicExperience[];
    workExperiences: WorkExperience[];
    languages: Language[];
    skills: Skill[];
    certificates: Certificate[];

    constructor(
        id: number,
        name: string,
        email: string,
        city: string,
        state: State,
        cep: string,
        description: string,
        cpf: string,
        academicExperiences: AcademicExperience[],
        workExperiences: WorkExperience[],
        languages: Language[],
        skills: Skill[],
        certificates: Certificate[]
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.city = city;
        this.state = state;
        this.cep = cep;
        this.description = description;
        this.cpf = cpf;
        this.academicExperiences = academicExperiences;
        this.workExperiences = workExperiences;
        this.languages = languages;
        this.skills = skills;
        this.certificates = certificates;
    }

}
