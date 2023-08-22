package com.linketinder.util

import groovy.json.JsonBuilder
import com.linketinder.domain.jobvacancy.JobVacancy
import com.linketinder.domain.shared.ContractType
import com.linketinder.domain.shared.LocationType
import com.linketinder.domain.shared.Skill

class JobVacanciesGenerator {

    void runGenerator() {
        JobVacancy jobVacancy1 = new JobVacancy(
                id: 1,
                title: "Vaga 1",
                description: "Descrição 1",
                requiredSkills: [
                        new Skill(
                                id: 1,
                                title: "Python"
                        )
                ],
                salary: 1000,
                contractType: ContractType.Estagio,
                locationType: LocationType.Hibrido
        )

        JobVacancy jobVacancy2 = new JobVacancy(
                id: 2,
                title: "Vaga 2",
                description: "Descrição 2",
                requiredSkills: [
                        new Skill(
                                id: 1,
                                title: "C#"
                        ),
                        new Skill(
                                id: 1,
                                title: "REST API"
                        ),
                ],
                salary: 2000.0,
                contractType: ContractType.Temporario,
                locationType: LocationType.Presencial
        )

        JobVacancy jobVacancy3 = new JobVacancy(
                id: 3,
                title: "Vaga 3",
                description: "Descrição 3",
                requiredSkills: [
                        new Skill(
                                id: 1,
                                title: "Java"
                        ),
                        new Skill(
                                id: 2,
                                title: "Groovy"
                        ),
                        new Skill(
                                id: 3,
                                title: "SQL"
                        )
                ],
                salary: 3000.0,
                contractType: ContractType.CLT,
                locationType: LocationType.Remoto
        )

        List<JobVacancy> jobVacancies = [jobVacancy1, jobVacancy2, jobVacancy3]

        JsonBuilder jobVacanciesBuilder = new JsonBuilder(jobVacancies)
        String filePath = "../../../resources/vagas.json"
        def file = new File(filePath)
        file.createNewFile()
        file.write(jobVacanciesBuilder.toPrettyString())
    }

}
