package com.linketinder.dao.companydao.queries

class RequiredSkillQueries {

    static final String GET_SKILLS_BY_JOB_VACANCY_ID = """
        SELECT jbs.id, jbs.job_vacancy_id, s.title 
            FROM job_vacancy_skills AS jbs, skills AS s, job_vacancies AS jb 
            WHERE jbs.skill_id = s.id 
            AND jb.id = jbs.job_vacancy_id 
            AND jb.id=?;
    """

    static final String GET_SKILL_BY_ID = """
        SELECT * 
            FROM job_vacancy_skills 
            WHERE id=?;
    """

    static final String GET_SKILL_BY_TITLE = """
        SELECT * 
            FROM skills 
            WHERE title=?;
    """

    static final String GET_SKILL_ID_BY_TITLE = """
        SELECT id 
            FROM skills 
            WHERE title=?;
    """

    static final String INSERT_JOB_VACANCY_SKILL = """
        INSERT INTO job_vacancy_skills (job_vacancy_id, skill_id) 
            VALUES (?,?);
    """

    static final String INSERT_SKILL = """
        INSERT INTO skills (title) 
            VALUES (?);
    """

    static final String UPDATE_JOB_VACANCY_SKILL = """
        UPDATE job_vacancy_skills 
            SET job_vacancy_id=?, skill_id=? 
            WHERE id=?;
    """

    static final String DELETE_JOB_VACANCY_SKILL_BY_ID = """
        DELETE FROM job_vacancy_skills 
            WHERE id=?;
    """

}
