package com.linketinder.view

import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.LocationType
import com.linketinder.model.shared.Skill
import com.linketinder.service.JobVacancyService
import com.linketinder.validation.JobVacancyValidation

class JobVacanciesView {

    JobVacancyService service = new JobVacancyService()
    BufferedReader reader = System.in.newReader()
    JobVacancyValidation validation = new JobVacancyValidation()

    void getAllJobVacancies() {
        println "Listagem de Vagas:"
        List<JobVacancy> jobVacancies = service.getAll()
        jobVacancies.each {println it}
    }

    void getAllJobVacanciesByCompanyId() {
        Integer id = validation.validateId()
        List<JobVacancy> jobVacancies = service.getAllByCompanyId(id)
        println "Listagem de Vagas da Empresa de Id ${id}:"
        jobVacancies.each {println it}
    }

    void getJobVacancyById() {
        Integer id = validation.validateId()
        println service.getById(id)
    }

    List<Skill> addRequiredSkills() {
        println "Deseja adicionar habilidades necessárias? (S/N)"
        List<Skill> skills = []
        String input = reader.readLine()
        boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println "Qual a habilidade?"
            String title = reader.readLine()

            Skill skill = new Skill(null, title)
            skills.add(skill)

            println "Deseja adicionar mais habilidades necessárias? (S/N)"
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return skills
    }

    void addJobVacancy() {
        Integer companyId = validation.validateCompanyId()
        println "Qual o nome da vaga?"
        String title = reader.readLine()
        println "Qual a descrição da vaga?"
        String description = reader.readLine()
        Double salary = validation.validateSalary()
        ContractType contractType = validation.validateContractType()
        LocationType locationType = validation.validateLocationType()
        List<Skill> requiredSkills = addRequiredSkills()

        JobVacancy jobVacancy = new JobVacancy(null, title, description, requiredSkills, salary, contractType,
                locationType)
        service.add(companyId, jobVacancy)
    }

    void updateJobVacancy() {
        Integer id = validation.validateId()
        println "Qual o nome da vaga?"
        String title = reader.readLine()
        println "Qual a descrição da vaga?"
        String description = reader.readLine()
        Double salary = validation.validateSalary()
        ContractType contractType = validation.validateContractType()
        LocationType locationType = validation.validateLocationType()
        List<Skill> requiredSkills = addRequiredSkills()

        JobVacancy jobVacancy = new JobVacancy(id, title, description, requiredSkills, salary, contractType,
                locationType)
        service.update(jobVacancy)
    }

    void removeJobVacancy() {
        Integer id = validation.validateId()
        service.delete(id)
    }

}
