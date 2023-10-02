package com.linketinder.domain.company

import groovy.transform.TupleConstructor

@TupleConstructor
class Benefit {

    Integer id
    String title

    @Override
    String toString() {
        return "id=${id}, title=${title}"
    }
}
