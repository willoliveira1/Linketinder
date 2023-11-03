package com.linketinder.builders.match


import com.linketinder.dao.matchdao.CompanyMatchDAO
import com.linketinder.dao.matchdao.interfaces.*
import com.linketinder.database.interfaces.IConnection

class CompanyMatchDAOBuilder implements com.linketinder.builders.interfaces.ICompanyMatchDAOBuilder {

    IConnection connection
    IMatchDAO matchDAO

    @Override
    CompanyMatchDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    CompanyMatchDAOBuilder withMatchDAO(IMatchDAO matchDAO) {
        this.matchDAO = matchDAO
        return this
    }

    @Override
    ICompanyMatchDAO build() {
        return new CompanyMatchDAO(this.matchDAO, this.connection)
    }

}
