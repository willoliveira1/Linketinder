package com.linketinder.validation.interfaces

import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.LocationType

interface IJobVacancyValidation {

    int validateId()
    int validateCompanyId()
    Double validateSalary()
    ContractType validateContractType()
    LocationType validateLocationType()
    boolean validateAddMore(String input)

}
