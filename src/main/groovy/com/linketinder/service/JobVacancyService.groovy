package com.linketinder.service

import com.linketinder.dao.companydao.JobVacancyDAO
import com.linketinder.domain.jobvacancy.JobVacancy

class JobVacancyService implements IBaseService<JobVacancy> {

    JobVacancyDAO jobVacancyDAO = new JobVacancyDAO()

    List<JobVacancy> getAll() {
        return jobVacancyDAO.getAllJobVacancies()
    }

    JobVacancy getById(Integer id) {
        return jobVacancyDAO.getJobVacancyById(id)
    }

    void add(JobVacancy jobVacancy) {}

    void add(Integer id, JobVacancy jobVacancy) {
        jobVacancyDAO.insertJobVacancy(jobVacancy, id)
    }

    void update(Integer id, JobVacancy jobVacancy) {
        jobVacancyDAO.updateJobVacancy(jobVacancy, id)
    }

    void delete(Integer id) {
        jobVacancyDAO.deleteJobVacancy(id)
    }

}