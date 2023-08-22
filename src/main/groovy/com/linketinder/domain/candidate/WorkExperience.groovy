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

}
