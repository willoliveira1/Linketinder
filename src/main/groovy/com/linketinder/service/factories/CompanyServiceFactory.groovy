package com.linketinder.service.factories

import com.linketinder.builders.company.*
import com.linketinder.dao.companydao.interfaces.*
import com.linketinder.database.*
import com.linketinder.database.interfaces.*
import com.linketinder.service.CompanyService
import com.linketinder.service.interfaces.ICompanyService
import com.linketinder.validation.CompanyValidation
import com.linketinder.validation.interfaces.ICompanyValidation

class CompanyServiceFactory {

    static ICompanyService createCompanyService() {
        IConnection connection = ConnectionFactory.createConnection("POSTGRESQL")

        ICompanyValidation validation = new CompanyValidation()

        IRequiredSkillDAO requiredSkillDAO = new RequiredSkillDAOBuilder()
                .withConnection(connection)
                .build()

        IJobVacancyDAO jobVacancyDAO = new JobVacancyDAOBuilder()
                .withConnection(connection)
                .withRequiredSkillDAO(requiredSkillDAO)
                .build()

        IBenefitDAO benefitDAO = new BenefitDAOBuilder()
                .withConnection(connection)
                .build()

        ICompanyDAO companyDAO = new CompanyDAOBuilder()
                .withConnection(connection)
                .withBenefitDAO(benefitDAO)
                .withJobVacancyDAO(jobVacancyDAO)
                .withRequiredSkillDAO(requiredSkillDAO)
                .build()

        return new CompanyService(companyDAO, validation)
    }

}
