package com.linketinder.view

import com.linketinder.model.candidate.AcademicExperience
import com.linketinder.model.candidate.Candidate
import com.linketinder.model.candidate.Certificate
import com.linketinder.model.candidate.CourseStatus
import com.linketinder.model.candidate.Language
import com.linketinder.model.candidate.WorkExperience
import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.LocationType
import com.linketinder.model.shared.Proficiency
import com.linketinder.model.shared.Skill
import com.linketinder.model.shared.State
import com.linketinder.service.CandidateService
import com.linketinder.service.IBaseService
import com.linketinder.validation.CandidateValidation
import com.linketinder.validation.IPersonValidation

class CandidatesView {

    BufferedReader reader = System.in.newReader()
    IBaseService service = new CandidateService()
    IPersonValidation validation = new CandidateValidation()

    void getAllCandidates() {
        println "Listagem de Candidatos:"
        List<Candidate> candidates = service.getAll()
        candidates.each {println it}
    }

    void getCandidateById() {
        Integer id = validation.validateId()
        println service.getById(id)
    }

    List<AcademicExperience> addAcademicExperiences() {
        println "Deseja adicionar experiências acadêmicas? (S/N)"
        List<AcademicExperience> academicExperiences = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println "Qual o nome da instituição de ensino?"
            String educationalInstitution = reader.readLine()
            println "Qual o tipo de diploma?"
            String degreeType = reader.readLine()
            println "Qual o curso?"
            String fieldOfStudy = reader.readLine()
            CourseStatus status = validation.validateCourseStatus()

            AcademicExperience experience = new AcademicExperience(null, educationalInstitution, degreeType,
                    fieldOfStudy, status)
            academicExperiences.add(experience)

            println "Deseja adicionar mais experiências acadêmicas? (S/N)"
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return academicExperiences
    }

    List<WorkExperience> addWorkExperiences() {
        println "Deseja adicionar experiências profissionais? (S/N)"
        List<WorkExperience> workExperiences = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println "Qual o cargo?"
            String title = reader.readLine()
            println "Qual a empresa?"
            String companyName = reader.readLine()
            ContractType contractType = validation.validateContractType()
            LocationType locationType = validation.validateLocationType()
            println "Qual a cidade?"
            String city = reader.readLine()
            State state = validation.validateState()
            boolean currentlyWork = validation.validateCurrentlyWork()

            println "Descreva as funções desempenhadas na vaga:"
            String description = reader.readLine()

            WorkExperience experience = new WorkExperience(null, title, companyName, contractType, locationType,
                    city, state, currentlyWork, description)
            workExperiences.add(experience)

            println "Deseja adicionar mais experiências profissionais? (S/N)"
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return workExperiences
    }

    List<Language> addLanguages() {
        println "Deseja adicionar idiomas? (S/N)"
        List<Language> languages = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println "Qual o idioma?"
            String name = reader.readLine()
            Proficiency proficiency = validation.validateProficiency()

            Language language = new Language(null, name, proficiency)
            languages.add(language)

            println "Deseja adicionar mais idiomas? (S/N)"
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return languages
    }

    List<Skill> addSkills() {
        println "Deseja adicionar competências? (S/N)"
        List<Skill> skills = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println "Qual a competência?"
            String title = reader.readLine()
            Proficiency proficiency = validation.validateProficiency()

            Skill skill = new Skill(null, title, proficiency)
            skills.add(skill)

            println "Deseja adicionar mais competências? (S/N)"
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return skills
    }

    List<Certificate> addCertificates() {
        println "Deseja adicionar certificados? (S/N)"
        List<Certificate> certificates = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println "Qual o título do certificado?"
            String title = reader.readLine()
            println "Qual a duração?"
            String duration = reader.readLine()

            Certificate certificate = new Certificate(null, title, duration)
            certificates.add(certificate)

            println "Deseja adicionar mais certificações? (S/N)"
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return certificates
    }

    void addCandidate() {
        println "Qual o nome do candidato?"
        String name = reader.readLine()
        String email = validation.validateEmail()
        println "Qual a cidade do candidato?"
        String city = reader.readLine()
        State state = validation.validateState()
        println "Qual o país do candidato?"
        String country = reader.readLine()
        String cep = validation.validateCep()
        println "Quais informações pessoais o candidato quer informar?"
        String description = reader.readLine()
        String cpf = validation.validateCpf()
        List<AcademicExperience> academicExperiences = addAcademicExperiences()
        List<WorkExperience> workExperiences = addWorkExperiences()
        List<Language> languages = addLanguages()
        List<Skill> skills = addSkills()
        List<Certificate> certificates = addCertificates()

        Candidate candidate = new Candidate(null, name, email, city, state, country, cep, description, cpf,
                academicExperiences, workExperiences, languages, skills, certificates)
        service.add(candidate)
    }

    void updateCandidate() {
        Integer id = validation.validateId()
        println "Qual o nome do candidato?"
        String name = reader.readLine()
        String email = validation.validateEmail()
        println "Qual a cidade do candidato?"
        String city = reader.readLine()
        State state = validation.validateState()
        println "Qual o país do candidato?"
        String country = reader.readLine()
        String cep = validation.validateCep()
        println "Quais informações pessoais o candidato quer informar?"
        String description = reader.readLine()
        String cpf = validation.validateCpf()
        List<AcademicExperience> academicExperiences = addAcademicExperiences()
        List<WorkExperience> workExperiences = addWorkExperiences()
        List<Language> languages = addLanguages()
        List<Skill> skills = addSkills()
        List<Certificate> certificates = addCertificates()

        Candidate updatedCandidate = new Candidate(id, name, email, city, state, country, cep, description, cpf,
                academicExperiences, workExperiences, languages, skills, certificates)
        service.update(updatedCandidate.id, updatedCandidate)
    }

    void removeCandidate() {
        Integer id = validation.validateId()
        service.delete(id)
    }

}
