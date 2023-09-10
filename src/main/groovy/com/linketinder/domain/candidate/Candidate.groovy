package com.linketinder.domain.candidate

import groovy.transform.TupleConstructor
import com.linketinder.domain.shared.Language
import com.linketinder.domain.shared.Person
import com.linketinder.domain.shared.Skill

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
        return super.toString() +
                "cpf=" + cpf +
                ", academicExperiences=" + academicExperiences +
                ", workExperiences=" + workExperiences +
                ", languages=" + languages +
                ", skills=" + skills +
                ", certificates=" + certificates
    }

}
