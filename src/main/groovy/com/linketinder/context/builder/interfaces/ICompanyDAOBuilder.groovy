package com.linketinder.context.builder.interfaces

import com.linketinder.dao.companydao.interfaces.*

interface ICompanyDAOBuilder extends IDAOBuilder<ICompanyDAO> {

    ICompanyDAOBuilder withBenefitDAO(IBenefitDAO benefitDAO)
    ICompanyDAOBuilder withJobVacancyDAO(IJobVacancyDAO jobVacancyDAO)
    ICompanyDAOBuilder withRequiredSkillDAO(IRequiredSkillDAO requiredSkillDAO)

}
