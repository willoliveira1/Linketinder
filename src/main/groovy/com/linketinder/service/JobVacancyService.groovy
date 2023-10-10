package com.linketinder.service

import com.linketinder.dao.companydao.JobVacancyDAO
import com.linketinder.model.jobvacancy.JobVacancy

class JobVacancyService {

    JobVacancyDAO jobVacancyDAO = new JobVacancyDAO()

    List<JobVacancy> getAll() {
        return jobVacancyDAO.getAllJobVacancies()
    }

    List<JobVacancy> getAllByCompanyId(int id) {
        return jobVacancyDAO.getJobVacancyByCompanyId(id)
    }

    JobVacancy getById(Integer id) {
        return jobVacancyDAO.getJobVacancyById(id)
    }

    void add(Integer companyId, JobVacancy jobVacancy) {
        jobVacancyDAO.insertJobVacancy(companyId, jobVacancy)
    }

    void update(JobVacancy jobVacancy) {
        jobVacancyDAO.updateJobVacancy(jobVacancy)
    }

    void delete(Integer id) {
        jobVacancyDAO.deleteJobVacancy(id)
    }

}
