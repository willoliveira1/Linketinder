import AcademicExperience from "../../models/candidate/academic-experience";
import Language from "../../models/shared/language";
import Skill from "../../models/shared/skill";

export default class CandidateReadDto {

    id: number;
    academicExperiences: AcademicExperience[];
    languages: Language[];
    skills: Skill[];

    constructor(
        id: number,
        academicExperiences: AcademicExperience[],
        languages: Language[],
        skills: Skill[]
    ) {
        this.id = id;
        this.academicExperiences = academicExperiences;
        this.languages = languages;
        this.skills = skills;
    }

}
