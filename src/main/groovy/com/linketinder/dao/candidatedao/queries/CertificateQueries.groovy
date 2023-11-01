package com.linketinder.dao.candidatedao.queries

class CertificateQueries {

    static final String GET_CERTIFICATES_BY_CANDIDATE_ID = """
        SELECT id, candidate_id, title, duration 
            FROM certificates 
            WHERE candidate_id=? 
            ORDER BY title;
    """

    static final String GET_CERTIFICATE_BY_ID = """
        SELECT * 
            FROM certificates 
            WHERE id=?;
    """

    static final String INSERT_CERTIFICATE = """
        INSERT INTO certificates (candidate_id, title, duration) 
            VALUES (?,?,?);
    """

    static final String UPDATE_CERTIFICATE = """
        UPDATE certificates 
            SET candidate_id=?, title=?, duration=? 
            WHERE id=?;
    """

    static final String DELETE_CERTIFICATE = """
        DELETE FROM certificates 
            WHERE id=?;
    """

}
