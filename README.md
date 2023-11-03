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
* Adicionado Regex as validações.
* Adicionado funcionalidade de Curtida e Match.
* Adicionada persistência do Match no BD.
* Transformado projeto em uma REST API utilizando Tomcat e Servlets.

* Utilizado as boas práticas do Clean Code e do SOLID.
  * Refatorado as classes para que os métodos fiquem mais sucintos, sem repetição de código.
  * Feito o desacoplamento das classes utilizando a prática do SOLID.

* Utilizado alguns Design Patterns para melhorar a aplicação.
  * Singleton para manter uma única conexão com o banco de dados durante a execução da aplicação, preservando assim os recursos do sistema.
  * Abstract Factory para desacoplar o código e facilitar a troca de banco de dados caso for necessário.
  * Builder para deixar mais limpa a classe ApplicationContext.
  * MVC para melhorar a divisão de responsabilidades.

## MER
<img src="MER.png">

### Como executar
Para executar a API é necessário executar o servidor do Tomcat.

### REST API com Tomcat e Servlets
Foi feito uma API sem frameworks, utilizando Tomcat 8.5.95 e Javax Servlet.

## Tecnologias
* Groovy
* PostgreSQL
* JUnit
* Mockito
* Typescript
* HTML
* CSS

==================================================
### Feito por: Willian H. de Oliveira
==================================================
