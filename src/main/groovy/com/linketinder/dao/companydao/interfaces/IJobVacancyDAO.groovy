package com.linketinder.dao.companydao.interfaces

import com.linketinder.model.jobvacancy.JobVacancy

interface IJobVacancyDAO {

    List<JobVacancy> getAllJobVacancies()
    List<JobVacancy> getJobVacancyByCompanyId(int companyId)
    JobVacancy getJobVacancyById(int id)
    void insertJobVacancy(int companyId, JobVacancy jobVacancy)
    void updateJobVacancy(int id, JobVacancy jobVacancy)
    void deleteJobVacancy(int id)

}
