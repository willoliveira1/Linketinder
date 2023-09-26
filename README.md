# Linketinder

Projeto para desenvolver uma aplicação chamada Linketinder.

## Features
* Criado aplicação em Groovy.
* CRUD de Candidatos e Empresas.
* Criado persistência em .json para manter os Candidatos e Empresas salvas e facilitar o uso.
* Criado frontend em Typescript, HTML e CSS.
* Feito a modelagem do Banco de Dados, gerado no dbDesigner (https://www.dbdesigner.net/).
* Criado Banco de Dados PostgreSQL.
* Adicionado tabelas ao BD referentes a funcionalidade de Match (
  * A lógica aplicada é dividida em etapas:
    * Candidato ve a lista de vagas e curte as que lhe despertaram o interesse.
    * A empresa acessa uma de suas vagas e ve parcialmente as informações dos candidatos que curtiram a vaga.
    * A empresa curte os candidatos que ela achou aderente a vaga.
    * Nesse momento o match é concretizado, aqui a empresa consegue visualizar todas as informações do candidato e podem seguir para as próximas etapas do processo seletivo.

## MER
<img src="MER.png">

### Como executar
O arquivo que tem o método main para a execução do projeto está em:

* Pasta: src/main/groovy/com/linketinder
* Arquivo: Main.groovy

==================================================
### Feito por: Willian Henrique de Oliveira
==================================================
