import $ from "jquery";

import CandidateService from "./services/candidate-service";
import CandidateProfile from "./views/candidate-view/candidate-profile";
import CandidateJobList from "./views/candidate-view/candidate-job-list";
import CompanyProfile from "./views/company-view/company-profile";
import CompanyService from "./services/company-service";
import CompanyCandidateList from "./views/company-view/company-candidate-list";
import CompanyGraph from "./views/company-view/company-graph";
import AdminGraph from "./views/adm-view/admin-graph";

var candidateService: CandidateService = new CandidateService();
var candidate = candidateService.generateCandidate();

var companyService: CompanyService = new CompanyService();
var company = companyService.generateCompany();

var view: AdminGraph = new AdminGraph();
view.generateGraph();

document.getElementById("linketinder")?.addEventListener("click", function(): void {
    document.querySelector(".menu")?.remove();

    view.generateGraph();
});

let candidateMenu = document.getElementById("candidate-menu");
candidateMenu?.addEventListener("click", function(): void {
    document.querySelector(".menu")?.remove();
    
    $("header").append(`
        <div class=menu>
            <ul>
                <li id="candidate-profile">Cadastro</li>
                <li id="job-vacancies">Lista de Vagas</li>
            </ul>
        </div>
    `);

    let view: CandidateJobList = new CandidateJobList();
    view.generateTable();

    document.getElementById("candidate-profile")?.addEventListener("click", function():void {
        let view: CandidateProfile = new CandidateProfile();
        view.populateForm(candidate);
    });

    document.getElementById("job-vacancies")?.addEventListener("click", function():void {
        let view: CandidateJobList = new CandidateJobList();
        view.generateTable();
    });

});

let companyMenu = document.getElementById("company-menu");
companyMenu?.addEventListener("click", function(): void {
    document.querySelector(".menu")?.remove();
    
    $("header").append(`
        <div class=menu>
            <ul>
                <li id="company-home">Home</li>
                <li id="company-profile">Cadastro</li>
                <li id="job-vacancy-candidates">Lista de Candidatos</li>
            </ul>
        </div>
    `);
    
    let view: CompanyGraph = new CompanyGraph();
    view.generateGraphs();

    document.getElementById("company-home")?.addEventListener("click", function():void {
        let view: CompanyGraph = new CompanyGraph();
    view.generateGraphs();
    });

    document.getElementById("company-profile")?.addEventListener("click", function():void {
        let view: CompanyProfile = new CompanyProfile();
        view.populateForm(company);
    });

    document.getElementById("job-vacancy-candidates")?.addEventListener("click", function():void {
        let view: CompanyCandidateList = new CompanyCandidateList();
        view.generateTable();
    });

});
