package com.linketinder.validation

import com.linketinder.model.candidate.Candidate
import com.linketinder.util.ErrorMessages
import com.linketinder.validation.interfaces.ICandidateValidation
import java.util.regex.Pattern

class CandidateValidation implements ICandidateValidation {

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

    void validateCpf(String cpf) {
        String regexCpf = /^\d{3}(\.)?\d{3}(\.)?\d{3}(-)?\d{2}$/
        if (!Pattern.matches(regexCpf, cpf)) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_CPF_TEXT)
        }
    }

    static void execute(Candidate candidate) {
        this.validateEmail(candidate.email)
        this.validateCep(candidate.cep)
        this.validateCpf(candidate.cpf)
    }

}
