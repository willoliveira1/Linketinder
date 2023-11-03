# Linketinder

Projeto para desenvolver uma aplicação chamada Linketinder.

## Features
* Criado aplicação em Groovy.
* CRUD de Candidatos, Empresas e Vagas.
* Criado frontend em Typescript, HTML e CSS.
* Feito a modelagem do Banco de Dados, gerado no dbDesigner (https://www.dbdesigner.net/).
* Criado Banco de Dados PostgreSQL, estrutura esta no create-and-populate-database.sql.
* Adicionado tabelas ao BD referentes a funcionalidade de Match, a lógica aplicada é dividida em etapas:
   * Candidato ve a lista de vagas e curte as que lhe despertaram o interesse.
   * A empresa acessa a lista de candidatos e curte os candidatos que lhe interessam.
   * Nesse momento o match é concretizado, aqui a empresa consegue visualizar todas as informações do candidato e podem seguir para as próximas etapas do processo seletivo.
* Refatorado utilizando as práticas do Clean Code.
* Adicionado Regex as validações.
* Transformado projeto em uma REST API utilizando Tomcat e Servlets.

## MER
<img src="MER.png">

### Como executar
Para executar a API é necessário executar o servidor do Tomcat.

### REST API com Tomcat e Servlets
Foi feito uma API sem frameworks, utilizando Tomcat 8.5.95 e Javax Servlet.

==================================================
### Feito por: Willian H. de Oliveira
==================================================
