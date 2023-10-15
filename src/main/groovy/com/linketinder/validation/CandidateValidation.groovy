package com.linketinder.validation

import com.linketinder.model.candidate.CourseStatus
import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.LocationType
import com.linketinder.model.shared.Proficiency
import com.linketinder.model.shared.State
import com.linketinder.validation.interfaces.ICandidateValidation
import java.util.regex.Pattern

class CandidateValidation implements ICandidateValidation {

    Readable reader = System.in.newReader()

    Integer validateId() {
        println "Qual o id do candidato?"
        try {
            Integer id = reader.readLine() as Integer
            return id
        } catch (IllegalArgumentException e) {
            println "Id inválido."
            validateId()
        }
    }

    String validateEmail() {
        println "Qual o email do candidato?"
        String email = reader.readLine()
        String regexEmail = /^[\w\.-]{2,}@\w{3,}\.\w{2,6}(\.\w{2,3})?$/
        if (!Pattern.matches(regexEmail, email)) {
            println "Email inválido."
            validateEmail()
        }
        return email
    }

    State validateState() {
        println "Qual a sigla do estado do candidato?"
        State state
        try {
            String input = reader.readLine()
            state = State.valueOf(input.toUpperCase())
            return state
        } catch (IllegalArgumentException e) {
            println "Estado inválido."
            validateState()
        }
    }

    String validateCep() {
        println "Qual o cep do candidato?"
        String cep = reader.readLine()
        String regexCep = /^\d{2}(\.)?\d{3}(-)?\d{3}$/
        if (!Pattern.matches(regexCep, cep)) {
            println "Cep inválido."
            validateCep()
        }
        return cep
    }

    String validateCpf() {
        println "Qual cpf do candidato?"
        String cpf = reader.readLine()
        String regexCpf = /^\d{3}(\.)?\d{3}(\.)?\d{3}(-)?\d{2}$/
        if (!Pattern.matches(regexCpf, cpf)) {
            println "Tamanho de CPF inválido."
            validateCpf()
        }
        return cpf
    }

    CourseStatus validateCourseStatus() {
        println "Qual o status atual do curso? (Cursando/Concluído/Trancado)"
        CourseStatus status
        try {
            String input = reader.readLine()
            status = CourseStatus.valueOf(input)
            return status
        } catch (IllegalArgumentException e) {
            println "Status inválido."
            validateCourseStatus()
        }
    }

    ContractType validateContractType() {
        println "Qual o tipo de contrato? (CLT/PJ/Temporário/Estágio/Aprendiz)"
        ContractType contractType
        try {
            String input = reader.readLine()
            contractType = ContractType.valueOf(input)
            return contractType
        } catch (IllegalArgumentException e) {
            println "Tipo de contrato inválido."
            validateContractType()
        }
    }

    LocationType validateLocationType() {
        println "Qual o regime de trabalho? (Presencial/Híbrido/Remoto)"
        LocationType locationType
        try {
            String input = reader.readLine()
            locationType = LocationType.valueOf(input)
            return locationType
        } catch (IllegalArgumentException e) {
            println "Regime de trabalho inválido."
            validateLocationType()
        }
    }

    Proficiency validateProficiency() {
        println "Qual nível de proficiência? (Básico/Intermediário/Avançado)"
        Proficiency proficiency
        try {
            String input = reader.readLine()
            proficiency = Proficiency.valueOf(input)
            return proficiency
        } catch (IllegalArgumentException e) {
            println "Argumento inválido."
            validateProficiency()
        }
    }

    boolean validateCurrentlyWork() {
        println "É o emprego atual? (S/N)"
        String input = reader.readLine()
        if (input.toUpperCase() != "S" && input.toUpperCase() != "N") {
            println "Opção inválida."
            validateCurrentlyWork()
        }
        return input.toUpperCase() == "S"
    }

    boolean validateAddMore(String input) {
        if (input.toUpperCase() != "S" && input.toUpperCase() != "N") {
            println "Opção inválida."
            println "Deseja adicionar mais? (S/N)"
            input = reader.readLine()
            validateAddMore(input)
        }
        return input.toUpperCase() == "S"
    }

}
