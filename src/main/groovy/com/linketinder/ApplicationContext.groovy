package com.linketinder

import com.linketinder.dao.candidatedao.CandidateDAO
import com.linketinder.dao.candidatedao.interfaces.ICandidateDAO
import com.linketinder.dao.companydao.CompanyDAO
import com.linketinder.dao.companydao.interfaces.ICompanyDAO
import com.linketinder.dao.companydao.interfaces.IJobVacancyDAO
import com.linketinder.dao.companydao.JobVacancyDAO
import com.linketinder.dao.matchdao.CandidateMatchDAO
import com.linketinder.dao.matchdao.CompanyMatchDAO
import com.linketinder.dao.matchdao.interfaces.ICandidateMatchDAO
import com.linketinder.dao.matchdao.interfaces.ICompanyMatchDAO
import com.linketinder.dao.matchdao.interfaces.IMatchDAO
import com.linketinder.dao.matchdao.MatchDAO
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
import com.linketinder.validation.interfaces.ICandidateValidation
import com.linketinder.validation.interfaces.ICompanyValidation
import com.linketinder.validation.interfaces.IJobVacancyValidation
import com.linketinder.validation.JobVacancyValidation
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

    ICandidateValidation candidateValidation = new CandidateValidation()
    ICandidateDAO candidateDAO = new CandidateDAO()
    ICandidateService candidateService = new CandidateService(candidateDAO)
    ICandidatesView candidatesView = new CandidatesView(candidateService, candidateValidation)

    ICompanyValidation companyValidation = new CompanyValidation()
    ICompanyDAO companyDAO = new CompanyDAO()
    ICompanyService companyService = new CompanyService(companyDAO)
    ICompaniesView companiesView = new CompaniesView(companyService, companyValidation)

    IJobVacancyValidation jobVacancyValidation = new JobVacancyValidation()
    IJobVacancyDAO jobVacancyDAO = new JobVacancyDAO()
    IJobVacancyService jobVacancyService = new JobVacancyService(jobVacancyDAO)
    IJobVacanciesView jobVacanciesView = new JobVacanciesView(jobVacancyService, jobVacancyValidation)

    IMatchDAO matchDAO = new MatchDAO()
    ICandidateMatchDAO candidateMatchDAO = new CandidateMatchDAO(matchDAO)
    ICompanyMatchDAO companyMatchDAO = new CompanyMatchDAO(matchDAO)
    IMatchService matchService = new MatchService(matchDAO, candidateMatchDAO, companyMatchDAO)
    IMatchesView matchesView = new MatchesView(candidatesView, jobVacanciesView, matchService)

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
