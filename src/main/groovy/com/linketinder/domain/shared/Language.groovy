package com.linketinder.domain.shared

import groovy.transform.TupleConstructor

@TupleConstructor
class Language {

    Integer id
    String name
    Proficiency proficiency

    @Override
    String toString() {
        return "id=${id}, name=${name}, proficiency=${proficiency}"
    }
}
