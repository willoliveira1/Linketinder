package com.linketinder.model.jobvacancy.dto

import com.linketinder.model.jobvacancy.JobVacancy
import groovy.transform.TupleConstructor

@TupleConstructor
class JobVacancyDTO {

    int companyId
    JobVacancy jobVacancy

}
