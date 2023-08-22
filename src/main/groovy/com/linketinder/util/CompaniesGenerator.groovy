package com.linketinder.util

import groovy.json.JsonBuilder
import com.linketinder.domain.company.Benefit
import com.linketinder.domain.company.Company
import com.linketinder.domain.shared.Person
import com.linketinder.domain.shared.State

class CompaniesGenerator {

    void runGenerator() {
        Person company1 = new Company(
                id: 1,
                name: "Empresa 1",
                email: "empresa1@empresa1.com.br",
                city: "Araraquara",
                state: State.SP,
                country: "Brasil",
                cep: "14800000",
                description: "Sobre a empresa 1",
                cnpj: "65498732165478",
                jobVacancies: [2,3],
                benefits: [
                        new Benefit(
                                id: 1,
                                title: "Vale Transporte"
                        ),
                        new Benefit(
                                id: 2,
                                title: "Refeitório no local"
                        ),
                        new Benefit(
                                id: 3,
                                title: "Plano de Saúde"
                        )
                ]
        )

        Person company2 = new Company(
                id: 2,
                name: "Empresa 2",
                email: "empresa2@empresa2.com.br",
                city: "Uberlândia",
                state: State.MG,
                country: "Brasil",
                cep: "04680000",
                description: "Sobre a empresa 2",
                cnpj: "12345678912345",
                jobVacancies: [1],
                benefits: [
                        new Benefit(
                                id: 1,
                                title: "Plano de Saúde"
                        )
                ]
        )

        Person company3 = new Company(
                id: 3,
                name: "Empresa 3",
                email: "empresa3@empresa3.com.br",
                city: "São Paulo",
                state: State.SP,
                country: "Brasil",
                cep: "04500000",
                description: "Sobre a empresa 3",
                cnpj: "45671237236234",
                jobVacancies: [],
                benefits: [
                        new Benefit(
                                id: 1,
                                title: "Vale Transporte"
                        ),
                        new Benefit(
                                id: 2,
                                title: "Vale Refeição"
                        ),
                        new Benefit(
                                id: 3,
                                title: "Plano de Saúde"
                        ),
                        new Benefit(
                                id: 4,
                                title: "Plano Odontológico"
                        )
                ]
        )

        Person company4 = new Company(
                id: 4,
                name: "Empresa 4",
                email: "empresa4@empresa4.com.br",
                city: "Manaus",
                state: State.AM,
                country: "Brasil",
                cep: "17900000",
                description: "Sobre a empresa 4",
                cnpj: "61789456435412",
                jobVacancies: [],
                benefits: []
        )

        Person company5 = new Company(
                id: 5,
                name: "Empresa 5",
                email: "empresa5@empresa5.com.br",
                city: "Curitiba",
                state: State.PR,
                country: "Brasil",
                cep: "19400000",
                description: "Sobre a empresa 5",
                cnpj: "12373415345763",
                jobVacancies: [],
                benefits: [
                        new Benefit(
                                id: 1,
                                title: "Plano de Saúde"
                        ),
                        new Benefit(
                                id: 2,
                                title: "Plano Odontológico"
                        ),
                        new Benefit(
                                id: 3,
                                title: "Vale Refeição"
                        ),
                        new Benefit(
                                id: 4,
                                title: "Vale Transporte"
                        )
                ]
        )

        List<Company> companies = [company1, company2, company3, company4, company5]

        JsonBuilder companiesBuilder = new JsonBuilder(companies)
        String filePath = "../../../resources/empresas.json"
        def file = new File(filePath)
        file.createNewFile()
        file.write(companiesBuilder.toPrettyString())
    }

}
