package com.linketinder.validation

import com.linketinder.model.shared.State
import com.linketinder.validation.interfaces.ICompanyValidation
import java.util.regex.Pattern

class CompanyValidation implements ICompanyValidation {

    Readable reader = System.in.newReader()

    Integer validateId() {
        println "Qual o id da empresa?"
        try {
            Integer id = reader.readLine() as Integer
            return id
        } catch (IllegalArgumentException e) {
            println "Id inválido."
            validateId()
        }
    }

    String validateEmail() {
        println "Qual o email da empresa?"
        String email = reader.readLine()
        String regexEmail = /^[\w\.-]{2,}@\w{3,}\.\w{2,6}(\.\w{2,3})?$/
        if (!Pattern.matches(regexEmail, email)) {
            println "Email inválido."
            validateEmail()
        }
        return email
    }

    State validateState() {
        State state
        println "Qual a sigla do estado da empresa?"
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
        println "Qual o cep da empresa?"
        String cep = reader.readLine()
        String regexCep = /^\d{2}(\.)?\d{3}(-)?\d{3}$/
        if (!Pattern.matches(regexCep, cep)) {
            println "Cep inválido."
            validateCep()
        }
        return cep
    }

    String validateCnpj() {
        println "Qual cnpj da empresa?"
        String cnpj = reader.readLine()
        String regexCnpj = /^\d{2}(\.)?\d{3}(\.)?\d{3}(\/)?\d{4}(-)?\d{2}$/
        if (!Pattern.matches(regexCnpj, cnpj)) {
            println "CNPJ inválido."
            validateCnpj()
        }
        return cnpj
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
