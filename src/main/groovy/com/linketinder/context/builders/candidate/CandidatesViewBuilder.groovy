package com.linketinder.context.builders.candidate

import com.linketinder.context.builders.interfaces.ICandidatesViewBuilder
import com.linketinder.controller.CandidateController
import com.linketinder.controller.interfaces.ICandidateController
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
    ICandidateController candidateController
    ICandidateValidation candidateValidation

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

    private void generateCandidateController() {
        this.generateCandidateDAO()
        ICandidateService candidateService = new CandidateService(this.candidateDAO)
        this.candidateController = new CandidateController(candidateService)
    }

    ICandidatesView build() {
        this.generateCandidateValidation()
        this.generateCandidateController()
        return new CandidatesView(this.candidateController, this.candidateValidation)
    }

}
