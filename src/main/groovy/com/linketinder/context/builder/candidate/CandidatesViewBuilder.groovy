package com.linketinder.context.builder.candidate

import com.linketinder.context.builder.interfaces.ICandidatesViewBuilder
import com.linketinder.dao.candidatedao.interfaces.*
import com.linketinder.database.interfaces.*
import com.linketinder.service.CandidateService
import com.linketinder.service.interfaces.ICandidateService
import com.linketinder.validation.CandidateValidation
import com.linketinder.validation.interfaces.ICandidateValidation
import com.linketinder.view.CandidatesView
import com.linketinder.view.interfaces.ICandidatesView

class CandidatesViewBuilder implements ICandidatesViewBuilder {

    IConnection connection
    IDBService dbService

    ICandidateDAO candidateDAO
    ICandidateValidation candidateValidation
    ICandidateService candidateService

    CandidatesViewBuilder(IConnection connection, IDBService dbService) {
        this.connection = connection
        this.dbService = dbService
    }

    private void generateCandidateDAO() {
        IAcademicExperienceDAO academicExperienceDAO = new AcademicExperienceDAOBuilder()
            .withDBService(this.dbService)
            .withConnection(this.connection)
            .build()

        ICandidateSkillDAO candidateSkillDAO = new CandidateSkillDAOBuilder()
            .withDBService(this.dbService)
            .withConnection(this.connection)
            .build()

        ICertificateDAO certificateDAO = new CertificateDAOBuilder()
            .withConnection(this.connection)
            .build()

        ILanguageDAO languageDAO = new LanguageDAOBuilder()
            .withDBService(this.dbService)
            .withConnection(this.connection)
            .build()

        IWorkExperienceDAO workExperienceDAO = new WorkExperienceDAOBuilder()
            .withDBService(this.dbService)
            .withConnection(this.connection)
            .build()

        this.candidateDAO = new CandidateDAOBuilder()
            .withDBService(this.dbService)
            .withConnection(this.connection)
            .withAcademicExperienceDAO(academicExperienceDAO)
            .withCandidateSkillDAO(candidateSkillDAO)
            .withCertificateDAO(certificateDAO)
            .withLanguageDAO(languageDAO)
            .withWorkExperienceDAO(workExperienceDAO)
            .build()
    }

    private void generateCandidateValidation() {
        this.candidateValidation = new CandidateValidation()
    }

    private void generateCandidateService() {
        this.candidateService = new CandidateService(this.candidateDAO)
    }

    ICandidatesView build() {
        this.generateCandidateDAO()
        this.generateCandidateValidation()
        this.generateCandidateService()
        return new CandidatesView(this.candidateService, this.candidateValidation)
    }

}
