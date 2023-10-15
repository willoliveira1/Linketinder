package com.linketinder.view

import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.LocationType
import com.linketinder.model.shared.Skill
import com.linketinder.service.interfaces.IJobVacancyService
import com.linketinder.util.viewtexts.JobVacancyTexts
import com.linketinder.validation.interfaces.IJobVacancyValidation
import com.linketinder.view.interfaces.IJobVacanciesView

class JobVacanciesView implements IJobVacanciesView {

    IJobVacancyService service
    IJobVacancyValidation validation
    Readable reader = System.in.newReader()

    JobVacanciesView(IJobVacancyService service, IJobVacancyValidation validation) {
        this.service = service
        this.validation = validation
    }

    void getAllJobVacancies() {
        println JobVacancyTexts.ALL_JOB_VACANCIES_TEXT
        List<JobVacancy> jobVacancies = service.getAll()
        jobVacancies.each {println it}
    }

    void getAllJobVacanciesByCompanyId() {
        Integer id = validation.validateId()
        List<JobVacancy> jobVacancies = service.getAllByCompanyId(id)
        println JobVacancyTexts.ALL_JOB_VACANCIES_BY_ID_TEXT + id+ ":"
        jobVacancies.each {println it}
    }

    void getJobVacancyById() {
        Integer id = validation.validateId()
        println service.getById(id)
    }

    List<Skill> addRequiredSkills() {
        println JobVacancyTexts.ADD_SKILL_TEXT
        List<Skill> skills = []
        String input = reader.readLine()
        boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println JobVacancyTexts.SKILL_TEXT
            String title = reader.readLine()

            Skill skill = new Skill(null, title)
            skills.add(skill)

            println JobVacancyTexts.ADD_MORE_SKILL_TEXT
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return skills
    }

    void addJobVacancy() {
        Integer companyId = validation.validateCompanyId()
        println JobVacancyTexts.NAME_TEXT
        String title = reader.readLine()
        println JobVacancyTexts.DESCRIPTION_TEXT
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
        println JobVacancyTexts.NAME_TEXT
        String title = reader.readLine()
        println JobVacancyTexts.DESCRIPTION_TEXT
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
