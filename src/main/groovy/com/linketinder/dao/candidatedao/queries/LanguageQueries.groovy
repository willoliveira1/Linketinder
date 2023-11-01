package com.linketinder.dao.candidatedao.queries

class LanguageQueries {

    static final String GET_LANGUAGES_BY_CANDIDATE_ID = """
        SELECT cl.id, l.name, p.title 
            FROM candidates AS c, candidate_languages AS cl, languages AS l, proficiences AS p 
            WHERE c.id = cl.candidate_id 
            AND l.id = cl.language_id 
            AND p.id = cl.proficiency_id 
            AND c.id=?;
    """

    static final String GET_LANGUAGES_BY_ID = """
        SELECT * 
            FROM candidate_languages 
            WHERE id=?;
    """

    static final String INSERT_CANDIDATE_LANGUAGE = """
        INSERT INTO candidate_languages (candidate_id, language_id, proficiency_id) 
            VALUES (?,?,?);
    """

    static final String UPDATE_CANDIDATE_LANGUAGE = """
        UPDATE candidate_languages 
            SET candidate_id=?, language_id=?, proficiency_id=? 
            WHERE id=?;
    """

    static final String DELETE_CANDIDATE_LANGUAGE = """
        DELETE FROM candidate_languages 
            WHERE id=?;
    """

    static final String INSERT_LANGUAGE = """
        INSERT INTO languages (name) 
            VALUES (?);
    """

    static final String GET_LANGUAGE_BY_NAME = """
        SELECT * 
            FROM languages 
            WHERE name=?;
    """

}
