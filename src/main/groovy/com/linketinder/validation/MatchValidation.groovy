package com.linketinder.validation

import com.linketinder.validation.interfaces.IMatchValidation

class MatchValidation implements IMatchValidation {

    Readable reader = System.in.newReader()

    Integer validateId() {
        try {
            return reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "Id inválido."
            validateId()
        }
    }

}
