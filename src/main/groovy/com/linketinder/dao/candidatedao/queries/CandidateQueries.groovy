package com.linketinder.dao.candidatedao.queries

class CandidateQueries {

    static final String GET_ALL_CANDIDATES = """
        SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cpf 
            FROM candidates AS c, states AS s 
            WHERE c.state_id = s.id 
            ORDER BY c.id;
    """

    static final String GET_CANDIDATE_BY_ID = """
        SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cpf 
            FROM candidates AS c, states AS s 
            WHERE c.state_id = s.id 
            AND c.id=?;
    """

    static final String GET_ACADEMIC_EXPERIENCE_ID_BY_CANDIDATE_ID = """
        SELECT id 
            FROM academic_experiences 
            WHERE candidate_id=?;
    """

    static final String GET_CERTIFICATE_ID_BY_CANDIDATE_ID = """
        SELECT id 
            FROM certificates 
            WHERE candidate_id=?;
    """

    static final String GET_LANGUAGE_ID_BY_CANDIDATE_ID = """
        SELECT id 
            FROM candidate_languages 
            WHERE candidate_id=?;
    """

    static final String GET_SKILL_ID_BY_CANDIDATE_ID = """
        SELECT id 
            FROM candidate_skills 
            WHERE candidate_id=?;
    """

    static final String GET_WORK_EXPERIENCE_ID_BY_CANDIDATE_ID = """
        SELECT id 
            FROM work_experiences 
            WHERE candidate_id=?;
    """

    static final String INSERT_CANDIDATE = """
        INSERT INTO candidates (name, email, city, state_id, country, cep, description, cpf) 
            VALUES (?,?,?,?,?,?,?,?);
    """

    static final String UPDATE_CANDIDATE = """
        UPDATE candidates 
            SET name=?, email=?, city=?, state_id=?, country=?, cep=?, description=?, cpf=? 
            WHERE id=?;
    """

    static final String DELETE_CANDIDATE = """
        DELETE FROM candidates 
            WHERE id=?;
    """

}
