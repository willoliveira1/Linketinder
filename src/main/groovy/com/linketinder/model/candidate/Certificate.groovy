package com.linketinder.model.candidate

import groovy.transform.TupleConstructor

@TupleConstructor
class Certificate {

    Integer id
    String title
    String duration

    @Override
    String toString() {
        return "id=${id}, title=${title}, duration=${duration}"
    }

}
