package com.linketinder.fileprocessor

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import com.linketinder.domain.jobvacancy.JobVacancy
import com.linketinder.util.JobVacanciesGenerator

class JobVacanciesProcessor implements Processor<JobVacancy> {

    String filePath = "../../../resources/vagas.json"

    void writeFile(List<JobVacancy> jobVacancies) {
        JsonBuilder jobVacanciesBuilder = new JsonBuilder(jobVacancies)
        new File(filePath).write(jobVacanciesBuilder.toPrettyString())
    }

    JobVacancy readById(Integer id) {
        List<JobVacancy> jobVacancies = readFile()
        JobVacancy company = jobVacancies.find {it.id == id}
        return company
    }

    List<JobVacancy> readFile() {
        File file = new File(filePath)
        if (file.exists()) {
            JsonSlurper slurper = new JsonSlurper()
            List<JobVacancy> jobVacancies = slurper.parse(new File(filePath))
            return jobVacancies
        }

        JobVacanciesGenerator generator = new JobVacanciesGenerator()
        generator.runGenerator()
        readFile()
    }

    void add(JobVacancy jobVacancy) {
        List<JobVacancy> jobVacancies = readFile()
        jobVacancies.add(jobVacancy)
        writeFile(jobVacancies)
    }

    void update(Integer id, JobVacancy updatedJobVacancy) {
        List<JobVacancy> jobVacancies = readFile()
        Integer index = jobVacancies.indexOf(jobVacancies.find {it.id == id})
        jobVacancies.set(index, updatedJobVacancy)
        writeFile(jobVacancies)
    }

    void delete(Integer id) {
        List<JobVacancy> jobVacancies = readFile()
        jobVacancies.removeIf {it.id == id}
        writeFile(jobVacancies)
    }

}
