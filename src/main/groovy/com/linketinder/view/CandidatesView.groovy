package com.linketinder.view

import com.linketinder.controller.interfaces.ICandidateController
import com.linketinder.model.candidate.*
import com.linketinder.model.jobvacancy.*
import com.linketinder.model.shared.*
import com.linketinder.util.viewtexts.*
import com.linketinder.validation.interfaces.ICandidateValidation
import com.linketinder.view.interfaces.ICandidatesView

class CandidatesView implements ICandidatesView {

    ICandidateController controller
    ICandidateValidation validation
    Readable reader = System.in.newReader()

    CandidatesView(ICandidateController controller, ICandidateValidation validation) {
        this.controller = controller
        this.validation = validation
    }

    void getAllCandidates() {
        println CandidateTexts.ALL_CANDIDATES_TEXT
        List<Candidate> candidates = this.controller.getAll()
        candidates.each {println it}
    }

    void getCandidateById() {
        Integer id = this.validation.validateId()
        println this.controller.getById(id)
    }

    private List<AcademicExperience> addAcademicExperiences() {
        println AcademicExperienceTexts.ADD_ACADEMIC_EXPERIENCE__TEXT
        List<AcademicExperience> academicExperiences = []
        String input = reader.readLine()
        Boolean addMore = this.validation.validateAddMore(input)

        while (addMore) {
            println AcademicExperienceTexts.EDUCATIONAl_INSTITUTION_TEXT
            String educationalInstitution = reader.readLine()
            println AcademicExperienceTexts.DEGREE_TYPE_TEXT
            String degreeType = reader.readLine()
            println AcademicExperienceTexts.FIELD_OF_STUDY_TEXT
            String fieldOfStudy = reader.readLine()
            CourseStatus status = this.validation.validateCourseStatus()

            AcademicExperience experience = new AcademicExperience(null, educationalInstitution, degreeType,
                    fieldOfStudy, status)
            academicExperiences.add(experience)

            println AcademicExperienceTexts.ADD_MORE_ACADEMIC_EXPERIENCE_TEXT
            input = reader.readLine()
            addMore = this.validation.validateAddMore(input)
        }
        return academicExperiences
    }

    private List<WorkExperience> addWorkExperiences() {
        println WorkExperienceTexts.ADD_WORK_EXPERIENCE_TEXT
        List<WorkExperience> workExperiences = []
        String input = reader.readLine()
        Boolean addMore = this.validation.validateAddMore(input)

        while (addMore) {
            println WorkExperienceTexts.TITLE_TEXT
            String title = reader.readLine()
            println WorkExperienceTexts.COMPANY_TEXT
            String companyName = reader.readLine()
            ContractType contractType = this.validation.validateContractType()
            LocationType locationType = this.validation.validateLocationType()
            println WorkExperienceTexts.COMPANY_CITY_TEXT
            String city = reader.readLine()
            State state = this.validation.validateState()
            boolean currentlyWork = this.validation.validateCurrentlyWork()

            println WorkExperienceTexts.DESCRIPTION_TEXT
            String description = reader.readLine()

            WorkExperience experience = new WorkExperience(null, title, companyName, contractType, locationType,
                    city, state, currentlyWork, description)
            workExperiences.add(experience)

            println WorkExperienceTexts.ADD_MORE_WORK_EXPERIENCE_TEXT
            input = reader.readLine()
            addMore = this.validation.validateAddMore(input)
        }
        return workExperiences
    }

    private List<Language> addLanguages() {
        println LanguageTexts.ADD_LANGUAGE_TEXT
        List<Language> languages = []
        String input = reader.readLine()
        Boolean addMore = this.validation.validateAddMore(input)

        while (addMore) {
            println LanguageTexts.NAME_TEXT
            String name = reader.readLine()
            Proficiency proficiency = this.validation.validateProficiency()

            Language language = new Language(null, name, proficiency)
            languages.add(language)

            println LanguageTexts.ADD_MORE_LANGUGAGE_TEXT
            input = reader.readLine()
            addMore = this.validation.validateAddMore(input)
        }
        return languages
    }

    private List<Skill> addSkills() {
        println SkillTexts.ADD_SKILL_TEXT
        List<Skill> skills = []
        String input = reader.readLine()
        Boolean addMore = this.validation.validateAddMore(input)

        while (addMore) {
            println SkillTexts.TITLE_TEXT
            String title = reader.readLine()
            Proficiency proficiency = this.validation.validateProficiency()

            Skill skill = new Skill(null, title, proficiency)
            skills.add(skill)

            println SkillTexts.ADD_MORE_SKILL_TEXT
            input = reader.readLine()
            addMore = this.validation.validateAddMore(input)
        }
        return skills
    }

    private List<Certificate> addCertificates() {
        println CertificateTexts.ADD_CERTIFICATE_TEXT
        List<Certificate> certificates = []
        String input = reader.readLine()
        Boolean addMore = this.validation.validateAddMore(input)

        while (addMore) {
            println CertificateTexts.TITLE_TEXT
            String title = reader.readLine()
            println CertificateTexts.DURATION_TEXT
            String duration = reader.readLine()

            Certificate certificate = new Certificate(null, title, duration)
            certificates.add(certificate)

            println CertificateTexts.ADD_MORE_CERTIFICATE_TEXT
            input = reader.readLine()
            addMore = this.validation.validateAddMore(input)
        }
        return certificates
    }

    private Candidate populateCandidate() {
        println CandidateTexts.NAME_TEXT
        String name = reader.readLine()
        String email = this.validation.validateEmail()
        println CandidateTexts.CITY_TEXT
        String city = reader.readLine()
        State state = this.validation.validateState()
        println CandidateTexts.COUNTRY_TEXT
        String country = reader.readLine()
        String cep = this.validation.validateCep()
        println CandidateTexts.DESCRIPTION_TEXT
        String description = reader.readLine()
        String cpf = this.validation.validateCpf()
        List<AcademicExperience> academicExperiences = this.addAcademicExperiences()
        List<WorkExperience> workExperiences = this.addWorkExperiences()
        List<Language> languages = this.addLanguages()
        List<Skill> skills = this.addSkills()
        List<Certificate> certificates = this.addCertificates()

        Candidate candidate = new Candidate(null, name, email, city, state, country, cep, description, cpf,
                academicExperiences, workExperiences, languages, skills, certificates)
        return candidate
    }

    void addCandidate() {
        Candidate candidate = this.populateCandidate()
        this.controller.add(candidate)
    }

    void updateCandidate() {
        Integer id = this.validation.validateId()
        Candidate updatedCandidate = this.populateCandidate()
        updatedCandidate.id = id
        this.controller.update(updatedCandidate.id, updatedCandidate)
    }

    void removeCandidate() {
        Integer id = this.validation.validateId()
        this.controller.delete(id)
    }

}
