package com.linketinder.builders.company


import com.linketinder.dao.companydao.CompanyDAO
import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.database.interfaces.*

class CompanyDAOBuilder implements com.linketinder.builders.interfaces.ICompanyDAOBuilder {

    IConnection connection

    IBenefitDAO benefitDAO
    IJobVacancyDAO jobVacancyDAO
    IRequiredSkillDAO requiredSkillDAO

    @Override
    CompanyDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    CompanyDAOBuilder withBenefitDAO(IBenefitDAO benefitDAO) {
        this.benefitDAO = benefitDAO
        return this
    }

    @Override
    CompanyDAOBuilder withJobVacancyDAO(IJobVacancyDAO jobVacancyDAO) {
        this.jobVacancyDAO = jobVacancyDAO
        return this
    }

    @Override
    CompanyDAOBuilder withRequiredSkillDAO(IRequiredSkillDAO requiredSkillDAO) {
        this.requiredSkillDAO = requiredSkillDAO
        return this
    }

    @Override
    ICompanyDAO build() {
        return new CompanyDAO(
            this.connection,
            this.benefitDAO,
            this.jobVacancyDAO
        )
    }

}
