package com.linketinder.service.factories

import com.linketinder.builders.candidate.*
import com.linketinder.dao.candidatedao.interfaces.*
import com.linketinder.database.*
import com.linketinder.database.interfaces.*
import com.linketinder.service.CandidateService
import com.linketinder.service.interfaces.ICandidateService
import com.linketinder.validation.CandidateValidation
import com.linketinder.validation.interfaces.ICandidateValidation

class CandidateServiceFactory {

    static ICandidateService createCandidateService() {
        IConnection connection = ConnectionFactory.createConnection("POSTGRESQL")

        ICandidateValidation validation = new CandidateValidation()

        IAcademicExperienceDAO academicExperienceDAO = new AcademicExperienceDAOBuilder()
                .withConnection(connection)
                .build()

        ICandidateSkillDAO candidateSkillDAO = new CandidateSkillDAOBuilder()
                .withConnection(connection)
                .build()

        ICertificateDAO certificateDAO = new CertificateDAOBuilder()
                .withConnection(connection)
                .build()

        ILanguageDAO languageDAO = new LanguageDAOBuilder()
                .withConnection(connection)
                .build()

        IWorkExperienceDAO workExperienceDAO = new WorkExperienceDAOBuilder()
                .withConnection(connection)
                .build()

        ICandidateDAO candidateDAO = new CandidateDAOBuilder()
                .withConnection(connection)
                .withAcademicExperienceDAO(academicExperienceDAO)
                .withCandidateSkillDAO(candidateSkillDAO)
                .withCertificateDAO(certificateDAO)
                .withLanguageDAO(languageDAO)
                .withWorkExperienceDAO(workExperienceDAO)
                .build()

        return new CandidateService(candidateDAO, validation)
    }

}
