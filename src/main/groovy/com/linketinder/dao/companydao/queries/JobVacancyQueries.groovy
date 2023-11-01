package com.linketinder.dao.companydao.queries

class JobVacancyQueries {

    static final String GET_ALL_JOB_VACANCIES = """
        SELECT jv.id, jv.title, jv.description, jv.salary, ct.title AS contract_type, lt.title AS location_type 
            FROM job_vacancies AS jv, companies AS c, contract_types AS ct, location_types AS lt 
            WHERE jv.company_id= c.id 
            AND jv.contract_type_id = ct.id 
            AND jv.location_type_id = lt.id;
    """

    static final String GET_JOB_VACANCY_BY_COMPANY_ID = """
        SELECT jv.id, jv.title, jv.description, jv.salary, ct.title AS contract_type, lt.title AS location_type 
            FROM job_vacancies AS jv, companies AS c, contract_types AS ct, location_types AS lt 
            WHERE jv.company_id= c.id 
            AND jv.contract_type_id = ct.id 
            AND jv.location_type_id = lt.id 
            AND c.id=?;
    """

    static final String GET_JOB_VACANCY_BY_ID = """
        SELECT jv.id, jv.title, jv.description, jv.salary, ct.title AS contract_type, lt.title AS location_type 
            FROM job_vacancies AS jv, companies AS c, contract_types AS ct, location_types AS lt 
            WHERE jv.company_id= c.id 
            AND jv.contract_type_id = ct.id 
            AND jv.location_type_id = lt.id 
            AND jv.id=?;
    """

    static final String GET_SKILL_BY_JOB_VACANCY_ID = """
        SELECT id 
            FROM job_vacancy_skills 
            WHERE job_vacancy_id=?;
    """

    static final String GET_COMPANY_ID = """
        SELECT company_id 
            FROM job_vacancies 
            WHERE id=?;
    """

    static final String INSERT_JOB_VACANCY = """
        INSERT INTO job_vacancies (company_id, title, description, salary, contract_type_id, location_type_id) 
            VALUES (?,?,?,?,?,?);
    """

    static final String UPDATE_JOB_VACANCY = """
        UPDATE job_vacancies 
            SET company_id=?, title=?, description=?, salary=?, contract_type_id=?, location_type_id=? 
            WHERE id=?;
    """

    static final String DELETE_JOB_VACANCY = """
        DELETE FROM job_vacancies 
            WHERE id=?;
    """

}
