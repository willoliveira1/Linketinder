package com.linketinder.builders.candidate


import com.linketinder.dao.candidatedao.WorkExperienceDAO
import com.linketinder.dao.candidatedao.interfaces.IWorkExperienceDAO
import com.linketinder.database.interfaces.*

class WorkExperienceDAOBuilder implements com.linketinder.builders.interfaces.IBaseDAOBuilder<IWorkExperienceDAO> {

    IConnection connection

    @Override
    WorkExperienceDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    IWorkExperienceDAO build() {
        return new WorkExperienceDAO(this.connection)
    }

}
