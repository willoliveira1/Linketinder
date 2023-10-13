package com.linketinder.validation

class MatchValidation {

    BufferedReader reader = System.in.newReader()

    Integer validateId() {
        try {
            return reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "Id inválido."
            validateId()
        }
    }

}
