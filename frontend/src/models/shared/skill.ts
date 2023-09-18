import { Proficiency } from "../enums/proficiency";

export default class Skill {

    id: number = 0;
    title: string = "";
    proficiency: Proficiency = Proficiency.Basico;

    constructor(id: number, title: string, proficiency: Proficiency) {
        this.id = id;
        this.title = title;
        this.proficiency = proficiency;
    }

}
