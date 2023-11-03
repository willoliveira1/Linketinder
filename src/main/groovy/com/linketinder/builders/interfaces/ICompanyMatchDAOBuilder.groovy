package com.linketinder.builders.interfaces

import com.linketinder.dao.matchdao.interfaces.ICompanyMatchDAO
import com.linketinder.dao.matchdao.interfaces.IMatchDAO

interface ICompanyMatchDAOBuilder extends IBaseDAOBuilder<ICompanyMatchDAO> {

    ICompanyMatchDAOBuilder withMatchDAO(IMatchDAO matchDAO)

}
