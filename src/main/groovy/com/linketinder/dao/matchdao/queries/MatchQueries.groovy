package com.linketinder.dao.matchdao.queries

class MatchQueries {

    static final String GET_ALL_MATCHES = """
        SELECT id, candidate_id, company_id, job_vacancy_id 
            FROM matches 
            WHERE company_id IS NOT NULL 
            AND job_vacancy_id IS NOT NULL 
            ORDER BY id;
    """

    static final String UPDATE_MATCH = """
        UPDATE matches 
            SET candidate_id=?, company_id=?, job_vacancy_id=? 
            WHERE id=?;
    """

}
