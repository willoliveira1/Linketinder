package com.linketinder.context.builders.interfaces

import com.linketinder.dao.companydao.interfaces.*

interface ICompanyDAOBuilder extends IDAOBuilder<ICompanyDAO> {

    ICompanyDAOBuilder withBenefitDAO(IBenefitDAO benefitDAO)
    ICompanyDAOBuilder withJobVacancyDAO(IJobVacancyDAO jobVacancyDAO)
    ICompanyDAOBuilder withRequiredSkillDAO(IRequiredSkillDAO requiredSkillDAO)

}
