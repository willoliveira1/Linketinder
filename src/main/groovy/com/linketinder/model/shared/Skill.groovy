package com.linketinder.model.shared

import groovy.transform.TupleConstructor

@TupleConstructor
class Skill {

    Integer id
    String title
    Proficiency proficiency

    @Override
    String toString() {
        return "id=${id}, title=${title}, proficiency=${proficiency}"
    }

}
