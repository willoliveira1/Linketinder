import { CourseStatus } from "../enums/course-status";

export default class AcademicExperience {

    id: number = 0;
    educationalInstitution: string = "";
    degreeType: string = "";
    fieldOfStudy: string = "";
    status: CourseStatus = CourseStatus.Cursando;

    constructor(id: number, educationalInstitution: string, degreeType: string, fieldOfStudy: string, status: CourseStatus) {
        this.id = id;
        this.educationalInstitution = educationalInstitution;
        this.degreeType = degreeType;
        this.fieldOfStudy = fieldOfStudy;
        this.status = status;
    }

} 
