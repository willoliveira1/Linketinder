package com.linketinder.view

import com.linketinder.domain.jobvacancy.JobVacancy
import com.linketinder.domain.jobvacancy.ContractType
import com.linketinder.domain.jobvacancy.LocationType
import com.linketinder.domain.shared.Skill
import com.linketinder.service.JobVacancyService

class JobVacanciesView {

    JobVacancyService service = new JobVacancyService()
    BufferedReader reader = System.in.newReader()

    void getAllJobVacancies() {
        println "Listagem de Vagas:"
        List<JobVacancy> jobVacancies = service.getAll()
        jobVacancies.each {println it}
    }

    void getAllJobVacanciesByCompanyId() {
        println "Qual id da empresa que deseja ver as vagas?"
        Integer id
        try {
            id = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "Argumento inválido."
            getAllJobVacanciesByCompanyId()
        }
        List<JobVacancy> jobVacancies = service.getAllByCompanyId(id)
        println "Listagem de Vagas por Id da Empresa:"
        jobVacancies.each {println it}
    }

    void getJobVacancyById() {
        println "Qual id da vaga que deseja ver o detalhamento?"
        Integer id
        try {
            id = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "Argumento inválido."
            getJobVacancyById()
        }
        JobVacancy jobVacancy = service.getById(id)
        if (jobVacancy == null) {
            println "Vaga não encontrada."
            return
        }
        println jobVacancy
    }

    void addJobVacancy() {
        println "Qual o id da empresa?"
        Integer companyId = reader.readLine() as Integer
        println "Qual o nome da vaga?"
        String title = reader.readLine()
        println "Qual a descrição da vaga?"
        String description = reader.readLine()
        println "Qual o salário da vaga?"
        Double salary = Double.parseDouble(reader.readLine())
        println "Qual o tipo de contrato? (CLT/PJ/Temporário/Estágio/Aprendiz)"
        ContractType contractType
        try {
            String input = reader.readLine()
            contractType = ContractType.valueOf(input)
        } catch (IllegalArgumentException e) {
            println "Tipo de contrato ${contractType} inválido."
            return
        }
        println "Qual o regime de trabalho? (Presencial/Híbrido/Remoto)"
        LocationType locationType
        try {
            String input = reader.readLine()
            locationType = LocationType.valueOf(input)
        } catch (IllegalArgumentException e) {
            println "Regime de trabalho ${locationType} inválido."
            return
        }

        println "Deseja adicionar habilidades necessárias? (S/N)"
        String input = reader.readLine()
        List<Skill> requiredSkills = []
        if (input.toUpperCase().equals("S")) {
            requiredSkills = addRequiredSkills()
        }

        JobVacancy jobVacancy = new JobVacancy(null, title, description, requiredSkills, salary, contractType, locationType)

        service.add(companyId, jobVacancy)
    }

    void removeJobVacancy() {
        println "Qual o id da vaga a ser removida?"
        Integer id
        try {
            id = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "Argumento inválido."
            removeJobVacancy()
        }
        JobVacancy jobVacancy = service.getById(id)
        if (jobVacancy == null) {
            println "Vaga não encontrada."
            return
        }
        service.delete(id)
        println "Vaga removida"
    }

    void updateJobVacancy() {
        println "Qual o id da vaga que deseja atualizar?"
        Integer id
        try {
            id = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "id inválido."
            return
        }
        service.getById(id)

        println "Qual o nome da vaga?"
        String title = reader.readLine()
        println "Qual a descrição da vaga?"
        String description = reader.readLine()
        println "Qual o salário da vaga?"
        Double salary = Double.parseDouble(reader.readLine())
        println "Qual o tipo de contrato? (CLT/PJ/Temporário/Estágio/Aprendiz)"
        ContractType contractType
        try {
            String input = reader.readLine()
            contractType = ContractType.valueOf(input)
        } catch (IllegalArgumentException e) {
            println "Tipo de contrato ${contractType} inválido."
            return
        }
        println "Qual o regime de trabalho? (Presencial/Híbrido/Remoto)"
        LocationType locationType
        try {
            String input = reader.readLine()
            locationType = LocationType.valueOf(input)
        } catch (IllegalArgumentException e) {
            println "Regime de trabalho ${locationType} inválido."
            return
        }

        println "Deseja adicionar habilidades necessárias? (S/N)"
        String input = reader.readLine()
        List<Skill> requiredSkills = []
        if (input.toUpperCase().equals("S")) {
            requiredSkills = addRequiredSkills()
        }
        JobVacancy jobVacancy = new JobVacancy(null, title, description, requiredSkills, salary, contractType, locationType)
        service.update(id, jobVacancy)
    }

    List<Skill> addRequiredSkills() {
        List<Skill> skills = []
        Integer addMore = 1

        while (addMore) {
            skills.size() == 0 ? 1 : (skills.size() + 1)
            println "Qual a habilidade?"
            String title = reader.readLine()

            Skill skill = new Skill(null, title)
            skills.add(skill)

            println "Deseja adicionar mais habilidades necessárias? (S/N)"
            String input = reader.readLine()
            if (input.toUpperCase().equals("S")) {
                addMore = 1
            } else {
                addMore = 0
            }
        }
        return skills
    }

}
