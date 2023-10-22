package com.linketinder.context.builder.candidate

import com.linketinder.context.builder.interfaces.IBaseDAOBuilder
import com.linketinder.dao.candidatedao.CertificateDAO
import com.linketinder.dao.candidatedao.interfaces.ICertificateDAO
import com.linketinder.database.interfaces.IConnection

class CertificateDAOBuilder implements IBaseDAOBuilder<ICertificateDAO> {

    IConnection connectionFactory

    @Override
    CertificateDAOBuilder withConnection(IConnection connectionFactory) {
        this.connectionFactory = connectionFactory
        return this
    }

    @Override
    ICertificateDAO build() {
        return new CertificateDAO(connectionFactory)
    }

}
