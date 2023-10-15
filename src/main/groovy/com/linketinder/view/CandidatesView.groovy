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
import com.linketinder.service.interfaces.ICandidateService
import com.linketinder.util.viewtexts.AcademicExperienceTexts
import com.linketinder.util.viewtexts.CandidateTexts
import com.linketinder.util.viewtexts.CertificateTexts
import com.linketinder.util.viewtexts.LanguageTexts
import com.linketinder.util.viewtexts.SkillTexts
import com.linketinder.util.viewtexts.WorkExperienceTexts
import com.linketinder.validation.interfaces.ICandidateValidation
import com.linketinder.view.interfaces.ICandidatesView

class CandidatesView implements ICandidatesView {

    ICandidateService service
    ICandidateValidation validation
    Readable reader = System.in.newReader()

    CandidatesView(ICandidateService service, ICandidateValidation validation) {
        this.service = service
        this.validation = validation
    }

    void getAllCandidates() {
        println CandidateTexts.ALL_CANDIDATES_TEXT
        List<Candidate> candidates = service.getAll()
        candidates.each {println it}
    }

    void getCandidateById() {
        Integer id = validation.validateId()
        println service.getById(id)
    }

    List<AcademicExperience> addAcademicExperiences() {
        println AcademicExperienceTexts.ADD_ACADEMIC_EXPERIENCE__TEXT
        List<AcademicExperience> academicExperiences = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println AcademicExperienceTexts.EDUCATIONAl_INSTITUTION_TEXT
            String educationalInstitution = reader.readLine()
            println AcademicExperienceTexts.DEGREE_TYPE_TEXT
            String degreeType = reader.readLine()
            println AcademicExperienceTexts.FIELD_OF_STUDY_TEXT
            String fieldOfStudy = reader.readLine()
            CourseStatus status = validation.validateCourseStatus()

            AcademicExperience experience = new AcademicExperience(null, educationalInstitution, degreeType,
                    fieldOfStudy, status)
            academicExperiences.add(experience)

            println AcademicExperienceTexts.ADD_MORE_ACADEMIC_EXPERIENCE_TEXT
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return academicExperiences
    }

    List<WorkExperience> addWorkExperiences() {
        println WorkExperienceTexts.ADD_WORK_EXPERIENCE_TEXT
        List<WorkExperience> workExperiences = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println WorkExperienceTexts.TITLE_TEXT
            String title = reader.readLine()
            println WorkExperienceTexts.COMPANY_TEXT
            String companyName = reader.readLine()
            ContractType contractType = validation.validateContractType()
            LocationType locationType = validation.validateLocationType()
            println WorkExperienceTexts.COMPANY_CITY_TEXT
            String city = reader.readLine()
            State state = validation.validateState()
            boolean currentlyWork = validation.validateCurrentlyWork()

            println WorkExperienceTexts.DESCRIPTION_TEXT
            String description = reader.readLine()

            WorkExperience experience = new WorkExperience(null, title, companyName, contractType, locationType,
                    city, state, currentlyWork, description)
            workExperiences.add(experience)

            println WorkExperienceTexts.ADD_MORE_WORK_EXPERIENCE_TEXT
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return workExperiences
    }

    List<Language> addLanguages() {
        println LanguageTexts.ADD_LANGUAGE_TEXT
        List<Language> languages = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println LanguageTexts.NAME_TEXT
            String name = reader.readLine()
            Proficiency proficiency = validation.validateProficiency()

            Language language = new Language(null, name, proficiency)
            languages.add(language)

            println LanguageTexts.ADD_MORE_LANGUGAGE_TEXT
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return languages
    }

    List<Skill> addSkills() {
        println SkillTexts.ADD_SKILL_TEXT
        List<Skill> skills = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println SkillTexts.TITLE_TEXT
            String title = reader.readLine()
            Proficiency proficiency = validation.validateProficiency()

            Skill skill = new Skill(null, title, proficiency)
            skills.add(skill)

            println SkillTexts.ADD_MORE_SKILL_TEXT
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return skills
    }

    List<Certificate> addCertificates() {
        println CertificateTexts.ADD_CERTIFICATE_TEXT
        List<Certificate> certificates = []
        String input = reader.readLine()
        Boolean addMore = validation.validateAddMore(input)

        while (addMore) {
            println CertificateTexts.TITLE_TEXT
            String title = reader.readLine()
            println CertificateTexts.DURATION_TEXT
            String duration = reader.readLine()

            Certificate certificate = new Certificate(null, title, duration)
            certificates.add(certificate)

            println CertificateTexts.ADD_MORE_CERTIFICATE_TEXT
            input = reader.readLine()
            addMore = validation.validateAddMore(input)
        }
        return certificates
    }

    void addCandidate() {
        println CandidateTexts.NAME_TEXT
        String name = reader.readLine()
        String email = validation.validateEmail()
        println CandidateTexts.CITY_TEXT
        String city = reader.readLine()
        State state = validation.validateState()
        println CandidateTexts.COUNTRY_TEXT
        String country = reader.readLine()
        String cep = validation.validateCep()
        println CandidateTexts.DESCRIPTION_TEXT
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
        println CandidateTexts.NAME_TEXT
        String name = reader.readLine()
        String email = validation.validateEmail()
        println CandidateTexts.CITY_TEXT
        String city = reader.readLine()
        State state = validation.validateState()
        println CandidateTexts.COUNTRY_TEXT
        String country = reader.readLine()
        String cep = validation.validateCep()
        println CandidateTexts.DESCRIPTION_TEXT
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
