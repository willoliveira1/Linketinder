import { ContractType } from "../enums/contract-type";
import { LocationType } from "../enums/location-type";
import Skill from "../shared/skill";

export default class JobVacancy {

    id: number;
    title: string;
    description: string;
    requiredSkills: Skill[];
    salary: number;
    contractType: ContractType;
    locationType: LocationType;

    constructor(
        id: number,
        title: string,
        description: string,
        requiredSkills: Skill[],
        salary: number,
        contractType: ContractType,
        locationType: LocationType
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.salary = salary;
        this.contractType = contractType;
        this.locationType = locationType;
    }

}
