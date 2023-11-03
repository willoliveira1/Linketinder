package com.linketinder.context.builders.candidate

import com.linketinder.context.builders.interfaces.IBaseDAOBuilder
import com.linketinder.dao.candidatedao.CandidateSkillDAO
import com.linketinder.dao.candidatedao.interfaces.ICandidateSkillDAO
import com.linketinder.database.interfaces.*

class CandidateSkillDAOBuilder implements IBaseDAOBuilder<ICandidateSkillDAO> {

    IConnection connection

    @Override
    CandidateSkillDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    ICandidateSkillDAO build() {
        return new CandidateSkillDAO(this.connection)
    }

}
