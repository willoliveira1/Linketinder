package com.linketinder.validation.interfaces

interface IPersonValidation<T> {

    void validateEmail(String email)
    void validateCep(String cep)
    void execute(T t)

}
