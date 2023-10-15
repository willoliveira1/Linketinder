package com.linketinder.view

import com.linketinder.util.viewtexts.ApplicationTexts
import com.linketinder.view.interfaces.IApplicationView
import com.linketinder.view.interfaces.ICandidatesView
import com.linketinder.view.interfaces.ICompaniesView
import com.linketinder.view.interfaces.IJobVacanciesView
import com.linketinder.view.interfaces.IMatchesView

class ApplicationView implements IApplicationView {

    ICandidatesView candidatesView
    ICompaniesView companiesView
    IJobVacanciesView jobVacanciesView
    IMatchesView matchesView
    Readable reader = System.in.newReader()

    ApplicationView(ICandidatesView candidatesView, ICompaniesView companiesView, IJobVacanciesView jobVacanciesView,
                    IMatchesView matchesView) {
        this.candidatesView = candidatesView
        this.companiesView = companiesView
        this.jobVacanciesView = jobVacanciesView
        this.matchesView = matchesView
    }

    void applicationGenerate() {
        initialScreen()
    }

    private Integer selectOption() {
        Integer selectedOption
        try {
            selectedOption = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            invalidOption()
        }
        return selectedOption
    }

    private void invalidOption() {
        println ApplicationTexts.INVALID_TEXT
        Thread.sleep(1500)
        initialScreen()
    }

    private void backToInitialScreen() {
        println ApplicationTexts.PRESS_TEXT
        reader.readLine()
        clearConsole()
        initialScreen()
    }

    private void clearConsole() {
        for (int i = 0; i < 20; ++i) System.out.println()
    }

    private void initialScreen() {
        clearConsole()
        println ApplicationTexts.APPLICATION
        Integer selectedOption = selectOption()

        switch (selectedOption) {
            case 1:
                clearConsole()
                candidatesScreen()
                break
            case 2:
                clearConsole()
                companiesScreen()
                break
            case 3:
                clearConsole()
                jobVacanciesScreen()
                break
            case 4:
                clearConsole()
                matchesScreen()
                break
            case 0:
                System.exit(0)
                break
            default:
                invalidOption()
        }
        backToInitialScreen()
    }

    private void candidatesScreen() {
        println ApplicationTexts.CANDIDATES
        Integer selectedOption = selectOption()

        switch (selectedOption) {
            case 1:
                clearConsole()
                candidatesView.getAllCandidates()
                break
            case 2:
                clearConsole()
                candidatesView.getCandidateById()
                break
            case 3:
                clearConsole()
                candidatesView.addCandidate()
                break
            case 4:
                clearConsole()
                candidatesView.removeCandidate()
                break
            case 5:
                clearConsole()
                candidatesView.updateCandidate()
                break
            case 0:
                initialScreen()
                break
            default:
                invalidOption()
        }
        backToInitialScreen()
    }

    private void companiesScreen() {
        println ApplicationTexts.COMPANIES
        Integer selectedOption = selectOption()

        switch (selectedOption) {
            case 1:
                clearConsole()
                companiesView.getAllCompanies()
                break
            case 2:
                clearConsole()
                companiesView.getCompanyById()
                break
            case 3:
                clearConsole()
                companiesView.addCompany()
                break
            case 4:
                clearConsole()
                companiesView.removeCompany()
                break
            case 5:
                clearConsole()
                companiesView.updateCompany()
                break
            case 0:
                initialScreen()
                break
            default:
                invalidOption()
        }
        backToInitialScreen()
    }

    private void jobVacanciesScreen() {
        println ApplicationTexts.JOB_VACANCIES
        Integer selectedOption = selectOption()
        switch (selectedOption) {
            case 1:
                clearConsole()
                jobVacanciesView.getAllJobVacancies()
                break
            case 2:
                clearConsole()
                jobVacanciesView.getAllJobVacanciesByCompanyId()
                break
            case 3:
                clearConsole()
                jobVacanciesView.getJobVacancyById()
                break
            case 4:
                clearConsole()
                jobVacanciesView.addJobVacancy()
                break
            case 5:
                clearConsole()
                jobVacanciesView.removeJobVacancy()
                break
            case 6:
                clearConsole()
                jobVacanciesView.updateJobVacancy()
                break
            case 0:
                initialScreen()
                break
            default:
                invalidOption()
        }
        backToInitialScreen()
    }

    void matchesScreen() {
        println ApplicationTexts.MATCHES
        Integer selectedOption = selectOption()

        switch (selectedOption) {
            case 1:
                clearConsole()
                this.matchesCandidateScreen()
                break
            case 2:
                clearConsole()
                this.matchesCompanyScreen()
                break
            case 0:
                initialScreen()
                break
            default:
                invalidOption()
        }
        backToInitialScreen()
    }

    void matchesCandidateScreen() {
        println ApplicationTexts.CANDIDATE_MATCHES
        Integer selectedOption = selectOption()

        switch (selectedOption) {
            case 1:
                clearConsole()
                matchesView.getAllMatches()
                break
            case 2:
                clearConsole()
                matchesView.getAllMatchesByCandidateId()
                break
            case 3:
                clearConsole()
                matchesView.likeJobVacancy()
                break
            case 0:
                initialScreen()
                break
            default:
                invalidOption()
        }
        backToInitialScreen()
    }

    void matchesCompanyScreen() {
        println ApplicationTexts.COMPANY_MATCHES
        Integer selectedOption = selectOption()

        switch (selectedOption) {
            case 1:
                clearConsole()
                matchesView.getAllMatches()
                break
            case 2:
                clearConsole()
                matchesView.getAllMatchesByCompanyId()
                break
            case 3:
                clearConsole()
                matchesView.likeCandidate()
                break
            case 0:
                initialScreen()
                break
            default:
                invalidOption()
        }
        backToInitialScreen()
    }

}
