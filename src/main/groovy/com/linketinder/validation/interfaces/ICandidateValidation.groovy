package com.linketinder.validation.interfaces

import com.linketinder.model.candidate.CourseStatus
import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.LocationType
import com.linketinder.model.shared.Proficiency

interface ICandidateValidation extends IPersonValidation {

    String validateCpf()
    CourseStatus validateCourseStatus()
    ContractType validateContractType()
    LocationType validateLocationType()
    Proficiency validateProficiency()
    boolean validateCurrentlyWork()

}
