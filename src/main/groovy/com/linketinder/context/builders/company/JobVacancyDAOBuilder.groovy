package com.linketinder.context.builders.company

import com.linketinder.context.builders.interfaces.*
import com.linketinder.dao.companydao.JobVacancyDAO
import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.database.interfaces.*

class JobVacancyDAOBuilder implements IJobVacancyDAOBuilder {

    IConnection connection
    IRequiredSkillDAO requiredSkillDAO

    @Override
    JobVacancyDAOBuilder withConnection(IConnection connection) {
        this.connection = connection
        return this
    }

    @Override
    JobVacancyDAOBuilder withRequiredSkillDAO(IRequiredSkillDAO requiredSkillDAO) {
        this.requiredSkillDAO = requiredSkillDAO
        return this
    }

    @Override
    IJobVacancyDAO build() {
        return new JobVacancyDAO(this.connection, this.requiredSkillDAO)
    }
}
