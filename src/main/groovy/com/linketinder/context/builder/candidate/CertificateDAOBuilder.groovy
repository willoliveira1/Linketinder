package com.linketinder.context.builder.candidate

import com.linketinder.context.builder.interfaces.IBaseDAOBuilder
import com.linketinder.dao.candidatedao.CertificateDAO
import com.linketinder.dao.candidatedao.interfaces.ICertificateDAO
import com.linketinder.database.interfaces.IConnection

class CertificateDAOBuilder implements IBaseDAOBuilder<ICertificateDAO> {

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
