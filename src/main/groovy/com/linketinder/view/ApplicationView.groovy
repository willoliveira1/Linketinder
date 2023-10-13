package com.linketinder.view

class ApplicationView {

    CandidatesView candidatesView = new CandidatesView()
    CompaniesView companiesView = new CompaniesView()
    JobVacanciesView jobVacanciesView = new JobVacanciesView()
    MatchesView matchesView = new MatchesView()
    BufferedReader reader = System.in.newReader()

    void applicationGenerate() {
        initialScreen()
    }

    private void applicationText() {
        String text = """##############################
##                          ##
## Aplicação do Linketinder ##
##                          ##
##############################
Digite a opção desejada:
1) Candidatos
2) Empresas
3) Vagas
4) Matches
0) Sair
"""
        println text
    }

    private void candidatesText() {
        String text = """##############################
##                          ##
##        Candidatos        ##
##                          ##
##############################
Digite a opção desejada:
1) Listar Candidatos
2) Listar Candidato Especifico
3) Adicionar Novo Candidato
4) Remover Candidato 
5) Atualizar Candidato
0) Voltar
"""
        println text
    }

    private void companiesText() {
        String text = """##############################
##                          ##
##         Empresas         ##
##                          ##
##############################
Digite a opção desejada:
1) Listar Empresas
2) Listar Empresa Especifica
3) Adicionar Nova Empresa
4) Remover Empresa
5) Atualizar Empresa
0) Voltar
"""
        println text
    }

    private void jobVacanciesText() {
        String text = """##############################
##                          ##
##           Vagas          ##
##                          ##
##############################
Digite a opção desejada:
1) Listar Vagas
2) Listar Vagas de Empresa Específica
3) Listar Vaga Especifica
4) Adicionar Nova Vaga
5) Remover Vaga
6) Atualizar Vaga
0) Voltar
"""
        println text
    }

    private void matchesText() {
        String text = """##############################
##                          ##
##          Matches         ##
##                          ##
##############################
Digite seu tipo de usuário:
1) Candidato
2) Empresa
0) Voltar
"""
        println text
    }

    private void matchesCandidateText() {
        String text = """##############################
##                          ##
##          Matches         ##
##                          ##
##############################
Digite a opção desejada:
1) Listar Todos os Matches
2) Listar Todos os Matches de um Candidato Específico
3) Curtir Vaga
0) Voltar
"""
        println text
    }

    private void matchesCompanyText() {
        String text = """##############################
##                          ##
##          Matches         ##
##                          ##
##############################
Digite a opção desejada:
1) Listar Todos os Matches
2) Listar Todos os Matches de uma Empresa Específica
3) Curtir Candidato
0) Voltar
"""
        println text
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
        println "Opção inválida."
        Thread.sleep(1500)
        initialScreen()
    }

    private void backToInitialScreen() {
        println "Aperte enter para continuar..."
        reader.readLine()
        clearConsole()
        initialScreen()
    }

    private void clearConsole() {
        for (int i = 0; i < 20; ++i) System.out.println()
    }

    private void initialScreen() {
        clearConsole()
        applicationText()
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
        candidatesText()
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
        companiesText()
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
        jobVacanciesText()
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
        matchesText()
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
            default:
                invalidOption()
        }
        backToInitialScreen()
    }

    void matchesCandidateScreen() {
        matchesCandidateText()
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
        matchesCompanyText()
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
