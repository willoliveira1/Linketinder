package com.linketinder.model.candidate

import groovy.transform.TupleConstructor

@TupleConstructor
class AcademicExperience {

    Integer id
    String educationalInstitution
    String degreeType
    String fieldOfStudy
    CourseStatus status

    @Override
    String toString() {
        return "id=${id}, educationalInstitution=${educationalInstitution}, degreeType=${degreeType}, " +
                "fieldOfStudy=${fieldOfStudy}, status=${status}"
    }

}
