package com.linketinder.validation.interfaces

import com.linketinder.model.company.Company

interface ICompanyValidation extends IPersonValidation<Company> {

    void validateCnpj(String cnpj)

}