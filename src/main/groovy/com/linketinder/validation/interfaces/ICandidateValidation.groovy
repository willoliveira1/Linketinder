package com.linketinder.validation.interfaces

import com.linketinder.model.candidate.Candidate

interface ICandidateValidation extends IPersonValidation<Candidate> {

    void validateCpf(String cpf)

}
