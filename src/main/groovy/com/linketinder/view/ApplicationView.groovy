package com.linketinder.view

class ApplicationView {

    CandidatesView candidatesView = new CandidatesView()
    CompaniesView companiesView = new CompaniesView()

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

    private void initialScreen() {
        clearConsole()
        applicationText()

        Integer selectedOption
        try {
            selectedOption = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            invalidOption()
        }

        switch (selectedOption) {
            case 1:
                clearConsole()
                candidatesScreen()
                break
            case 2:
                clearConsole()
                companiesScreen()
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

        Integer selectedOption
        try {
            selectedOption = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            invalidOption()
        }

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
        Integer selectedOption
        try {
            selectedOption = reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            invalidOption()
        }

        switch (selectedOption) {
            case 1:
                clearConsole()
                companiesView.getAllCompanies()
                backToInitialScreen()
                break
            case 2:
                clearConsole()
                companiesView.getCompanyById()
                backToInitialScreen()
                break
            case 3:
                clearConsole()
                companiesView.addCompany()
                backToInitialScreen()
                break
            case 4:
                clearConsole()
                companiesView.removeCompany()
                backToInitialScreen()
                break
            case 5:
                clearConsole()
                companiesView.updateCompany()
                backToInitialScreen()
                break
            case 0:
                initialScreen()
                break
            default:
                invalidOption()
        }
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

}
