-- Database Creation
CREATE DATABASE linketinder;



-- Tables Creation
CREATE TABLE states (
  id SERIAL PRIMARY KEY,
  name VARCHAR(25) NOT NULL,
  acronym VARCHAR(2) NOT NULL
);

CREATE TABLE contract_types (
  id SERIAL PRIMARY KEY,
  title VARCHAR(20) NOT NULL
);

CREATE TABLE location_types (
  id SERIAL PRIMARY KEY,
  title VARCHAR(20) NOT NULL
);

CREATE TABLE proficiences (
  id SERIAL PRIMARY KEY,
  title VARCHAR(20) NOT NULL
);

CREATE TABLE skills (
  id SERIAL PRIMARY KEY,
  title VARCHAR(50) NOT NULL
);

CREATE TABLE certificates (
  id SERIAL PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  duration VARCHAR(50) NOT NULL
);

CREATE TABLE benefits (
  id SERIAL PRIMARY KEY,
  title VARCHAR(50) NOT NULL
);

CREATE TABLE course_status (
  id SERIAL PRIMARY KEY,
  title VARCHAR(20) NOT NULL
);

CREATE TABLE candidates (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  city VARCHAR(50) NOT NULL,
  state_id INTEGER NOT NULL REFERENCES states(id),
  country VARCHAR(50) NOT NULL,
  cep VARCHAR(8) NOT NULL,
  description VARCHAR(1000) NOT NULL,
  cpf VARCHAR(11) NOT NULL
);

CREATE TABLE companies (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  city VARCHAR(50) NOT NULL,
  state_id INTEGER NOT NULL REFERENCES states(id),
  country VARCHAR(50) NOT NULL,
  cep VARCHAR(8) NOT NULL,
  description VARCHAR(1000) NOT NULL,
  cnpj VARCHAR(14) NOT NULL
);

CREATE TABLE languages (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE academic_experiences (
  id SERIAL PRIMARY KEY,
  educational_institution VARCHAR(50) NOT NULL,
  degree_type VARCHAR(50) NOT NULL,
  field_of_study VARCHAR(50) NOT NULL,
  course_status_id INTEGER NOT NULL REFERENCES course_status(id)
);

CREATE TABLE work_experiences (
  id SERIAL PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  company_name VARCHAR(50) NOT NULL,
  city VARCHAR(50) NOT NULL,
  currently_work BOOLEAN NOT NULL,
  description VARCHAR(1000) NOT NULL,
  state_id INTEGER NOT NULL REFERENCES states(id),
  contract_type_id INTEGER NOT NULL REFERENCES contract_types(id),
  location_id INTEGER NOT NULL REFERENCES location_types(id)
);

CREATE TABLE job_vacancies (
  id SERIAL PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  description VARCHAR(2000) NOT NULL,
  salary FLOAT NOT NULL,
  contract_type_id INTEGER NOT NULL REFERENCES contract_types(id),
  location_type_id INTEGER NOT NULL REFERENCES location_types(id)
);

CREATE TABLE candidate_academic_experiences (
  id SERIAL PRIMARY KEY,
  candidate_id INTEGER NOT NULL REFERENCES candidates(id),
  academic_experience_id INTEGER NOT NULL REFERENCES academic_experiences(id)
);

CREATE TABLE candidate_languages (
  id SERIAL PRIMARY KEY,
  candidate_id INTEGER NOT NULL REFERENCES candidates(id),
  language_id INTEGER NOT NULL REFERENCES languages(id),
  proficiency_id INTEGER NOT NULL REFERENCES proficiences(id)
);

CREATE TABLE candidate_work_experiences (
  id SERIAL PRIMARY KEY,
  candidate_id INTEGER NOT NULL REFERENCES candidates(id),
  work_experience_id INTEGER NOT NULL REFERENCES work_experiences(id)
);

CREATE TABLE candidate_skills (
  id SERIAL PRIMARY KEY,
  candidate_id INTEGER NOT NULL REFERENCES candidates(id),
  skill_id INTEGER NOT NULL REFERENCES skills(id),
  proficiency_id INTEGER NOT NULL REFERENCES proficiences(id)
);

CREATE TABLE candidate_certificates (
  id SERIAL PRIMARY KEY,
  candidate_id INTEGER NOT NULL REFERENCES candidates(id),
  certificate_id INTEGER NOT NULL REFERENCES certificates(id)
);

CREATE TABLE company_job_vacancies (
  id SERIAL PRIMARY KEY,
  company_id INTEGER NOT NULL REFERENCES companies(id),
  job_vacancy_id INTEGER NOT NULL REFERENCES job_vacancies(id)
);

CREATE TABLE company_benefits (
  id SERIAL PRIMARY KEY,
  company_id INTEGER NOT NULL REFERENCES companies(id),
  benefit_id INTEGER NOT NULL REFERENCES benefits(id)
);

CREATE TABLE job_vacancy_skills (
  id SERIAL PRIMARY KEY,
  job_vacancy_id INTEGER NOT NULL REFERENCES job_vacancies(id),
  skill_id INTEGER NOT NULL REFERENCES skills(id)
);

CREATE TABLE candidate_matches (
    id SERIAL PRIMARY KEY,
    candidate_id INT REFERENCES candidates(id),
    company_job_vacancy_id INT REFERENCES company_job_vacancies(id)
);

CREATE TABLE company_matches (
    id SERIAL PRIMARY KEY,
    company_job_vacancy_id INT REFERENCES company_job_vacancies(id),
    candidate_match_id INT REFERENCES candidate_matches(id)
);



-- Data Inserts
INSERT INTO states (name, acronym) VALUES
('Acre', 'AC'),
('Alagoas', 'AL'),
('Amazonas', 'AM'),
('Amapá', 'AP'),
('Bahia', 'BA'),
('Ceará', 'CE'),
('Distrito Federal', 'DF'),
('Espírito Santo', 'ES'),
('Goiás', 'GO'),
('Maranhão', 'MA'),
('Minas Gerais', 'MG'),
('Mato Grosso', 'MT'),
('Mato Grosso do Sul', 'MS'),
('Pará', 'PA'),
('Paraiba', 'PB'),
('Pernambuco', 'PE'),
('Piauí', 'PI'),
('Paraná', 'PR'),
('Rio de Janeiro', 'RJ'),
('Rio Grande do Norte', 'RN'),
('Rondônia', 'RO'),
('Roraima', 'RR'),
('Rio Grande do Sul', 'RS'),
('Santa Catarina', 'SC'),
('Sergipe', 'SE'),
('São Paulo', 'SP'),
('Tocantins', 'TO');

INSERT INTO contract_types (title) VALUES
('CLT'),
('PJ'),
('Temporário'),
('Estágio'),
('Aprendiz');

INSERT INTO location_types (title) VALUES
('Presencial'),
('Híbrido'),
('Remoto');

INSERT INTO proficiences (title) VALUES
('Básico'),
('Intermediário'),
('Avançado');

INSERT INTO skills (title) VALUES
('Java'),
('Groovy'),
('SQL Server'),
('PostgreSQL'),
('Rust'),
('C++'),
('Kotlin'),
('Angular'),
('Typescript'),
('Python'),
('Javascript'),
('C#');

INSERT INTO certificates (title, duration) VALUES
('Curso Certificado 1', '1 mês'),
('Curso Certificado 2', '2 anos'),
('Curso Certificado 3', '3 meses'),
('Curso Certificado 4', '1 mês'),
('Curso Certificado A', '3 semanas'),
('Curso Certificado B', '1 ano'),
('Curso Certificado C', '100 horas'),
('Curso Certificado D', '10 horas'),
('Curso Certificado E', '1 hora');

INSERT INTO benefits (title) VALUES
('Vale-Transporte'),
('Vale-Refeição'),
('Vale-Alimentação'),
('Refeição no local'),
('Plano de Saúde'),
('Plano Odontológico'),
('Gympass'),
('Seguro de Vida'),
('Participação nos Lucros');

INSERT INTO course_status (title) VALUES
('Cursando'),
('Concluído'),
('Trancado');

INSERT INTO candidates (name, email, city, state_id, country, cep, description, cpf) VALUES
('Candidato 1', 'candidato1@gmail.com', 'Araraquara', 26, 'Brasil', '14800000', 'Descrição 1', '12312312334'),
('Candidato 2', 'candidato2@hotmail.com', 'São Carlos', 26, 'Brasil', '13560000', 'Descrição 2', '34534534567'),
('Candidato 3', 'candi@bol.com.br', 'Uberlândia', 11, 'Brasil', '38400000', 'Descrição 3', '67867845689'),
('Candidato 4', 'candidato4@gmail.com', 'São Paulo', 26, 'Brasil', '01153000', 'Descrição 4', '12378945634'),
('Candidato 5', 'candidato5@mail.com', 'Araxá', 11, 'Brasil', '38180000', 'Descrição 5', '45623467898');

INSERT INTO companies (name, email, city, state_id, country, cep, description, cnpj) VALUES
('Empresa 1', 'empresa1@gmail.com', 'Ribeirão Preto', 26, 'Brasil', '14010000', 'Descrição 1', '12345678945634'),
('Empresa 2', 'empresa2@gmail.com', 'Araraquara', 26, 'Brasil', '14800000', 'Descrição 2', '45645645667845'),
('Empresa 3', 'empresa3@gmail.com', 'Maceió', 2, 'Brasil', '57000000', 'Descrição 3', '32198765498745'),
('Empresa 4', 'empresa4@gmail.com', 'São Paulo', 26, 'Brasil', '01153015', 'Descrição 4', '98745835487515'),
('Empresa 5', 'empresa5@gmail.com', 'São Paulo', 26, 'Brasil', '01153000', 'Descrição 5', '23534567845629');

INSERT INTO languages (name) VALUES
('Português'),
('Inglês'),
('Espanhol'),
('Alemão'),
('Francês'),
('Chinês'),
('Japonês');

INSERT INTO academic_experiences (educational_institution, degree_type, field_of_study, course_status_id) VALUES
('Anhanguera', 'Bacharel', 'Engenharia de Software', 1),
('FGV', 'Bacharel', 'Análise e Desenvolvimento de Sistemas', 2),
('PUC', 'Mestrado', 'Engenharia de Software', 1),
('Logatti', 'Bacharel', 'Engenharia de Software', 3),
('UFPR', 'Bacharel', 'Engenharia da Computação', 1),
('USP', 'Bacharel', 'Ciência da Computação', 2),
('FGV', 'Bacharel', 'Análise e Desenvolvimento de Sistemas', 2),
('PUC', 'Mestrado', 'Engenharia de Software', 1);

INSERT INTO work_experiences (title, company_name, city, currently_work, description, state_id, contract_type_id, location_id) VALUES
('Assistente Fiscal', 'Empresa 1', 'Patos de Minas', true, 'Descrição Emprego 1', 11, 1, 1),
('Assistente Contábil', 'Empresa Beta', 'Curitiba', false, 'Descrição Emprego 2', 18, 3, 3),
('Desenvolvedor Backend', 'Empresa Neo', 'São Paulo', true, 'Descrição Emprego 3', 26, 2, 1),
('Estagiário de Desenvolvimento', 'Empresa 8', 'Patos de Minas', true, 'Descrição Emprego 4', 11, 4, 2),
('Desenvolvedor Web', 'Empresa Neo', 'São Paulo', true, 'Descrição Emprego 5', 26, 2, 1),
('Assistente Cantábil', 'Empresa Alfa', 'Curitiba', false, 'Descrição Emprego 6', 18, 3, 3),
('Desenvolvedor Web', 'Empresa Neo', 'São Paulo', false, 'Descrição Emprego 7',26, 2, 1);

INSERT INTO job_vacancies (title, description, salary, contract_type_id, location_type_id) VALUES
('Vaga 1', 'Descrição Vaga 1', 1000, 4, 2),
('Vaga 2', 'Descrição Vaga 2', 2000, 3, 1),
('Vaga 3', 'Descrição Vaga 3', 3000, 1, 3),
('Vaga 4', 'Descrição Vaga 4', 2500, 2, 1),
('Vaga 5', 'Descrição Vaga 5', 3500, 1, 1),
('Vaga 6', 'Descrição Vaga 6', 2250, 2, 3);

INSERT INTO company_job_vacancies (company_id, job_vacancy_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(4, 4),
(3, 5),
(2, 6);

INSERT INTO company_benefits (company_id, benefit_id) VALUES
(1, 1),
(1, 2),
(1, 5),
(3, 1),
(2, 2),
(3, 4),
(2, 3),
(4, 5),
(4, 6),
(4, 2),
(5, 1),
(5, 3),
(5, 5);

INSERT INTO job_vacancy_skills (job_vacancy_id, skill_id) VALUES
(1, 1),
(1, 8),
(1, 4),
(2, 12),
(2, 3),
(4, 1),
(4, 6),
(4, 8),
(5, 9),
(5, 10),
(5, 12),
(6, 12),
(6, 3),
(2, 11),
(6, 6),
(6, 7);

INSERT INTO candidate_academic_experiences (candidate_id, academic_experience_id) VALUES
(1, 1),
(2, 2),
(2, 3),
(3, 4),
(4, 5),
(4, 6),
(5, 7),
(5, 8);

INSERT INTO candidate_languages (candidate_id, language_id, proficiency_id) VALUES
(1, 1, 3),
(1, 2, 2),
(2, 1, 3),
(3, 1, 3),
(3, 3, 3),
(3, 5, 1),
(4, 1, 3),
(4, 6, 2),
(5, 1, 3);

INSERT INTO candidate_certificates (candidate_id, certificate_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(3, 5),
(3, 6),
(2, 7),
(5, 8),
(5, 9);

INSERT INTO candidate_work_experiences (candidate_id, work_experience_id) VALUES
(1, 1),
(2, 2),
(2, 3),
(3, 4),
(5, 5),
(5, 6),
(5, 7);

INSERT INTO candidate_skills (candidate_id, skill_id, proficiency_id) VALUES
(1, 1, 2),
(2, 1, 3),
(2, 2, 2),
(2, 4, 1),
(3, 1, 3),
(3, 2, 2),
(3, 5, 2),
(3, 6, 1),
(4, 7, 3),
(4, 3, 2),
(4, 8, 1),
(5, 10, 3),
(5, 11, 2),
(5, 8, 1),
(5, 12, 3);

INSERT INTO candidate_matches (candidate_id, company_job_vacancy_id) VALUES
(1, 1),
(1, 2),
(1, 6),
(2, 3),
(4, 5),
(4, 1),
(3, 3),
(3, 4),
(5, 1),
(5, 6),
(5, 4),
(3, 1);

INSERT INTO company_matches (company_job_vacancy_id, candidate_match_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 2),
(3, 4),
(4, 1),
(4, 5),
(6, 1),
(6, 3),
(6, 4);
