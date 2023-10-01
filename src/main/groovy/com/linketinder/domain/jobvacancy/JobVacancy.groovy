package com.linketinder.domain.jobvacancy

import com.linketinder.domain.shared.ContractType
import com.linketinder.domain.shared.LocationType
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

}
