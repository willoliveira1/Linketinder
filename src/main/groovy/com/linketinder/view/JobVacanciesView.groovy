package com.linketinder.view

import com.linketinder.controller.interfaces.IJobVacancyController
import com.linketinder.model.jobvacancy.*
import com.linketinder.model.shared.Skill
import com.linketinder.util.viewtexts.JobVacancyTexts
import com.linketinder.validation.interfaces.IJobVacancyValidation
import com.linketinder.view.interfaces.IJobVacanciesView

class JobVacanciesView implements IJobVacanciesView {

    IJobVacancyController controller
    IJobVacancyValidation validation
    Readable reader = System.in.newReader()

    JobVacanciesView(IJobVacancyController controller, IJobVacancyValidation validation) {
        this.controller = controller
        this.validation = validation
    }

    void getAllJobVacancies() {
        println JobVacancyTexts.ALL_JOB_VACANCIES_TEXT
        List<JobVacancy> jobVacancies = this.controller.getAll()
        jobVacancies.each {println it}
    }

    void getAllJobVacanciesByCompanyId() {
        Integer id = this.validation.validateId()
        List<JobVacancy> jobVacancies = this.controller.getAllByCompanyId(id)
        println JobVacancyTexts.ALL_JOB_VACANCIES_BY_ID_TEXT + id+ ":"
        jobVacancies.each {println it}
    }

    void getJobVacancyById() {
        Integer id = this.validation.validateId()
        println this.controller.getById(id)
    }

    List<Skill> addRequiredSkills() {
        println JobVacancyTexts.ADD_SKILL_TEXT
        List<Skill> skills = []
        String input = reader.readLine()
        boolean addMore = this.validation.validateAddMore(input)

        while (addMore) {
            println JobVacancyTexts.SKILL_TEXT
            String title = reader.readLine()

            Skill skill = new Skill(null, title)
            skills.add(skill)

            println JobVacancyTexts.ADD_MORE_SKILL_TEXT
            input = reader.readLine()
            addMore = this.validation.validateAddMore(input)
        }
        return skills
    }

    void addJobVacancy() {
        Integer companyId = this.validation.validateCompanyId()
        println JobVacancyTexts.NAME_TEXT
        String title = reader.readLine()
        println JobVacancyTexts.DESCRIPTION_TEXT
        String description = reader.readLine()
        Double salary = this.validation.validateSalary()
        ContractType contractType = this.validation.validateContractType()
        LocationType locationType = this.validation.validateLocationType()
        List<Skill> requiredSkills = addRequiredSkills()

        JobVacancy jobVacancy = new JobVacancy(null, title, description, requiredSkills, salary, contractType,
                locationType)
        this.controller.add(companyId, jobVacancy)
    }

    void updateJobVacancy() {
        Integer id = this.validation.validateId()
        println JobVacancyTexts.NAME_TEXT
        String title = reader.readLine()
        println JobVacancyTexts.DESCRIPTION_TEXT
        String description = reader.readLine()
        Double salary = this.validation.validateSalary()
        ContractType contractType = this.validation.validateContractType()
        LocationType locationType = this.validation.validateLocationType()
        List<Skill> requiredSkills = addRequiredSkills()

        JobVacancy jobVacancy = new JobVacancy(id, title, description, requiredSkills, salary, contractType,
                locationType)
        this.controller.update(jobVacancy)
    }

    void removeJobVacancy() {
        Integer id = this.validation.validateId()
        this.controller.delete(id)
    }

}
