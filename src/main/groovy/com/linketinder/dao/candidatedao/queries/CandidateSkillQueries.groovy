package com.linketinder.dao.candidatedao.queries

class CandidateSkillQueries {

    static final String GET_SKILLS_BY_CANDIDATE_ID = """
        SELECT cs.id, s.title, p.title AS proficiency_title 
            FROM candidates AS c, candidate_skills AS cs, skills AS s, proficiences AS p 
            WHERE c.id = cs.candidate_id 
            AND s.id = cs.skill_id 
            AND p.id = cs.proficiency_id 
            AND c.id=?;
    """

    static final String GET_SKILL_BY_ID = """
        SELECT * 
            FROM candidate_skills 
            WHERE id=?;
    """

    static final String GET_SKILL_ID_BY_TITLE = """
        SELECT id 
            FROM skills 
            WHERE title=?;
    """

    static final String GET_PROFICIENCY_ID_BY_TITLE = """
        SELECT id 
            FROM proficiences 
            WHERE title=?;
    """

    static final String INSERT_CANDIDATE_SKILL = """
        INSERT INTO candidate_skills (candidate_id, skill_id, proficiency_id) 
            VALUES (?,?,?);
    """

    static final String UPDATE_CANDIDATE_SKILL = """
        UPDATE candidate_skills 
            SET candidate_id=?, skill_id=?, proficiency_id=? 
            WHERE id=?;
    """

    static final String DELETE_CANDIDATE_SKILL = """
        DELETE FROM candidate_skills 
            WHERE id=?;
    """

    static final String INSERT_SKILL = """
        INSERT INTO skills (title) 
            VALUES (?);
    """

    static final String GET_SKILL_BY_TITLE = """
        SELECT * 
            FROM skills 
            WHERE title=?;
    """

}
