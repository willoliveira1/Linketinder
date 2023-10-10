package com.linketinder.model.candidate

import com.linketinder.model.shared.Person
import com.linketinder.model.shared.Skill
import groovy.transform.TupleConstructor

@TupleConstructor(callSuper=true, includeSuperProperties=true, includeSuperFields=true)
class Candidate extends Person {

    String cpf
    List<AcademicExperience> academicExperiences
    List<WorkExperience> workExperiences
    List<Language> languages
    List<Skill> skills
    List<Certificate> certificates

    @Override
    String toString() {
        return "${super.toString()}cpf=${cpf}, academicExperiences=${academicExperiences}, " +
            "workExperiences=${workExperiences}, languages=${languages}, skills=${skills}, " +
            "certificates=${certificates}"
    }

}
