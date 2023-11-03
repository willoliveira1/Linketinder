package com.linketinder.builders.candidate


import com.linketinder.dao.candidatedao.CertificateDAO
import com.linketinder.dao.candidatedao.interfaces.ICertificateDAO
import com.linketinder.database.interfaces.IConnection

class CertificateDAOBuilder implements com.linketinder.builders.interfaces.IBaseDAOBuilder<ICertificateDAO> {

    IConnection connection

    @Override
    CertificateDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    ICertificateDAO build() {
        return new CertificateDAO(this.connection)
    }

}
