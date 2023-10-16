package com.linketinder.validation

import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.LocationType
import com.linketinder.util.viewtexts.JobVacancyTexts
import com.linketinder.validation.interfaces.IJobVacancyValidation

class JobVacancyValidation implements IJobVacancyValidation {

    Readable reader = System.in.newReader()

    int validateId() {
        println JobVacancyTexts.JOB_VACANCY_ID_TEXT
        try {
            Integer id = reader.readLine() as Integer
            return id
        } catch (IllegalArgumentException e) {
            println JobVacancyTexts.INVALID_ID_TEXT
            validateId()
        }
    }

    int validateCompanyId() {
        println JobVacancyTexts.COMPANY_ID_TEXT
        try {
            Integer id = reader.readLine() as Integer
            return id
        } catch (IllegalArgumentException e) {
            println JobVacancyTexts.INVALID_ID_TEXT
            validateId()
        }
    }

    Double validateSalary() {
        println JobVacancyTexts.SALARY_TEXT
        try {
            Double salary = Double.parseDouble(reader.readLine())
            return salary
        } catch (IllegalArgumentException e) {
            println JobVacancyTexts.INVALID_VALUE_TEXT
            validateSalary()
        }
    }

    ContractType validateContractType() {
        println JobVacancyTexts.CONTRACT_TYPE_TEXT
        ContractType contractType
        try {
            String input = reader.readLine()
            contractType = ContractType.valueOf(input)
            return contractType
        } catch (IllegalArgumentException e) {
            println JobVacancyTexts.INVALID_ARG_TEXT
            validateContractType()
        }
    }

    LocationType validateLocationType() {
        println JobVacancyTexts.LOCATION_TYPE_TEXT
        LocationType locationType
        try {
            String input = reader.readLine()
            locationType = LocationType.valueOf(input)
            return locationType
        } catch (IllegalArgumentException e) {
            println JobVacancyTexts.INVALID_ARG_TEXT
            validateLocationType()
        }
    }

    boolean validateAddMore(String input) {
        if (input.toUpperCase() != "S" && input.toUpperCase() != "N") {
            println JobVacancyTexts.INVALID_ARG_TEXT
            println JobVacancyTexts.ADD_MORE_TEXT
            input = reader.readLine()
            validateAddMore(input)
        }
        return input.toUpperCase() == "S"
    }

}
