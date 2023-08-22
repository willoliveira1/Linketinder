package com.linketinder.domain.candidate

import groovy.transform.TupleConstructor

@TupleConstructor
class AcademicExperience {

    Integer id
    String educationalInstitution
    String degreeType
    String fieldOfStudy
    CourseStatus status

}
