package com.linketinder.context.builders.candidate

import com.linketinder.context.builders.interfaces.IBaseDAOBuilder
import com.linketinder.dao.candidatedao.AcademicExperienceDAO
import com.linketinder.dao.candidatedao.interfaces.IAcademicExperienceDAO
import com.linketinder.database.interfaces.*

class AcademicExperienceDAOBuilder implements IBaseDAOBuilder<IAcademicExperienceDAO> {

    IConnection connection

    AcademicExperienceDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    IAcademicExperienceDAO build() {
        return new AcademicExperienceDAO(this.connection)
    }

}
