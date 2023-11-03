package com.linketinder.service.interfaces

import com.linketinder.model.jobvacancy.JobVacancy

interface IJobVacancyService {

    List<JobVacancy> getAll()
    List<JobVacancy> getAllByCompanyId(int id)
    JobVacancy getById(Integer id)
    void add(Integer companyId, JobVacancy jobVacancy)
    void update(int id, JobVacancy jobVacancy)
    void delete(Integer id)

}
