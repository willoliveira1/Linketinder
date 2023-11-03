package com.linketinder.service.factories

import com.linketinder.context.builders.company.*
import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.database.ConnectionFactory
import com.linketinder.database.DBService
import com.linketinder.database.interfaces.*
import com.linketinder.service.JobVacancyService
import com.linketinder.service.interfaces.IJobVacancyService

class JobVacancyServiceFactory {

    static IJobVacancyService createJobVacancyService() {
        IConnection connection = ConnectionFactory.createConnection("POSTGRESQL")
        IDBService dbService = new DBService(connection)

        IRequiredSkillDAO requiredSkillDAO = new RequiredSkillDAOBuilder()
                .withDBService(dbService)
                .withConnection(connection)
                .build()

        IJobVacancyDAO jobVacancyDAO = new JobVacancyDAOBuilder()
                .withDBService(dbService)
                .withConnection(connection)
                .withRequiredSkillDAO(requiredSkillDAO)
                .build()

        return new JobVacancyService(jobVacancyDAO)
    }

}
