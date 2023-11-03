package com.linketinder.service

import com.linketinder.dao.companydao.interfaces.IJobVacancyDAO
import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.service.interfaces.IJobVacancyService

class JobVacancyService implements IJobVacancyService {

    IJobVacancyDAO jobVacancyDAO

    JobVacancyService(IJobVacancyDAO jobVacancyDAO) {
        this.jobVacancyDAO = jobVacancyDAO
    }

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

    void update(int id, JobVacancy jobVacancy) {
        jobVacancyDAO.updateJobVacancy(id, jobVacancy)
    }

    void delete(Integer id) {
        jobVacancyDAO.deleteJobVacancy(id)
    }

}
