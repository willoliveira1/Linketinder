import { Proficiency } from "../enums/proficiency";

export default class Language {

    id: number = 0;
    name: string = "";
    proficiency: Proficiency = Proficiency.Basico;

    constructor(id: number, name: string, proficiency: Proficiency) {
        this.id = id;
        this.name = name;
        this.proficiency = proficiency;
    }

}
