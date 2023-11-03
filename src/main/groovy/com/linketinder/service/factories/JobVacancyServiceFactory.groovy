package com.linketinder.service.factories

import com.linketinder.builders.company.JobVacancyDAOBuilder
import com.linketinder.builders.company.RequiredSkillDAOBuilder
import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.database.*
import com.linketinder.database.interfaces.*
import com.linketinder.service.JobVacancyService
import com.linketinder.service.interfaces.IJobVacancyService

class JobVacancyServiceFactory {

    static IJobVacancyService createJobVacancyService() {
        IConnection connection = ConnectionFactory.createConnection("POSTGRESQL")

        IRequiredSkillDAO requiredSkillDAO = new RequiredSkillDAOBuilder()
                .withConnection(connection)
                .build()

        IJobVacancyDAO jobVacancyDAO = new JobVacancyDAOBuilder()
                .withConnection(connection)
                .withRequiredSkillDAO(requiredSkillDAO)
                .build()

        return new JobVacancyService(jobVacancyDAO)
    }

}
