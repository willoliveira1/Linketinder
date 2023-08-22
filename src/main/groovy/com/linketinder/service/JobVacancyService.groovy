package com.linketinder.service

import com.linketinder.domain.jobvacancy.JobVacancy
import com.linketinder.fileProcessor.JobVacanciesProcessor
import com.linketinder.fileProcessor.Processor

class JobVacancyService implements BaseService<JobVacancy> {

    Processor<JobVacancy> processor = new JobVacanciesProcessor()

    List<JobVacancy> getAll() {
        return processor.readFile()
    }

    JobVacancy getById(Integer id) {
        return processor.readById(id)
    }

    void add(JobVacancy jobVacancy) {
        processor.add(jobVacancy)
    }

    void update(Integer id, JobVacancy jobVacancy) {
        processor.update(id, jobVacancy)
    }

    void delete(int id) {
        processor.delete(id)
    }

}
