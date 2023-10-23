package com.linketinder.controller.interfaces

import com.linketinder.model.jobvacancy.JobVacancy

interface IJobVacancyController {

    List<JobVacancy> getAll()
    List<JobVacancy> getAllByCompanyId(int id)
    JobVacancy getById(int id)
    void add(int companyId, JobVacancy jobVacancy)
    void update(JobVacancy jobVacancy)
    void delete(int id)

}
