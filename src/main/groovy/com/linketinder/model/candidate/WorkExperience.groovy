package com.linketinder.model.candidate

import com.linketinder.model.jobvacancy.*
import com.linketinder.model.shared.State
import groovy.transform.TupleConstructor

@TupleConstructor
class WorkExperience {

    Integer id
    String title
    String companyName
    ContractType contractType
    LocationType locationType
    String city
    State state
    Boolean currentlyWork
    String description

    @Override
    String toString() {
        return "id=${id}, title=${title}, companyName=${companyName}, contractType=${contractType}, " +
                "locationType=${locationType}, city=${city}, state=${state}, currentlyWork=${currentlyWork}, " +
                "description=${description}"
    }

}
