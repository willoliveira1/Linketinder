package com.linketinder.model.match

import groovy.transform.TupleConstructor

@TupleConstructor
class Match {

    Integer id
    Integer candidateId
    Integer companyId
    Integer jobVacancyId

    @Override
    String toString() {
        return "Match{id=${id}, candidateId=${candidateId}, companyId=${companyId}, jobVacancyId=${jobVacancyId}}"
    }

}
