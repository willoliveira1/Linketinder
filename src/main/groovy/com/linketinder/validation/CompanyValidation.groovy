package com.linketinder.validation

import com.linketinder.model.shared.State

class CompanyValidation implements IPersonValidation {

    BufferedReader reader = System.in.newReader()

    Integer validateId() {
        try {
            println "Qual o id da empresa?"
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
        return email
    }

    State validateState() {
        State state
        try {
            println "Qual a sigla do estado da empresa?"
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
        if (cep.length() < 8 || cep.length() > 10) {
            println "Cep inválido."
            validateCep()
        }
        return cep
    }

    String validateCnpj() {
        println "Qual cnpj da empresa?"
        String cnpj = reader.readLine()
        if (cnpj.length() < 14 || cnpj.length() > 18) {
            println "Tamanho de CNPJ inválido."
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