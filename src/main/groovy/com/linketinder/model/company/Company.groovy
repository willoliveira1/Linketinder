package com.linketinder.model.company

import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.model.shared.Person
import groovy.transform.TupleConstructor

@TupleConstructor(callSuper=true, includeSuperProperties=true, includeSuperFields=true)
class Company extends Person {

    String cnpj
    List<JobVacancy> jobVacancies
    List<Benefit> benefits

    @Override
    String toString() {
        return "${super.toString()}cnpj=${cnpj}, jobVacancies=${jobVacancies}, benefits=${benefits}"
    }

}
