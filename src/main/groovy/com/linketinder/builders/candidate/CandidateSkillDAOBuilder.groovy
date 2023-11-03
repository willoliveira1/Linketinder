package com.linketinder.builders.candidate


import com.linketinder.dao.candidatedao.CandidateSkillDAO
import com.linketinder.dao.candidatedao.interfaces.ICandidateSkillDAO
import com.linketinder.database.interfaces.*

class CandidateSkillDAOBuilder implements com.linketinder.builders.interfaces.IBaseDAOBuilder<ICandidateSkillDAO> {

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
