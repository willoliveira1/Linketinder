package com.linketinder.validation

import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.LocationType
import com.linketinder.validation.interfaces.IJobVacancyValidation

class JobVacancyValidation implements IJobVacancyValidation {

    Readable reader = System.in.newReader()

    int validateId() {
        println "Qual o id da vaga?"
        try {
            Integer id = reader.readLine() as Integer
            return id
        } catch (IllegalArgumentException e) {
            println "Id inválido."
            validateId()
        }
    }

    int validateCompanyId() {
        println "Qual o id da empresa?"
        try {
            Integer id = reader.readLine() as Integer
            return id
        } catch (IllegalArgumentException e) {
            println "Id inválido."
            validateId()
        }
    }

    Double validateSalary() {
        println "Qual o salário da vaga?"
        try {
            Double salary = Double.parseDouble(reader.readLine())
            return salary
        } catch (IllegalArgumentException e) {
            println "Valor inválido."
            validateSalary()
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
