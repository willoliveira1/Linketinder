package com.linketinder.dao.matchdao.queries

class CandidateMatchQueries {

    static final String GET_ALL_MATCHES_BY_CANDIDATE_ID = """
        SELECT id, candidate_id, company_id, job_vacancy_id 
            FROM matches 
            WHERE candidate_id=? 
            AND company_id IS NOT NULL 
            AND job_vacancy_id IS NOT NULL 
            ORDER BY id;
    """

    static final String GET_COMPANY_ID_BY_JOB_VACANCY_ID = """
        SELECT DISTINCT company_id 
            FROM job_vacancies 
            WHERE id=?;
    """

    static final String GET_MATCH_BY_CANDIDATE_ID_AND_JOB_VACANCY_ID = """
        SELECT id, candidate_id, company_id, job_vacancy_id 
            FROM matches 
            WHERE candidate_id=? 
            AND job_vacancy_id=?;
    """

    static final String CANDIDATE_LIKE_JOB_VACANCY = """
        SELECT id, candidate_id, job_vacancy_id, company_id 
            FROM matches 
            WHERE (candidate_id=? AND company_id=?) 
            OR (candidate_id=? AND job_vacancy_id=?);
    """

    static final String INSERT_CANDIDATE_LIKE = """
        INSERT INTO matches (candidate_id, job_vacancy_id, company_id) 
            VALUES (?,?,null);
    """

}
