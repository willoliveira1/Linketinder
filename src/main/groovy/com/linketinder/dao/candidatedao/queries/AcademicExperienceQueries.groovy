package com.linketinder.dao.candidatedao.queries

class AcademicExperienceQueries {

    static final String GET_ACADEMIC_EXPERIENCES_BY_CANDIDATE_ID = """
        SELECT a.id, a.educational_institution, a.degree_type, a.field_of_study, cs.title 
            FROM candidates AS c, academic_experiences AS a, course_status AS cs 
            WHERE c.id = a.candidate_id 
            AND a.course_status_id = cs.id 
            AND c.id=?;
    """

    static final String GET_ACADEMIC_EXPERIENCE_BY_ID = """
        SELECT * 
            FROM academic_experiences 
            WHERE id=?;
    """

    static final String INSERT_ACADEMIC_EXPERIENCE = """
        INSERT INTO academic_experiences (candidate_id, educational_institution, degree_type, field_of_study, course_status_id) 
            VALUES (?,?,?,?,?);
    """

    static final String UPDATE_ACADEMIC_EXPERIENCE = """
        UPDATE academic_experiences 
            SET candidate_id=?, educational_institution=?, degree_type=?, field_of_study=?, course_status_id=? 
            WHERE id=?""";

    static final String DELETE_ACADEMIC_EXPERIENCE = """
        DELETE FROM academic_experiences 
            WHERE id=?;
    """

}
