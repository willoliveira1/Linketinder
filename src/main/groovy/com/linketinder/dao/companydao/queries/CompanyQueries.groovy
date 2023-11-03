package com.linketinder.dao.companydao.queries

class CompanyQueries {

    static final String GET_ALL_COMPANIES = """
        SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cnpj 
            FROM companies AS c, states AS s 
            WHERE c.state_id = s.id 
            ORDER BY c.id;
    """

    static final String GET_COMPANY_BY_ID = """
        SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cnpj 
            FROM companies AS c, states AS s 
            WHERE c.state_id = s.id 
            AND c.id=?;
    """

    static final String GET_COMPANY_BENEFITS_BY_COMPANY_ID = """
        SELECT id 
            FROM company_benefits 
            WHERE company_id=?;
    """

    static final String GET_STATE_ID_BY_TITLE = """
        SELECT id 
            FROM states 
            WHERE acronym=?;
    """

    static final String INSERT_COMPANY = """
        INSERT INTO companies (name, email, city, state_id, country, cep, description, cnpj) 
            VALUES (?,?,?,?,?,?,?,?);
    """

    static final String UPDATE_COMPANY = """
        UPDATE companies 
            SET name=?, email=?, city=?, state_id=?, country=?, cep=?, description=?, cnpj=? 
            WHERE id=?;
    """

    static final String DELETE_COMPANY = """
        DELETE FROM companies 
            WHERE id=?;
    """

}
