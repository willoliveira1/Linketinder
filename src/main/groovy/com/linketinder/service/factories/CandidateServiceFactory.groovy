package com.linketinder.service.factories

import com.linketinder.builders.candidate.AcademicExperienceDAOBuilder
import com.linketinder.builders.candidate.CandidateDAOBuilder
import com.linketinder.builders.candidate.CandidateSkillDAOBuilder
import com.linketinder.builders.candidate.CertificateDAOBuilder
import com.linketinder.builders.candidate.LanguageDAOBuilder
import com.linketinder.builders.candidate.WorkExperienceDAOBuilder
import com.linketinder.dao.candidatedao.interfaces.*
import com.linketinder.database.*
import com.linketinder.database.interfaces.*
import com.linketinder.service.CandidateService
import com.linketinder.service.interfaces.ICandidateService

class CandidateServiceFactory {

    static ICandidateService createCandidateService() {
        IConnection connection = ConnectionFactory.createConnection("POSTGRESQL")

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

        return new CandidateService(candidateDAO)
    }

}
