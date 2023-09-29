package com.linketinder.domain.shared

import groovy.transform.TupleConstructor

@TupleConstructor(includeFields=true)
abstract class Person {

    Integer id
    String name
    String email
    String city
    State state
    String country
    String cep
    String description


    @Override
    String toString() {
        return """id=${id}, name=${name}, email=${email}, city=${city}, state=${state}, 
            country=${country}, cep=${cep}, description=${description}, """
    }
}
