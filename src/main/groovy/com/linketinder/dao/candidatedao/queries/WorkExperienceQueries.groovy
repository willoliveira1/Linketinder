package com.linketinder.dao.candidatedao.queries

class WorkExperienceQueries {

    static final String GET_WORK_EXPERIENCES_BY_CANDIDATE_ID = """
        SELECT we.id, we.title, we.company_name, ct.title AS contract_type_title, lt.title AS location_type_title, we.city, s.acronym, we.currently_work, we.description 
            FROM candidates AS c, work_experiences AS we, states AS s, contract_types AS ct, location_types AS lt 
            WHERE c.id = we.candidate_id 
            AND we.contract_type_id = ct.id 
            AND we.location_id = lt.id 
            AND we.state_id = s.id 
            AND c.id=?;
    """

    static final String GET_WORK_EXPERIENCES_ID = """
        SELECT * 
            FROM work_experiences 
            WHERE id=?;
    """

    static final String GET_STATE_ID_BY_TITLE = """
        SELECT id 
            FROM states 
            WHERE acronym=?;
    """

    static final String GET_CONTRACT_TYPE_ID_BY_TITLE = """
        SELECT id 
            FROM contract_types 
            WHERE title=?;
    """

    static final String GET_LOCATION_TYPE_ID_BY_TITLE = """
        SELECT id 
            FROM location_types 
            WHERE title=?;
    """

    static final String INSERT_WORK_EXPERIENCE = """
        INSERT INTO work_experiences (candidate_id, title, company_name, city, currently_work, description, state_id, contract_type_id, location_id) 
            VALUES (?,?,?,?,?,?,?,?,?);
    """

    static final String UPDATE_WORK_EXPERIENCE = """
        UPDATE work_experiences 
            SET candidate_id=?, title=?, company_name=?, city=?, currently_work=?, description=?, state_id=?, contract_type_id=?, location_id=? 
            WHERE id=?;
    """

    static final String DELETE_WORK_EXPERIENCE = """
        DELETE FROM work_experiences 
            WHERE id=?;
    """

}
