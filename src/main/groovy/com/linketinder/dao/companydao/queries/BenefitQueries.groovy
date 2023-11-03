package com.linketinder.dao.companydao.queries

class BenefitQueries {

    static final String GET_BENEFITS_BY_COMPANY_ID = """
        SELECT cb.id, c.id AS company_id, b.title 
            FROM companies AS c, company_benefits AS cb, benefits AS b 
            WHERE c.id = cb.company_id 
            AND b.id = cb.benefit_id 
            AND c.id=?;
    """

    static final String GET_COMPANY_BENEFIT_BY_ID = """
        SELECT * 
            FROM company_benefits 
            WHERE id=?;
    """

    static final String GET_BENEFIT_BY_TITLE = """
        SELECT * 
            FROM benefits 
            WHERE title=?;
    """

    static final String GET_BENEFIT_ID_BY_TITLE = """
        SELECT id 
            FROM benefits 
            WHERE title=?;
    """

    static final String INSERT_COMPANY_BENEFIT = """
        INSERT INTO company_benefits (company_id, benefit_id) 
            VALUES (?,?);
    """

    static final String INSERT_BENEFIT = """
        INSERT INTO benefits (title) 
            VALUES (?);
    """

    static final String UPDATE_COMPANY_BENEFIT = """
        UPDATE company_benefits 
            SET company_id=?, benefit_id=? 
            WHERE id=?;
    """

    static final String DELETE_COMPANY_BENEFIT = """
        DELETE FROM company_benefits 
            WHERE id=?;
    """

}
