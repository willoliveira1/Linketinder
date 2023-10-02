package com.linketinder.domain.jobvacancy


import com.linketinder.domain.shared.Skill

import groovy.transform.TupleConstructor

@TupleConstructor
class JobVacancy {

    Integer id
    String title
    String description
    List<Skill> requiredSkills
    Double salary
    ContractType contractType
    LocationType locationType

    @Override
    String toString() {
        return "id=${id}, title=${title}, description=${description}, requiredSkills=${requiredSkills}, salary=${salary}" +
                ", contractType=${contractType}, locationType=${locationType}"
    }
}
