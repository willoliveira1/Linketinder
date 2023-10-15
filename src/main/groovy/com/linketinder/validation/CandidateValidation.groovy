package com.linketinder.validation

import com.linketinder.model.candidate.CourseStatus
import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.LocationType
import com.linketinder.model.shared.Proficiency
import com.linketinder.model.shared.State
import com.linketinder.util.viewtexts.CandidateTexts
import com.linketinder.validation.interfaces.ICandidateValidation
import java.util.regex.Pattern

class CandidateValidation implements ICandidateValidation {

    Readable reader = System.in.newReader()

    Integer validateId() {
        println CandidateTexts.ID_TEXT
        try {
            Integer id = reader.readLine() as Integer
            return id
        } catch (IllegalArgumentException e) {
            println CandidateTexts.INVALID_ID_TEXT
            validateId()
        }
    }

    String validateEmail() {
        println CandidateTexts.EMAIL_TEXT
        String email = reader.readLine()
        String regexEmail = /^[\w\.-]{2,}@\w{3,}\.\w{2,6}(\.\w{2,3})?$/
        if (!Pattern.matches(regexEmail, email)) {
            println CandidateTexts.INVALID_EMAIL_TEXT
            validateEmail()
        }
        return email
    }

    State validateState() {
        println CandidateTexts.STATE_TEXT
        State state
        try {
            String input = reader.readLine()
            state = State.valueOf(input.toUpperCase())
            return state
        } catch (IllegalArgumentException e) {
            println CandidateTexts.INVALID_STATE_TEXT
            validateState()
        }
    }

    String validateCep() {
        println CandidateTexts.CEP_TEXT
        String cep = reader.readLine()
        String regexCep = /^\d{2}(\.)?\d{3}(-)?\d{3}$/
        if (!Pattern.matches(regexCep, cep)) {
            println CandidateTexts.INVALID_CEP_TEXT
            validateCep()
        }
        return cep
    }

    String validateCpf() {
        println CandidateTexts.CPF_TEXT
        String cpf = reader.readLine()
        String regexCpf = /^\d{3}(\.)?\d{3}(\.)?\d{3}(-)?\d{2}$/
        if (!Pattern.matches(regexCpf, cpf)) {
            println CandidateTexts.INVALID_CPF_TEXT
            validateCpf()
        }
        return cpf
    }

    CourseStatus validateCourseStatus() {
        println CandidateTexts.STATUS_TEXT
        CourseStatus status
        try {
            String input = reader.readLine()
            status = CourseStatus.valueOf(input)
            return status
        } catch (IllegalArgumentException e) {
            println CandidateTexts.INVALID_ARG_TEXT
            validateCourseStatus()
        }
    }

    ContractType validateContractType() {
        println CandidateTexts.CONTRACT_TYPE_TEXT
        ContractType contractType
        try {
            String input = reader.readLine()
            contractType = ContractType.valueOf(input)
            return contractType
        } catch (IllegalArgumentException e) {
            println CandidateTexts.INVALID_ARG_TEXT
            validateContractType()
        }
    }

    LocationType validateLocationType() {
        println CandidateTexts.LOCATION_TYPE_TEXT
        LocationType locationType
        try {
            String input = reader.readLine()
            locationType = LocationType.valueOf(input)
            return locationType
        } catch (IllegalArgumentException e) {
            println CandidateTexts.INVALID_ARG_TEXT
            validateLocationType()
        }
    }

    Proficiency validateProficiency() {
        println CandidateTexts.PROFICIENCY_TEXT
        Proficiency proficiency
        try {
            String input = reader.readLine()
            proficiency = Proficiency.valueOf(input)
            return proficiency
        } catch (IllegalArgumentException e) {
            println CandidateTexts.INVALID_ARG_TEXT
            validateProficiency()
        }
    }

    boolean validateCurrentlyWork() {
        println CandidateTexts.CURRENTLY_WORK_TEXT
        String input = reader.readLine()
        if (input.toUpperCase() != "S" && input.toUpperCase() != "N") {
            println CandidateTexts.INVALID_ARG_TEXT
            validateCurrentlyWork()
        }
        return input.toUpperCase() == "S"
    }

    boolean validateAddMore(String input) {
        if (input.toUpperCase() != "S" && input.toUpperCase() != "N") {
            println CandidateTexts.INVALID_ARG_TEXT
            println CandidateTexts.ADD_MORE_TEXT
            input = reader.readLine()
            validateAddMore(input)
        }
        return input.toUpperCase() == "S"
    }

}
