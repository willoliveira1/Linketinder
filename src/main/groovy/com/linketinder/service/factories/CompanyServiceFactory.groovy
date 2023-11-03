package com.linketinder.service.factories

import com.linketinder.context.builders.company.*
import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.database.ConnectionFactory
import com.linketinder.database.DBService
import com.linketinder.database.interfaces.*
import com.linketinder.service.CompanyService
import com.linketinder.service.interfaces.ICompanyService

class CompanyServiceFactory {

    static ICompanyService createCompanyService() {
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

        IBenefitDAO benefitDAO = new BenefitDAOBuilder()
                .withDBService(dbService)
                .withConnection(connection)
                .build()

        ICompanyDAO companyDAO = new CompanyDAOBuilder()
                .withDBService(dbService)
                .withConnection(connection)
                .withBenefitDAO(benefitDAO)
                .withJobVacancyDAO(jobVacancyDAO)
                .withRequiredSkillDAO(requiredSkillDAO)
                .build()

        return new CompanyService(companyDAO)
    }

}
