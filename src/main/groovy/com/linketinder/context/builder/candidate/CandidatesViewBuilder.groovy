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

    IConnection connectionFactory
    IDBService dbService

    ICandidateDAO candidateDAO
    ICandidateValidation candidateValidation
    ICandidateService candidateService

    CandidatesViewBuilder(IConnection connectionFactory, IDBService dbService) {
        this.connectionFactory = connectionFactory
        this.dbService = dbService
    }

    private void generateCandidateDAO() {
        IAcademicExperienceDAO academicExperienceDAO = new AcademicExperienceDAOBuilder()
            .withDBService(dbService)
            .withConnection(connectionFactory)
            .build()

        ICandidateSkillDAO candidateSkillDAO = new CandidateSkillDAOBuilder()
            .withDBService(dbService)
            .withConnection(connectionFactory)
            .build()

        ICertificateDAO certificateDAO = new CertificateDAOBuilder()
            .withConnection(connectionFactory)
            .build()

        ILanguageDAO languageDAO = new LanguageDAOBuilder()
            .withDBService(dbService)
            .withConnection(connectionFactory)
            .build()

        IWorkExperienceDAO workExperienceDAO = new WorkExperienceDAOBuilder()
            .withDBService(dbService)
            .withConnection(connectionFactory)
            .build()

        this.candidateDAO = new CandidateDAOBuilder()
            .withDBService(dbService)
            .withConnection(connectionFactory)
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
        this.candidateService = new CandidateService(candidateDAO)
    }

    ICandidatesView build() {
        this.generateCandidateDAO()
        this.generateCandidateValidation()
        this.generateCandidateService()
        return new CandidatesView(this.candidateService, this.candidateValidation)
    }

}
