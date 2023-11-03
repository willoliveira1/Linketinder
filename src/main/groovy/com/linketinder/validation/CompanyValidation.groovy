package com.linketinder.validation

import com.linketinder.model.company.Company
import com.linketinder.util.ErrorMessages
import com.linketinder.validation.interfaces.ICompanyValidation
import java.util.regex.Pattern

class CompanyValidation implements ICompanyValidation {

    void validateEmail(String email) {
        String regexEmail = /^[\w\.-]{2,}@\w{3,}\.\w{2,6}(\.\w{2,3})?$/
        if (!Pattern.matches(regexEmail, email)) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_EMAIL_TEXT)
        }
    }

    void validateCep(String cep) {
        String regexCep = /^\d{2}(\.)?\d{3}(-)?\d{3}$/
        if (!Pattern.matches(regexCep, cep)) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_CEP_TEXT)
        }
    }

    void validateCnpj(String cnpj) {
        String regexCnpj = /^\d{2}(\.)?\d{3}(\.)?\d{3}(\/)?\d{4}(-)?\d{2}$/
        if (!Pattern.matches(regexCnpj, cnpj)) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_CNPJ_TEXT)
        }
    }

    static void execute(Company company) {
        this.validateEmail(company.email)
        this.validateCep(company.cep)
        this.validateCnpj(company.cnpj)
    }

}
