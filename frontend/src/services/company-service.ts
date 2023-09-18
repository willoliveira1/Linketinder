import Company from "../models/company/company";

import data from "../../../src/main/resources/empresas.json";

export default class CompanyService {

    initialCompanies: Array<Company> = data;

    generateCompanies(): void {
        localStorage.setItem("companies", JSON.stringify(data))
    }

    generateCompany(): Company {
        if (localStorage.getItem("companies") == null) {
            this.generateCompanies();
        }

        let companies = JSON.parse(localStorage.getItem("companies")!);

        let index: number = 0;
        let company: Company = new Company(
            companies[index].id,
            companies[index].name,
            companies[index].email,
            companies[index].city,
            companies[index].state,
            companies[index].cep,
            companies[index].description,
            companies[index].cnpj,
            companies[index].jobVacancies,
            companies[index].benefits
        );

        return company;
    }

    updateCompany(company: Company): void {
        let companies: Company[] = JSON.parse(localStorage.getItem("companies")!);

        let index = companies.findIndex(c => c.id == company.id);
        if (index != -1) {
            companies[index] = company;
        }

        localStorage.setItem("companies", JSON.stringify(companies));
    }

    createCompany(company: Company): void {
        let companies = localStorage.getItem("companies");

        if (companies == null) {
            this.generateCompanies();
            companies = localStorage.getItem("companies");
        }
        
        let newCompany: String = JSON.stringify(company);
        companies! += newCompany;

        localStorage.setItem("companies", companies!);
    }

}
