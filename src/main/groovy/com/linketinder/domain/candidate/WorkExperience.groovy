package com.linketinder.domain.candidate

import groovy.transform.TupleConstructor
import com.linketinder.domain.shared.ContractType
import com.linketinder.domain.shared.LocationType
import com.linketinder.domain.shared.State

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
