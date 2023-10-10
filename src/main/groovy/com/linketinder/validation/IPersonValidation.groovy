package com.linketinder.validation

import com.linketinder.model.shared.State

interface IPersonValidation {

    Integer validateId()
    String validateEmail()
    State validateState()
    String validateCep()
    boolean validateAddMore(String input)

}
