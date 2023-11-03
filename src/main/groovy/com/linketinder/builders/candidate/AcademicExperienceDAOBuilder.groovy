package com.linketinder.builders.candidate


import com.linketinder.dao.candidatedao.AcademicExperienceDAO
import com.linketinder.dao.candidatedao.interfaces.IAcademicExperienceDAO
import com.linketinder.database.interfaces.*

class AcademicExperienceDAOBuilder implements com.linketinder.builders.interfaces.IBaseDAOBuilder<IAcademicExperienceDAO> {

    IConnection connection

    AcademicExperienceDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    IAcademicExperienceDAO build() {
        return new AcademicExperienceDAO(this.connection)
    }

}
