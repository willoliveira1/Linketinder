# Linketinder

Projeto para desenvolver uma aplicação chamada Linketinder.

## Features
* Criado aplicação em Groovy.
* CRUD de Candidatos e Empresas.
* Criado persistência em .json para manter os Candidatos e Empresas salvas e facilitar o uso.
* Criado frontend em Typescript, HTML e CSS.
* Feito a modelagem do Banco de Dados, gerado no dbDesigner (https://www.dbdesigner.net/).
* Criado Banco de Dados PostgreSQL, estrutura esta no create-and-populate-database.sql.
* Adicionado tabelas ao BD referentes a funcionalidade de Match, a lógica aplicada é dividida em etapas:
   * Candidato ve a lista de vagas e curte as que lhe despertaram o interesse.
   * A empresa acessa a lista de candidatos e curte os candidatos que lhe interessam.
   * Nesse momento o match é concretizado, aqui a empresa consegue visualizar todas as informações do candidato e podem seguir para as próximas etapas do processo seletivo.
* Refatorado utilizando as práticas do Clean Code.
* Adicionado Regex as validações.

## MER
<img src="MER.png">

### Como executar
O arquivo que tem o método main para a execução do projeto está em:

* Pasta: src/main/groovy/com/linketinder
* Arquivo: Main.groovy

==================================================
### Feito por: Willian Henrique de Oliveira
==================================================
