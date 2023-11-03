package com.linketinder.builders.interfaces

import com.linketinder.dao.matchdao.interfaces.ICandidateMatchDAO
import com.linketinder.dao.matchdao.interfaces.IMatchDAO

interface ICandidateMatchDAOBuilder extends IBaseDAOBuilder<ICandidateMatchDAO> {

    ICandidateMatchDAOBuilder withMatchDAO(IMatchDAO matchDAO)

}
