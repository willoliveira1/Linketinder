package com.linketinder.domain.company

import groovy.transform.TupleConstructor
import com.linketinder.domain.jobvacancy.JobVacancy
import com.linketinder.domain.shared.Person

@TupleConstructor(callSuper=true, includeSuperProperties=true, includeSuperFields=true)
class Company extends Person {

    String cnpj
    List<JobVacancy> jobVacancies
    List<Benefit> benefits

    @Override
    String toString() {
        return super.toString() +
                "cnpj='" + cnpj + '\'' +
                ", jobVacancies=" + jobVacancies +
                ", benefits=" + benefits
    }

}
