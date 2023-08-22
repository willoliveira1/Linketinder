package com.linketinder.view

import com.linketinder.domain.candidate.AcademicExperience
import com.linketinder.domain.candidate.Candidate
import com.linketinder.domain.candidate.Certificate
import com.linketinder.domain.candidate.CourseStatus
import com.linketinder.domain.candidate.WorkExperience
import com.linketinder.domain.shared.ContractType
import com.linketinder.domain.shared.Language
import com.linketinder.domain.shared.LocationType
import com.linketinder.domain.shared.Proficiency
import com.linketinder.domain.shared.Skill
import com.linketinder.domain.shared.State
import com.linketinder.service.CandidateService
import com.linketinder.util.ObjectHandler

class CandidatesView {

    CandidateService service = new CandidateService()
    BufferedReader reader = System.in.newReader()

    void getAllCandidates() {
        println "Listagem de Candidatos:"
        List<Candidate> candidates = service.getAll()
        candidates.each {println it}
    }

    void getCandidateById() {
        println "Qual id do Candidato que deseja ver o detalhamento?"
        Integer id
        try {
            id = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "Argumento inválido."
            getCandidateById()
        }
        Candidate candidate = service.getById(id)
        if (candidate == null) {
            println "Candidato não encontrado."
            return
        }
        println candidate
    }

    void addCandidate() {
        List<Candidate> candidates = service.getAll()
        Integer id
        if (candidates.size() == 0) {
            id = 1
        } else {
            id = ObjectHandler.getNextId(candidates)
        }
        println "Qual o nome do candidato?"
        String name = reader.readLine()
        println "Qual o email do candidato?"
        String email = reader.readLine()
        println "Qual a cidade do candidato?"
        String city = reader.readLine()
        println "Qual a sigla do estado do candidato?"
        State state
        try {
            String input = reader.readLine()
            state = State.valueOf(input.toUpperCase())
        } catch (IllegalArgumentException e) {
            println "Estado ${state} é inválido."
            return
        }
        println "Qual o país do candidato?"
        String country = reader.readLine()
        println "Qual o cep do candidato?"
        String cep = reader.readLine()
        if (cep.length() < 8 || cep.length() > 10) {
            println "Cep ${cep} é inválido."
            return
        }
        println "Quais informações pessoais o candidato quer informar?"
        String description = reader.readLine()
        println "Qual cpf do candidato?"
        String cpf = reader.readLine()
        if (cpf.length() < 11 || cpf.length() > 14) {
            println "Tamanho de CPF inválido."
            return
        }
        println "Deseja adicionar experiências acadêmicas? (S/N)"
        String input = reader.readLine()
        List<AcademicExperience> academicExperiences = []
        if (input.toUpperCase().equals("S")) {
            academicExperiences = addAcademicExperiences()
        }
        println "Deseja adicionar experiências profissionais? (S/N)"
        input = reader.readLine()
        List<WorkExperience> workExperiences = []
        if (input.toUpperCase().equals("S")) {
            workExperiences = addWorkExperiences()
        }
        println "Deseja adicionar idiomas? (S/N)"
        input = reader.readLine()
        List<Language> languages = []
        if (input.toUpperCase().equals("S")) {
            languages = addLanguages()
        }
        println "Deseja adicionar competências? (S/N)"
        input = reader.readLine()
        List<Skill> skills = []
        if (input.toUpperCase().equals("S")) {
            skills = addSkills()
        }
        println "Deseja adicionar certificados? (S/N)"
        input = reader.readLine()
        List<Certificate> certificates = []
        if (input.toUpperCase().equals("S")) {
            certificates = addCertificates()
        }

        Candidate candidate = new Candidate(id, name, email, city, state, country, cep, description,
                cpf, academicExperiences, workExperiences, languages, skills, certificates)
        service.add(candidate)
    }

    void removeCandidate() {
        println "Qual o id do candidato a ser removido?"
        Integer id
        try {
            id = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "Argumento inválido."
            removeCandidate()
        }
        Candidate candidate = service.getById(id)
        if (candidate == null) {
            println "Candidato não encontrado."
            return
        }
        service.delete(id)
        println "Candidato Removido"
    }

    void updateCandidate() {
        println "Qual o id do candidato que deseja atualizar"
        Integer id
        try {
            id = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println "id inválido."
            return
        }
        Candidate candidate = service.getById(id)

        println "Qual o nome do candidato?"
        String name = reader.readLine()
        println "Qual o email do candidato?"
        String email = reader.readLine()
        println "Qual a cidade do candidato?"
        String city = reader.readLine()
        println "Qual a sigla do estado do candidato?"
        State state
        try {
            String input = reader.readLine()
            state = State.valueOf(input.toUpperCase())
        } catch (IllegalArgumentException e) {
            println "Estado ${state} é inválido."
            return
        }
        println "Qual o país do candidato?"
        String country = reader.readLine()
        println "Qual o cep do candidato?"
        String cep = reader.readLine()
        if (cep.length() < 8 || cep.length() > 10) {
            println "Cep ${cep} é inválido."
            return
        }
        println "Quais informações pessoais o candidato quer informar?"
        String description = reader.readLine()
        println "Qual CPF do candidato?"
        String cpf = reader.readLine()
        if (cpf.length() < 11 || cpf.length() > 14) {
            println "Tamanho de CPF inválido."
            return
        }
        println "Deseja adicionar experiências acadêmicas? (S/N)"
        String input = reader.readLine()
        List<AcademicExperience> academicExperiences = []
        if (input.toUpperCase().equals("S")) {
            academicExperiences = addAcademicExperiences()
        }
        println "Deseja adicionar experiências profissionais? (S/N)"
        input = reader.readLine()
        List<WorkExperience> workExperiences = []
        if (input.toUpperCase().equals("S")) {
            workExperiences = addWorkExperiences()
        }
        println "Deseja adicionar idiomas? (S/N)"
        input = reader.readLine()
        List<Language> languages = []
        if (input.toUpperCase().equals("S")) {
            languages = addLanguages()
        }
        println "Deseja adicionar competências? (S/N)"
        input = reader.readLine()
        List<Skill> skills = []
        if (input.toUpperCase().equals("S")) {
            skills = addSkills()
        }
        println "Deseja adicionar certificados? (S/N)"
        input = reader.readLine()
        List<Certificate> certificates = []
        if (input.toUpperCase().equals("S")) {
            certificates = addCertificates()
        }

        Candidate updatedCandidate = new Candidate(candidate.id, name, email, city, state, country, cep, description,
                cpf, academicExperiences, workExperiences, languages, skills, certificates)
        service.update(id, updatedCandidate)
    }

    List<AcademicExperience> addAcademicExperiences() {
        List<AcademicExperience> academicExperiences = []
        Integer addMore = 1

        while (addMore) {
            Integer id
            if (academicExperiences.size() == 0) {
                id = 1
            } else {
                id = academicExperiences.size() + 1
            }
            println "Qual o nome da instituição de ensino?"
            String educationalInstitution = reader.readLine()
            println "Qual o tipo de diploma?"
            String degreeType = reader.readLine()
            println "Qual o curso?"
            String fieldOfStudy = reader.readLine()
            println "Qual o status atual do curso? (Cursando/Concluido/Trancado)"
            String input = reader.readLine()
            CourseStatus status
            try {
                status = CourseStatus.valueOf(input)
            } catch (IllegalArgumentException e) {
                println "Status inválido."
                addAcademicExperiences()
            }

            AcademicExperience experience = new AcademicExperience(id, educationalInstitution, degreeType, fieldOfStudy, status)
            academicExperiences.add(experience)

            println "Deseja adicionar mais experiências acadêmicas? (S/N)"
            input = reader.readLine()
            if (input.toUpperCase().equals("S")) {
                addMore = 1
            } else {
                addMore = 0
            }
        }
        return academicExperiences
    }

    List<WorkExperience> addWorkExperiences() {
        List<WorkExperience> workExperiences = []
        Integer addMore = 1

        while (addMore) {
            Integer id
            workExperiences.size() == 0 ? 1 : (workExperiences.size() + 1)
            println "Qual o cargo?"
            String title = reader.readLine()
            println "Qual a empresa?"
            String companyName = reader.readLine()
            println "Qual o tipo de contrato? (CLT/PJ/Temporario/Estagio/Aprendiz)"
            ContractType contractType
            String input = reader.readLine()
            try {
                contractType = ContractType.valueOf(input)
            } catch (IllegalArgumentException e) {
                println "Argumento inválido."
                addWorkExperiences()
            }
            println "Qual o regime de trabalho? (Presencial/Hibrido/Remoto)"
            LocationType locationType
            input = reader.readLine()
            try {
                locationType = LocationType.valueOf(input)
            } catch (IllegalArgumentException e) {
                println "Argumento inválido."
                addWorkExperiences()
            }
            println "Qual a cidade?"
            String city = reader.readLine()
            println "Qual a sigla do estado?"
            State state
            try {
                input = reader.readLine()
                state = State.valueOf(input.toUpperCase())
            } catch (IllegalArgumentException e) {
                println "Estado ${state} é inválido."
                return
            }
            println "É o emprego atual? (S/N)"
            Boolean currentlyWork
            input = reader.readLine()
            input.toUpperCase().equals("S") ? (currentlyWork = true) : (currentlyWork = false)

            println "Descreva as funções desempenhadas na vaga:"
            String description = reader.readLine()

            WorkExperience experience = new WorkExperience(id, title, companyName, contractType, locationType, city, state, currentlyWork, description)
            workExperiences.add(experience)

            println "Deseja adicionar mais experiências profissionais? (S/N)"
            input = reader.readLine()
            if (input.toUpperCase().equals("S")) {
                addMore = 1
            } else {
                addMore = 0
            }
        }
        return workExperiences
    }

    List<Language> addLanguages() {
        List<Language> languages = []
        Integer addMore = 1

        while (addMore) {
            Integer id
            languages.size() == 0 ? 1 : (languages.size() + 1)
            println "Qual o idioma?"
            String name = reader.readLine()
            println "Qual nível de proficiência? (Basico/Intermediario/Avancado)"
            String input = reader.readLine()
            Proficiency proficiency
            try {
                proficiency = Proficiency.valueOf(input)
            } catch (IllegalArgumentException e) {
                println "Argumento inválido."
                addLanguages()
            }

            Language language = new Language(id, name, proficiency)
            languages.add(language)

            println "Deseja adicionar mais idiomas? (S/N)"
            input = reader.readLine()
            if (input.toUpperCase().equals("S")) {
                addMore = 1
            } else {
                addMore = 0
            }
        }
        return languages
    }

    List<Skill> addSkills() {
        List<Skill> skills = []
        Integer addMore = 1

        while (addMore) {
            Integer id
            skills.size() == 0 ? 1 : (skills.size() + 1)
            println "Qual a competência?"
            String title = reader.readLine()
            println "Qual nível de proficiência? (Basico/Intermediario/Avancado)"
            String input = reader.readLine()
            Proficiency proficiency
            try {
                proficiency = Proficiency.valueOf(input)
            } catch (IllegalArgumentException e) {
                println "Argumento inválido."
                addSkills()
            }

            Skill skill = new Skill(id, title, proficiency)
            skills.add(skill)

            println "Deseja adicionar mais competências? (S/N)"
            input = reader.readLine()
            if (input.toUpperCase().equals("S")) {
                addMore = 1
            } else {
                addMore = 0
            }
        }
        return skills
    }

    List<Certificate> addCertificates() {
        List<Certificate> certificates = []
        Integer addMore = 1

        while (addMore) {
            Integer id
            certificates.size() == 0 ? 1 : (certificates.size() + 1)
            println "Qual o título do certificado?"
            String title = reader.readLine()
            println "Qual a duração?"
            String duration = reader.readLine()

            Certificate certificate = new Certificate(id, title, duration)
            certificates.add(certificate)

            println "Deseja adicionar mais certificações? (S/N)"
            String input = reader.readLine()
            if (input.toUpperCase().equals("S")) {
                addMore = 1
            } else {
                addMore = 0
            }
        }
        return certificates
    }

}
