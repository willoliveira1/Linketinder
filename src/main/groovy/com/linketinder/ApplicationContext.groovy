package com.linketinder

import com.linketinder.dao.candidatedao.AcademicExperienceDAO
import com.linketinder.dao.candidatedao.CandidateDAO
import com.linketinder.dao.candidatedao.CandidateSkillDAO
import com.linketinder.dao.candidatedao.CertificateDAO
import com.linketinder.dao.candidatedao.LanguageDAO
import com.linketinder.dao.candidatedao.WorkExperienceDAO
import com.linketinder.dao.candidatedao.interfaces.IAcademicExperienceDAO
import com.linketinder.dao.candidatedao.interfaces.ICandidateDAO
import com.linketinder.dao.candidatedao.interfaces.ICandidateSkillDAO
import com.linketinder.dao.candidatedao.interfaces.ICertificateDAO
import com.linketinder.dao.candidatedao.interfaces.ILanguageDAO
import com.linketinder.dao.candidatedao.interfaces.IWorkExperienceDAO
import com.linketinder.dao.companydao.BenefitDAO
import com.linketinder.dao.companydao.CompanyDAO
import com.linketinder.dao.companydao.RequiredSkillDAO
import com.linketinder.dao.companydao.interfaces.IBenefitDAO
import com.linketinder.dao.companydao.interfaces.ICompanyDAO
import com.linketinder.dao.companydao.interfaces.IJobVacancyDAO
import com.linketinder.dao.companydao.JobVacancyDAO
import com.linketinder.dao.companydao.interfaces.IRequiredSkillDAO
import com.linketinder.dao.matchdao.CandidateMatchDAO
import com.linketinder.dao.matchdao.CompanyMatchDAO
import com.linketinder.dao.matchdao.interfaces.ICandidateMatchDAO
import com.linketinder.dao.matchdao.interfaces.ICompanyMatchDAO
import com.linketinder.dao.matchdao.interfaces.IMatchDAO
import com.linketinder.dao.matchdao.MatchDAO
import com.linketinder.database.DBService
import com.linketinder.database.DatabaseConnection
import com.linketinder.database.interfaces.IDBService
import com.linketinder.database.interfaces.IDatabaseConnection
import com.linketinder.service.CandidateService
import com.linketinder.service.CompanyService
import com.linketinder.service.interfaces.ICandidateService
import com.linketinder.service.interfaces.ICompanyService
import com.linketinder.service.interfaces.IJobVacancyService
import com.linketinder.service.interfaces.IMatchService
import com.linketinder.service.JobVacancyService
import com.linketinder.service.MatchService
import com.linketinder.validation.CandidateValidation
import com.linketinder.validation.CompanyValidation
import com.linketinder.validation.MatchValidation
import com.linketinder.validation.interfaces.ICandidateValidation
import com.linketinder.validation.interfaces.ICompanyValidation
import com.linketinder.validation.interfaces.IJobVacancyValidation
import com.linketinder.validation.JobVacancyValidation
import com.linketinder.validation.interfaces.IMatchValidation
import com.linketinder.view.ApplicationView
import com.linketinder.view.CandidatesView
import com.linketinder.view.CompaniesView
import com.linketinder.view.interfaces.IApplicationView
import com.linketinder.view.interfaces.ICandidatesView
import com.linketinder.view.interfaces.ICompaniesView
import com.linketinder.view.interfaces.IJobVacanciesView
import com.linketinder.view.interfaces.IMatchesView
import com.linketinder.view.JobVacanciesView
import com.linketinder.view.MatchesView

class ApplicationContext {

    IDatabaseConnection databaseConnection = new DatabaseConnection()
    IDBService dbService = new DBService(databaseConnection)

    IAcademicExperienceDAO academicExperienceDAO = new AcademicExperienceDAO(dbService, databaseConnection)
    ICandidateSkillDAO candidateSkillDAO = new CandidateSkillDAO(dbService, databaseConnection)
    ICertificateDAO certificateDAO = new CertificateDAO(databaseConnection)
    ILanguageDAO languageDAO = new LanguageDAO(dbService, databaseConnection)
    IWorkExperienceDAO workExperienceDAO = new WorkExperienceDAO(dbService, databaseConnection)
    IBenefitDAO benefitDAO = new BenefitDAO(dbService, databaseConnection)
    IRequiredSkillDAO requiredSkillDAO = new RequiredSkillDAO(dbService, databaseConnection)

    IJobVacancyDAO jobVacancyDAO = new JobVacancyDAO(dbService, databaseConnection, requiredSkillDAO)
    IJobVacancyValidation jobVacancyValidation = new JobVacancyValidation()
    IJobVacancyService jobVacancyService = new JobVacancyService(jobVacancyDAO)
    IJobVacanciesView jobVacanciesView = new JobVacanciesView(jobVacancyService, jobVacancyValidation)

    ICandidateDAO candidateDAO = new CandidateDAO(dbService, databaseConnection, certificateDAO, languageDAO,
            candidateSkillDAO, academicExperienceDAO, workExperienceDAO)
    ICandidateValidation candidateValidation = new CandidateValidation()
    ICandidateService candidateService = new CandidateService(candidateDAO)
    ICandidatesView candidatesView = new CandidatesView(candidateService, candidateValidation)

    ICompanyDAO companyDAO = new CompanyDAO(dbService, databaseConnection, benefitDAO, jobVacancyDAO)
    ICompanyValidation companyValidation = new CompanyValidation()
    ICompanyService companyService = new CompanyService(companyDAO)
    ICompaniesView companiesView = new CompaniesView(companyService, companyValidation)

    IMatchDAO matchDAO = new MatchDAO(databaseConnection)
    IMatchValidation matchValidation = new MatchValidation()
    ICandidateMatchDAO candidateMatchDAO = new CandidateMatchDAO(matchDAO, databaseConnection)
    ICompanyMatchDAO companyMatchDAO = new CompanyMatchDAO(matchDAO, databaseConnection)
    IMatchService matchService = new MatchService(matchDAO, candidateMatchDAO, companyMatchDAO)
    IMatchesView matchesView = new MatchesView(candidatesView, jobVacanciesView, matchService, matchValidation)

    IApplicationView application = new ApplicationView(
        candidatesView,
        companiesView,
        jobVacanciesView,
        matchesView
    )

    void generate() {
        application.applicationGenerate()
    }

}
