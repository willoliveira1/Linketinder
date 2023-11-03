package com.linketinder.service.factories

import com.linketinder.context.builders.candidate.*
import com.linketinder.dao.candidatedao.interfaces.*
import com.linketinder.database.ConnectionFactory
import com.linketinder.database.DBService
import com.linketinder.database.interfaces.*
import com.linketinder.service.CandidateService
import com.linketinder.service.interfaces.ICandidateService

class CandidateServiceFactory {

    static ICandidateService createCandidateService() {
        IConnection connection = ConnectionFactory.createConnection("POSTGRESQL")
        IDBService dbService = new DBService(connection)

        IAcademicExperienceDAO academicExperienceDAO = new AcademicExperienceDAOBuilder()
                .withDBService(dbService)
                .withConnection(connection)
                .build()

        ICandidateSkillDAO candidateSkillDAO = new CandidateSkillDAOBuilder()
                .withDBService(dbService)
                .withConnection(connection)
                .build()

        ICertificateDAO certificateDAO = new CertificateDAOBuilder()
                .withConnection(connection)
                .build()

        ILanguageDAO languageDAO = new LanguageDAOBuilder()
                .withDBService(dbService)
                .withConnection(connection)
                .build()

        IWorkExperienceDAO workExperienceDAO = new WorkExperienceDAOBuilder()
                .withDBService(dbService)
                .withConnection(connection)
                .build()

        ICandidateDAO candidateDAO = new CandidateDAOBuilder()
                .withDBService(dbService)
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
