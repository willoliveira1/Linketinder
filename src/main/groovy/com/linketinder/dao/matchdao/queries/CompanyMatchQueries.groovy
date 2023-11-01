package com.linketinder.dao.matchdao.queries

class CompanyMatchQueries {

    static final String GET_ALL_MATCHES_BY_COMPANY_ID = """
        SELECT id, candidate_id, company_id, job_vacancy_id 
            FROM matches 
            WHERE company_id=? 
            AND job_vacancy_id IS NOT NULL 
            ORDER BY id;
    """

    static final String GET_MATCH_BY_CANDIDATE_ID_AND_COMPANY_ID = """
        SELECT id, candidate_id, company_id, job_vacancy_id 
            FROM matches 
            WHERE candidate_id=? 
            AND company_id=?;
    """

    static final String COMPANY_LIKE_CANDIDATE = """
        SELECT DISTINCT m.id, m.candidate_id, m.job_vacancy_id, m.company_id 
            FROM matches AS m, job_vacancies AS j 
            WHERE m.candidate_id=? 
            AND (m.job_vacancy_id = j.id OR m.job_vacancy_id IS NULL) 
            AND (m.company_id=? OR m.company_id IS NULL);
    """

    static final String INSERT_COMPANY_LIKE = """
        INSERT INTO matches (candidate_id, job_vacancy_id, company_id) 
            VALUES (?,null,?);
    """

}
