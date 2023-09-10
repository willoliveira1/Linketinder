package com.linketinder.service

import com.linketinder.domain.jobvacancy.JobVacancy
import com.linketinder.fileprocessor.JobVacanciesProcessor
import com.linketinder.fileprocessor.Processor

class JobVacancyService implements IBaseService<JobVacancy> {

    Processor processor = new JobVacanciesProcessor()

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

    void delete(Integer id) {
        processor.delete(id)
    }

}
