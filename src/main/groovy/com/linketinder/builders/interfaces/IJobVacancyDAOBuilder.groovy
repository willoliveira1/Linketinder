package com.linketinder.builders.interfaces

import com.linketinder.dao.companydao.interfaces.*

interface IJobVacancyDAOBuilder extends IBaseDAOBuilder<IJobVacancyDAO> {

    IJobVacancyDAOBuilder withRequiredSkillDAO(IRequiredSkillDAO requiredSkillDAO)

}
