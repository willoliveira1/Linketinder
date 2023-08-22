package com.linketinder.util

import groovy.json.JsonBuilder
import com.linketinder.domain.candidate.AcademicExperience
import com.linketinder.domain.candidate.Candidate
import com.linketinder.domain.candidate.Certificate
import com.linketinder.domain.candidate.CourseStatus
import com.linketinder.domain.candidate.WorkExperience
import com.linketinder.domain.shared.ContractType
import com.linketinder.domain.shared.Language
import com.linketinder.domain.shared.LocationType
import com.linketinder.domain.shared.Person
import com.linketinder.domain.shared.Proficiency
import com.linketinder.domain.shared.Skill
import com.linketinder.domain.shared.State

class CandidatesGenerator {

        void runGenerator() {
                Person candidate1 = new Candidate(
                        id: 1,
                        name: "Afonso Alberto",
                        email: "aa@gmail.com",
                        city: "Araraquara",
                        state: State.SP,
                        country: "Brasil",
                        cep: "14800000",
                        description: "Sobre mim 1",
                        cpf: "12345678900",
                        academicExperiences: [
                                new AcademicExperience(
                                        id: 1,
                                        educationalInstitution: "Anhanguera",
                                        degreeType: "Bacharel",
                                        fieldOfStudy: "Engenharia de Software",
                                        status: CourseStatus.Cursando
                                )
                        ],
                        workExperiences: [
                                new WorkExperience(
                                        id: 1,
                                        title: "Assistente Fiscal",
                                        companyName: "Empresa 1",
                                        contractType: ContractType.CLT,
                                        locationType: LocationType.Presencial,
                                        city: "Patos de Minas",
                                        state: State.MG,
                                        currentlyWork: true,
                                        description: "Descrição 1"
                                )
                        ],
                        languages: [
                                new Language(
                                        id: 1,
                                        name: "Português",
                                        proficiency: Proficiency.Avancado
                                )
                        ],
                        skills: [
                                new Skill(
                                        id: 1,
                                        title: "Java",
                                        proficiency: Proficiency.Intermediario
                                )
                        ],
                        certificates: [
                                new Certificate(
                                        id: 1,
                                        title: "Curso Certificado 1",
                                        duration: "6 meses",
                                )
                        ]
                )

                Person candidate2 = new Candidate(
                        id: 2,
                        name: "Breno Bernardo",
                        email: "bb@gmail.com",
                        city: "São Carlos",
                        state: State.SP,
                        country: "Brasil",
                        cep: "14900000",
                        description: "Sobre mim 2",
                        cpf: "45678912312",
                        academicExperiences: [
                                new AcademicExperience(
                                        id: 1,
                                        educationalInstitution: "FGV",
                                        degreeType: "Bacharel",
                                        fieldOfStudy: "Análise e Desenvolvimento de Sistemas",
                                        status: CourseStatus.Concluido
                                ),
                                new AcademicExperience(
                                        id: 2,
                                        educationalInstitution: "PUC",
                                        degreeType: "Mestrado",
                                        fieldOfStudy: "Engenharia de Software",
                                        status: CourseStatus.Cursando
                                )
                        ],
                        workExperiences: [],
                        languages: [
                                new Language(
                                        id: 1,
                                        name: "Português",
                                        proficiency: Proficiency.Avancado
                                ),
                                new Language(
                                        id: 2,
                                        name: "Inglês",
                                        proficiency: Proficiency.Basico
                                )
                        ],
                        skills: [
                                new Skill(
                                        id: 1,
                                        title: "Java",
                                        proficiency: Proficiency.Avancado
                                ),
                                new Skill(
                                        id: 2,
                                        title: "Groovy",
                                        proficiency: Proficiency.Intermediario
                                ),
                                new Skill(
                                        id: 3,
                                        title: "SQL",
                                        proficiency: Proficiency.Basico
                                )
                        ],
                        certificates: []
                )

                Person candidate3 = new Candidate(
                        id: 3,
                        name: "Carlos Carvalho",
                        email: "cc@gmail.com",
                        city: "Patos de Minas",
                        state: State.MG,
                        country: "Brasil",
                        cep: "17800000",
                        description: "Sobre mim 3",
                        cpf: "12398745617",
                        academicExperiences: [
                                new AcademicExperience(
                                        id: 1,
                                        educationalInstitution: "Logatti",
                                        degreeType: "Bacharel",
                                        fieldOfStudy: "Engenharia de Software",
                                        status: CourseStatus.Trancado
                                )
                        ],
                        workExperiences: [
                                new WorkExperience(
                                        id: 1,
                                        title: "Estagiário de Desenvolvimento",
                                        companyName: "Empresa 8",
                                        contractType: ContractType.Estagio,
                                        locationType: LocationType.Hibrido,
                                        city: "Patos de Minas",
                                        state: State.MG,
                                        currentlyWork: true,
                                        description: "Descrição 1"
                                )
                        ],
                        languages: [
                                new Language(
                                        id: 1,
                                        name: "Inglês",
                                        proficiency: Proficiency.Avancado
                                ),
                                new Language(
                                        id: 2,
                                        name: "Português",
                                        proficiency: Proficiency.Avancado
                                )
                        ],
                        skills: [
                                new Skill(
                                        id: 1,
                                        title: "Rust",
                                        proficiency: Proficiency.Intermediario
                                ),
                                new Skill(
                                        id: 2,
                                        title: "C++",
                                        proficiency: Proficiency.Basico
                                )
                        ],
                        certificates: [
                                new Certificate(
                                        id: 1,
                                        title: "Curso Certificado 1",
                                        duration: "1 ano",
                                ),
                                new Certificate(
                                        id: 2,
                                        title: "Curso Certificado 2",
                                        duration: "2 meses"
                                )
                        ]
                )

                Person candidate4 = new Candidate(
                        id: 4,
                        name: "Denis Delavechia",
                        email: "dd@hotmail.com",
                        city: "Curitiba",
                        state: State.PR,
                        country: "Brasil",
                        cep: "12300000",
                        description: "Sobre mim 4",
                        cpf: "45615167904",
                        academicExperiences: [
                                new AcademicExperience(
                                        id: 1,
                                        educationalInstitution: "UFPR",
                                        degreeType: "Bacharel",
                                        fieldOfStudy: "Engenharia da Computação",
                                        status: CourseStatus.Cursando
                                )
                        ],
                        workExperiences: [
                                new WorkExperience(
                                        id: 1,
                                        title: "Assistente Contábil",
                                        companyName: "Empresa Alfa",
                                        contractType: ContractType.Temporario,
                                        locationType: LocationType.Remoto,
                                        city: "Curitiba",
                                        state: State.PR,
                                        currentlyWork: false,
                                        description: "Descrição 1"
                                )
                        ],
                        languages: [
                                new Language(
                                        id: 1,
                                        name: "Português",
                                        proficiency: Proficiency.Avancado
                                ),
                                new Language(
                                        id: 2,
                                        name: "Inglês",
                                        proficiency: Proficiency.Basico
                                )
                        ],
                        skills: [
                                new Skill(
                                        id: 1,
                                        title: "Kotlin",
                                        proficiency: Proficiency.Avancado
                                ),
                                new Skill(
                                        id: 2,
                                        title: "PostgreSQL",
                                        proficiency: Proficiency.Intermediario
                                ),
                                new Skill(
                                        id: 3,
                                        title: "Angular",
                                        proficiency: Proficiency.Basico
                                )
                        ],
                        certificates: [
                                new Certificate(
                                        id: 1,
                                        title: "Curso Certificado 1",
                                        duration: "1 mês"
                                )
                        ]
                )

                Person candidate5 = new Candidate(
                        id: 5,
                        name: "Elaine Elalia",
                        email: "elaine@gmail.com",
                        city: "São Paulo",
                        state: State.SP,
                        country: "Brasil",
                        cep: "04500000",
                        description: "Sobre mim 5",
                        cpf: "98765432109",
                        academicExperiences: [
                                new AcademicExperience(
                                        id: 1,
                                        educationalInstitution: "USP",
                                        degreeType: "Bacharel",
                                        fieldOfStudy: "Ciência da Computação",
                                        status: CourseStatus.Concluido
                                )
                        ],
                        workExperiences: [
                                new WorkExperience(
                                        id: 1,
                                        title: "Desenvolvedora Web",
                                        companyName: "Empresa Neo",
                                        contractType: ContractType.PJ,
                                        locationType: LocationType.Presencial,
                                        city: "São Paulo",
                                        state: State.SP,
                                        currentlyWork: true,
                                        description: "Descrição 2"
                                )
                        ],
                        languages: [
                                new Language(
                                        id: 1,
                                        name: "Inglês",
                                        proficiency: Proficiency.Basico
                                ),
                                new Language(
                                        id: 2,
                                        name: "Espanhol",
                                        proficiency: Proficiency.Intermediario
                                )
                        ],
                        skills: [
                                new Skill(
                                        id: 1,
                                        title: "Python",
                                        proficiency: Proficiency.Avancado
                                ),
                                new Skill(
                                        id: 2,
                                        title: "JavaScript",
                                        proficiency: Proficiency.Intermediario
                                )
                        ],
                        certificates: [
                                new Certificate(
                                        id: 1,
                                        title: "Curso Certificado 1",
                                        duration: "3 meses"
                                )
                        ]
                )

                List<Person> candidates = [candidate1, candidate2, candidate3, candidate4, candidate5]

                JsonBuilder candidatesBuilder = new JsonBuilder(candidates)
                String filePath = "../../../resources/candidatos.json"
                def file = new File(filePath)
                file.createNewFile()
                file.write(candidatesBuilder.toPrettyString())
        }

}
