import $ from "jquery";

import { State } from "../../models/enums/state";
import Company from "../../models/company/company";
import CompanyService from "../../services/company-service";
import Benefit from "../../models/company/benefit";

export default class CompanyProfile {

    generateForm(): void {
        document.querySelector("#container")?.remove();

        $("main").append(`
            <div id="container">
                <form id="table"></form>
            </div>
        `);

        this.generateBasicInformation();
        this.generateBenefit();
        this.generateSubmitButtons();
    }

    generateBasicInformation(): void {
        $("#table").append(`
            <div id="basic-information" class="information">
                <div class="form-field form-text">
                    <label>Nome:</label>
                    <input type="text" id="name">
                </div>
                <div class="form-list">
                    <div class="form-field">
                        <label>Email:</label>
                        <input type="email" id="email">
                    </div>
                    <div class="form-field">
                        <label>CNPJ:</label>
                        <input type="text" id="cnpj">
                    </div>
                </div>
                <div class="form-list">
                    <div class="form-field city">
                        <label>Cidade:</label>
                        <input type="text" id="city">
                    </div>
                    <div class="form-field state">
                        <label>Estado:</label>
                        <select name="state" id="states">
                            ${this.generateStates()}
                        </select>
                    </div>
                    <div class="form-field cep">
                        <label>CEP:</label>
                        <input type="text" id="cep">
                    </div>
                </div>
                <div class="form-field form-text">
                    <label>Sobre a empresa:</label>
                    <input type="email" id="description">
                </div>
            </div>
        `);
    }

    generateBenefit(): void {
        $("#table").append(`
            <div id="benefit-informations" class="information">
                <button type="button" id="add-benefit-button">Adicionar Benefício</button>
            </div>
        `);

        this.generateBenefitButton();
    }

    generateBenefitButton(): void {
        document.getElementById("add-benefit-button")?.addEventListener("click", function(): void {
            $("#benefit-informations").append(`
                <div class="benefit-information information">
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Benefício:</label>
                            <div class="line">
                                <input type="text" class="benefit-name">
                                <span class="material-symbols-outlined delete-btn">delete</span>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        });

        document.querySelectorAll(".benefit-information").forEach(benefit => {
            benefit.querySelectorAll(".delete-btn").forEach(btn => {
                btn.addEventListener("click", () => {
                    benefit.remove();
                });
            });
        });
    }

    generateSubmitButtons(): void {
        $("#table").append(`
            <div class="form-list" id="buttons">
                <p id="submit-input">Atualizar</p>
                <button type="submit" id="cancel-input">Cancelar</button>
            </div>
        `);

        document.getElementById("submit-input")?.addEventListener("click", function(): void {
            let companyService: CompanyService = new CompanyService();
            let company = companyService.generateCompany();

            company.name = <string> $("#name").val();
            company.email = <string> $("#email").val();
            company.cnpj = <string> $("#cnpj").val();
            company.city = <string> $("#city").val();
            company.state = <State> $("#states :selected").val();
            company.cep = <string> $("#cep").val();
            company.description = <string> $("#description").val();            

            let benefits: Benefit[] = [];
            let benefitId: number = 1;
            let benefitInfos = Array.from(document.querySelectorAll(".benefit-information"));
            benefitInfos.forEach(function(info) {
                let title = (info.querySelector(".benefit-name") as HTMLInputElement).value;
                benefits.push(new Benefit(benefitId++, title));
            });
            company.benefits = benefits;

            companyService.updateCompany(company);
            
            window.location.href = "http://localhost:5502/dist/";
        });

        document.getElementById("cancel-input")?.addEventListener("click", function(): void {
            window.location.href = "http://localhost:5502/dist/";
        });
    }

    generateStates(): string {
        let options!: string;
        Object.entries(State).forEach(state => {
            const [key, value] = state;
            options += `<option value="${key}">${value}</option>`
            });
        return options;
    }

    populateForm(company: Company): void {
        document.querySelector("#container")?.remove();

        $("main").append(`
            <div id="container">
                <form id="table"></form>
            </div>
        `);

        this.populateBasicInformation(company);
        this.populateBenefit(company);
        this.generateSubmitButtons();
    }

    populateBasicInformation(company: Company): void {
        $("#table").append(`
            <div id="basic-information" class="information">
                <div class="form-field form-text">
                    <label>Nome:</label>
                    <input type="text" id="name" value="${company.name}">
                </div>
                <div class="form-list">
                    <div class="form-field">
                        <label>Email:</label>
                        <input type="email" id="email" value="${company.email}">
                    </div>
                    <div class="form-field">
                        <label>CNPJ:</label>
                        <input type="text" id="cnpj" value="${company.cnpj}">
                    </div>
                </div>
                <div class="form-list">
                    <div class="form-field city">
                        <label>Cidade:</label>
                        <input type="text" id="city" value="${company.city}">
                    </div>
                    <div class="form-field state">
                        <label>Estado:</label>
                        <select name="state" id="states">
                            ${this.populateState(company.state)}
                        </select>
                    </div>
                    <div class="form-field cep">
                        <label>CEP:</label>
                        <input type="text" id="cep" value="${company.cep}">
                    </div>
                </div>
                <div class="form-field form-text">
                    <label>Sobre a empresa:</label>
                    <input type="text" id="description" value="${company.description}">
                </div>
            </div>
        `);
    }

    populateBenefit(company: Company): void {
        $("#table").append(`
            <div id="benefit-informations" class="information">
                <button type="button" id="add-benefit-button">Adicionar Benefício</button>
            </div>
        `); 

        company.benefits.forEach(benefit => {
            $("#benefit-informations").append(`
                <div class="benefit-information information">
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Benefício:</label>
                            <div class="line">
                                <input type="text" class="benefit-name" value="${benefit.title}">
                                <span class="material-symbols-outlined delete-btn">delete</span>
                            </div>
                        </div>
                    </div>
                </div>
            `); 
        });
        
        this.generateBenefitButton();
    }

    populateState(selectedState: State): string {
        let options!: string;
        Object.entries(State).forEach(state => {
            const [key, value] = state;
            const isSelected = key === selectedState ? "selected" : "";
            options += `<option value="${key}" ${isSelected}>${value}</option>`
        });
        return options;
    }
    
}
