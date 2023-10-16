package com.linketinder.validation

import com.linketinder.model.shared.State
import com.linketinder.util.viewtexts.CompanyTexts
import com.linketinder.validation.interfaces.ICompanyValidation
import java.util.regex.Pattern

class CompanyValidation implements ICompanyValidation {

    Readable reader = System.in.newReader()

    Integer validateId() {
        println CompanyTexts.ID_TEXT
        try {
            Integer id = reader.readLine() as Integer
            return id
        } catch (IllegalArgumentException e) {
            println CompanyTexts.INVALID_ID_TEXT
            validateId()
        }
    }

    String validateEmail() {
        println CompanyTexts.EMAIL_TEXT
        String email = reader.readLine()
        String regexEmail = /^[\w\.-]{2,}@\w{3,}\.\w{2,6}(\.\w{2,3})?$/
        if (!Pattern.matches(regexEmail, email)) {
            println CompanyTexts.INVALID_EMAIL_TEXT
            validateEmail()
        }
        return email
    }

    State validateState() {
        State state
        println CompanyTexts.STATE_TEXT
        try {
            String input = reader.readLine()
            state = State.valueOf(input.toUpperCase())
            return state
        } catch (IllegalArgumentException e) {
            println CompanyTexts.INVALID_STATE_TEXT
            validateState()
        }
    }

    String validateCep() {
        println CompanyTexts.CEP_TEXT
        String cep = reader.readLine()
        String regexCep = /^\d{2}(\.)?\d{3}(-)?\d{3}$/
        if (!Pattern.matches(regexCep, cep)) {
            println CompanyTexts.INVALID_CEP_TEXT
            validateCep()
        }
        return cep
    }

    String validateCnpj() {
        println CompanyTexts.CNPJ_TEXT
        String cnpj = reader.readLine()
        String regexCnpj = /^\d{2}(\.)?\d{3}(\.)?\d{3}(\/)?\d{4}(-)?\d{2}$/
        if (!Pattern.matches(regexCnpj, cnpj)) {
            println CompanyTexts.INVALID_CNPJ_TEXT
            validateCnpj()
        }
        return cnpj
    }

    boolean validateAddMore(String input) {
        if (input.toUpperCase() != "S" && input.toUpperCase() != "N") {
            println CompanyTexts.INVALID_ARG_TEXT
            println CompanyTexts.ADD_MORE_TEXT
            input = reader.readLine()
            validateAddMore(input)
        }
        return input.toUpperCase() == "S"
    }

}
