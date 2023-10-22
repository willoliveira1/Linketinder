package com.linketinder.context.builders.interfaces

import com.linketinder.dao.companydao.interfaces.*

interface IJobVacancyDAOBuilder extends IDAOBuilder<IJobVacancyDAO> {

    IJobVacancyDAOBuilder withRequiredSkillDAO(IRequiredSkillDAO requiredSkillDAO)

}
