package com.linketinder.controller

import com.linketinder.controller.interfaces.IJobVacancyController
import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.service.interfaces.IJobVacancyService

class JobVacancyController implements IJobVacancyController {

    IJobVacancyService service

    JobVacancyController(IJobVacancyService service) {
        this.service = service
    }

    List<JobVacancy> getAll() {
        List<JobVacancy> jobVacancies = this.service.getAll()
        return jobVacancies
    }

    List<JobVacancy> getAllByCompanyId(int id) {
        List<JobVacancy> jobVacancies = this.service.getAllByCompanyId(id)
        return jobVacancies
    }

    JobVacancy getById(int id) {
        JobVacancy jobVacancy = this.service.getById(id)
        return jobVacancy
    }

    void add(int companyId, JobVacancy jobVacancy) {
        this.service.add(companyId, jobVacancy)
    }

    void update(JobVacancy jobVacancy) {
        this.service.update(jobVacancy)
    }

    void delete(int id) {
        this.service.delete(id)
    }

}
