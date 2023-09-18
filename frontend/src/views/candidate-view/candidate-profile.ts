import $ from "jquery";

import { State } from "../../models/enums/state";
import { CourseStatus } from "../../models/enums/course-status";
import { ContractType } from "../../models/enums/contract-type";
import { LocationType } from "../../models/enums/location-type";
import { Proficiency } from "../../models/enums/proficiency";
import Candidate from "../../models/candidate/candidate";
import CandidateService from "../../services/candidate-service";
import AcademicExperience from "../../models/candidate/academic-experience";
import WorkExperience from "../../models/candidate/work-experience";
import Language from "../../models/shared/language";
import Skill from "../../models/shared/skill";
import Certificate from "../../models/candidate/certificate";

export default class CandidateProfile {

    generateForm(): void {
        document.querySelector("#container")?.remove();

        $("main").append(`
            <div id="container">
                <form id="table"></form>
            </div>
        `);

        this.generateBasicInformation();
        this.generateAcademicInformation();
        this.generateWorkInformation();
        this.generateLanguageInformation();
        this.generateSkillInformation();
        this.generateCertificateInformation();
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
                        <label>CPF:</label>
                        <input type="email" id="cpf">
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
                    <label>Sobre mim:</label>
                    <input type="email" id="description">
                </div>
            </div>
        `);
    }

    generateAcademicInformation(): void {
        $("#table").append(`
            <div id="academic-informations" class="information">
                <button type="button" id="add-academic-button">Adicionar Experiência Acadêmica</button>
            </div>`
        );
        this.generateAcademicButton();
    }

    generateWorkInformation(): void {
        $("#table").append(`
            <div id="work-informations" class="information">
                <button type="button" id="add-work-button">Adicionar Experiência Profissional</button>
            </div>
        `);
        this.generateWorkButton();
    }

    generateLanguageInformation(): void {
        $("#table").append(`
            <div id="language-informations" class="information">
                <button type="button" id="add-language-button">Adicionar Idioma</button>
            </div>
        `); 
        this.generateLanguageButton();
    }

    generateSkillInformation(): void {
        $("#table").append(`
            <div id="skill-informations" class="information">
                <button type="button" id="add-skill-button">Adicionar Habilidade</button>
            </div>
        `);
        this.generateSkillButton();
    }

    generateCertificateInformation(): void {
        $("#table").append(`
            <div id="certification-informations" class="information">
                <button type="button" id="add-certificate-button">Adicionar Certificado</button>
            </div>
        `);
        this.generateCertificateButton();
    }

    generateAcademicButton(): void {
        const self = this;
        document.getElementById("add-academic-button")?.addEventListener("click", function(): void {
            $("#academic-informations").append(`
                <div class="academic-information">
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Instituição:</label>
                            <input type="text" class="educationalInstitution">
                        </div>
                        <div class="form-field">
                            <label>Diploma:</label>
                            <input type="text" class="degreeType">
                        </div>
                    </div>
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Curso:</label>
                            <input type="text" class="fieldOfStudy">
                        </div>
                        <div class="form-field">
                            <label>Status:</label>
                            <div class="line">
                                <select class="courseStatus">
                                    ${self.generateStatus()}
                                </select>
                                <span class="material-symbols-outlined delete-btn">delete</span>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        });

        document.querySelectorAll(".academic-information").forEach(academic => {
            academic.querySelectorAll(".delete-btn").forEach(btn => {
                btn.addEventListener("click", () => {
                    academic.remove();
                });
            })
        });
    }

    generateWorkButton(): void {
        const self = this;
        document.getElementById("add-work-button")?.addEventListener("click", function(): void {
            $("#work-informations").append(`
                <div class="work-information information">
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Cargo:</label>
                            <input type="text" class="title">
                        </div>
                        <div class="form-field">
                            <label>Empresa:</label>
                            <input type="text" class="companyName">
                        </div>
                    </div>
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Tipo de contrato:</label>
                            <select class="contractType">
                                ${self.generateContractTypes()}
                            </select>
                        </div>
                        <div class="form-field">
                            <label>Regime de trabalho:</label>
                            <select class="contractType">
                                ${self.generateLocationTypes()}
                            </select>
                        </div>
                    </div>
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Cidade:</label>
                            <input type="text" class="city">
                        </div>
                        <div class="form-field">
                            <label>Estado:</label>
                            <select class="state">
                                ${self.generateStates()}
                            </select>
                        </div>
                    </div>
                    <div class="form-field">
                        <label>Sobre:</label>
                        <input type="text" class="description">
                    </div>
                    <div class="form-field line">
                        <label class="work-label">Trabalho Atual:</label>
                        <input type="checkbox" class="currently-work">
                        <span class="material-symbols-outlined work-btn">delete</span>
                    </div>
                </div>
            `);
        });

        document.querySelectorAll(".work-information").forEach(work => {
            work.querySelectorAll(".delete-btn").forEach(btn => {
                btn.addEventListener("click", () => {
                    work.remove();
                });
            })
        });
    }

    generateLanguageButton(): void {
        const self = this;
        document.getElementById("add-language-button")?.addEventListener("click", function(): void {
            $("#language-informations").append(`
                <div class="language-information information">
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Idioma:</label>
                            <input type="text" class="language-name">
                        </div>
                        <div class="form-field">
                            <label>Tipo de contrato:</label>
                            <div class="line">
                                <select class="language-proficiency">
                                    ${self.generateProficiencies()}
                                </select>
                                <span class="material-symbols-outlined delete-btn">delete</span>
                            </div>
                        </div>
                    </div>
                </div>
            `); 
        });

        document.querySelectorAll(".language-information").forEach(language => {
            language.querySelectorAll(".delete-btn").forEach(btn => {
                btn.addEventListener("click", () => {
                    language.remove();
                });
            })
        });
    }

    generateSkillButton(): void {
        const self = this;
        document.getElementById("add-skill-button")?.addEventListener("click", function(): void {
            $("#skill-informations").append(`
                <div class="skill-information information">
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Habilidade:</label>
                            <input type="text" class="skill-name">
                        </div>
                        <div class="form-field">
                            <label>Tipo de contrato:</label>
                            <div class="line">
                                <select class="skill-proficiency">
                                    ${self.generateProficiencies()}
                                </select>
                                <span class="material-symbols-outlined delete-btn">delete</span>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        });

        document.querySelectorAll(".skill-information").forEach(skill => {
            skill.querySelectorAll(".delete-btn").forEach(btn => {
                btn.addEventListener("click", () => {
                    skill.remove();
                });
            })
        });
    }

    generateCertificateButton(): void {
        document.getElementById("add-certificate-button")?.addEventListener("click", function(): void {
            $("#certification-informations").append(`
                <div class="certification-information information">
                    <div class="form-list">
                        <div class="form-field">
                            <label>Certificação:</label>
                            <input type="text" class="certificate-name">
                        </div>
                        <div class="form-field">
                            <label>Duração:</label>
                            <div class="line">
                                <input type="text" class="duration">
                                <span class="material-symbols-outlined delete-btn">delete</span>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        });

        document.querySelectorAll(".certification-information").forEach(certification => {
            certification.querySelectorAll(".delete-btn").forEach(btn => {
                btn.addEventListener("click", () => {
                    certification.remove();
                });
            })
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
            let candidateService: CandidateService = new CandidateService();
            let candidate = candidateService.generateCandidate();

            candidate.name = <string> $("#name").val();
            candidate.email = <string> $("#email").val();
            candidate.city = <string> $("#city").val();
            candidate.state = <State> $("#states :selected").val();
            candidate.cep = <string> $("#cep").val();
            candidate.description = <string> $("#description").val();            
            candidate.cpf = <string> $("#cpf").val();

            let academicExperiences: AcademicExperience[] = [];
            let academicId: number = 1;
            let academicInfos = Array.from(document.querySelectorAll(".academic-information"));
            academicInfos.forEach(function(info) {
                let educationalInstitution = (info.querySelector(".educationalInstitution") as HTMLInputElement).value;
                let degreeType = (info.querySelector(".degreeType") as HTMLInputElement).value;
                let fieldOfStudy = (info.querySelector(".fieldOfStudy") as HTMLInputElement).value;
                let status = (info.querySelector(".courseStatus") as HTMLSelectElement).value as CourseStatus;

                academicExperiences.push(new AcademicExperience(academicId++, educationalInstitution, degreeType, 
                    fieldOfStudy, status));
            });
            candidate.academicExperiences = academicExperiences;

            let workExperiences: WorkExperience[] = [];
            let workId: number = 1;
            let workInfos = Array.from(document.querySelectorAll(".work-information"));
            workInfos.forEach(function(info) {
                let title = (info.querySelector(".title") as HTMLInputElement).value;
                let companyName = (info.querySelector(".companyName") as HTMLInputElement).value;
                let contractType = (info.querySelector(".contractType") as HTMLSelectElement).value as ContractType;
                let locationType = (info.querySelector(".locationType") as HTMLSelectElement).value as LocationType;
                let city = (info.querySelector(".city") as HTMLInputElement).value;
                let state = (info.querySelector(".state") as HTMLSelectElement).value as State;
                let currentlyWork = (info.querySelector(".currently-work") as HTMLInputElement).checked;
                let description = (info.querySelector(".description") as HTMLInputElement).value;

                workExperiences.push(new WorkExperience(workId++, title, companyName, contractType, locationType, city, 
                    state, currentlyWork, description));
            });
            candidate.workExperiences = workExperiences;

            let languages: Language[] = [];
            let languagesId: number = 1;
            let languageInfos = Array.from(document.querySelectorAll(".language-information"));
            languageInfos.forEach(function(info) {
                let name = (info.querySelector(".language-name") as HTMLInputElement).value;
                let proficiency = (info.querySelector(".language-proficiency") as HTMLSelectElement).value as Proficiency;

                languages.push(new Language(languagesId++, name, proficiency));
            });
            candidate.languages = languages;

            let skills: Skill[] = [];
            let skillsId: number = 1;
            let skillInfos = Array.from(document.querySelectorAll(".skill-information"));
            skillInfos.forEach(function(info) {
                let title = (info.querySelector(".skill-name") as HTMLInputElement).value;
                let proficiency = (info.querySelector(".skill-proficiency") as HTMLSelectElement).value as Proficiency;

                skills.push(new Skill(skillsId++, title, proficiency));
            });
            candidate.skills = skills;

            let certificates: Certificate[] = [];
            let certificatesId: number = 1;
            let certificateInfos = Array.from(document.querySelectorAll(".certification-information"));
            certificateInfos.forEach(function(info) {
                let title = (info.querySelector(".certificate-name") as HTMLInputElement).value;
                let duration = (info.querySelector(".duration") as HTMLSelectElement).value as Proficiency;

                certificates.push(new Certificate(certificatesId++, title, duration));
            });
            candidate.certificates = certificates;

            candidateService.updateCandidate(candidate);

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

    generateStatus(): string {
        let options!: string;
        Object.values(CourseStatus).forEach(status => {
            options += `<option value="${status}">${status}</option>`
        });
        return options;
    }

    generateContractTypes(): string {
        let options!: string;
        Object.values(ContractType).forEach(contractType => {
            options += `<option value="${contractType}">${contractType}</option>`
        });
        return options;
    }

    generateLocationTypes(): string {
        let options!: string;
        Object.values(LocationType).forEach(locationType => {
            options += `<option value="${locationType}">${locationType}</option>`
        });
        return options;
    }

    generateProficiencies(): string {
        let options!: string;
        Object.values(Proficiency).forEach(proficiency => {
            options += `<option value="${proficiency}">${proficiency}</option>`
        });
        return options;
    }

    populateForm(candidate: Candidate): void {
        document.querySelector("#container")?.remove();

        $("main").append(`
            <div id="container">
                <form id="table"></form>
            </div>
        `);

        this.populateBasicInformation(candidate);
        this.populateAcademicInformation(candidate);
        this.populateWorkInformation(candidate);
        this.populateLanguageInformation(candidate);
        this.populateSkillInformation(candidate);
        this.populateCertificateInformation(candidate);
        this.generateSubmitButtons();
    }

    populateBasicInformation(candidate: Candidate): void {
        $("#table").append(`
            <div id="basic-information" class="information">
                <div class="form-field form-text">
                    <label>Nome:</label>
                    <input type="text" id="name" value="${candidate.name}">
                </div>
                <div class="form-list">
                    <div class="form-field">
                        <label>Email:</label>
                        <input type="email" id="email" value="${candidate.email}">
                    </div>
                    <div class="form-field">
                        <label>CPF:</label>
                        <input type="text" id="cpf" value="${candidate.cpf}">
                    </div>
                </div>
                <div class="form-list">
                    <div class="form-field city">
                        <label>Cidade:</label>
                        <input type="text" id="city" value="${candidate.city}">
                    </div>
                    <div class="form-field state">
                        <label>Estado:</label>
                        <select name="state" id="states">
                            ${this.populateState(candidate.state)}
                        </select>
                    </div>
                    <div class="form-field cep">
                        <label>CEP:</label>
                        <input type="text" id="cep" value="${candidate.cep}">
                    </div>
                </div>
                <div class="form-field form-text">
                    <label>Sobre mim:</label>
                    <input type="text" id="description" value="${candidate.description}">
                </div>
            </div>
        `);
    }

    populateAcademicInformation(candidate: Candidate): void {
        const self = this;

        $("#table").append(`
            <div id="academic-informations" class="information">
                <button type="button" id="add-academic-button">Adicionar Experiência Acadêmica</button>
            </div>`
        );

        candidate.academicExperiences.forEach(experience => {
            $("#academic-informations").append(`
                <div class="academic-information">
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Instituição:</label>
                            <input type="text" class="educationalInstitution" value="${experience.educationalInstitution}">
                        </div>
                        <div class="form-field">
                            <label>Diploma:</label>
                            <input type="text" class="degreeType" value="${experience.degreeType}">
                        </div>
                    </div>
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Curso:</label>
                            <input type="text" class="fieldOfStudy" value="${experience.fieldOfStudy}">
                        </div>
                        <div class="form-field">
                            <label>Status:</label>
                            <div class="line">
                                <select class="courseStatus">
                                    ${self.populateStatus(experience.status)}
                                </select>
                                <span class="material-symbols-outlined delete-btn">delete</span>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        });

        this.generateAcademicButton();
    }

    populateWorkInformation(candidate: Candidate): void {
        const self = this;

        $("#table").append(`
            <div id="work-informations" class="information">
                <button type="button" id="add-work-button">Adicionar Experiência Profissional</button>
            </div>
        `);

        candidate.workExperiences.forEach(work => {
            $("#work-informations").append(`
                <div class="work-information information">
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Cargo:</label>
                            <input type="text" class="title" value="${work.title}">
                        </div>
                        <div class="form-field">
                            <label>Empresa:</label>
                            <input type="text" class="companyName" value="${work.companyName}">
                        </div>
                    </div>
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Tipo de contrato:</label>
                            <select class="contractType">
                                ${self.populateContractType(work.contractType)}
                            </select>
                        </div>
                        <div class="form-field">
                            <label>Regime de trabalho:</label>
                            <select class="locationType">
                                ${self.populateLocationType(work.locationType)}
                            </select>
                        </div>
                    </div>
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Cidade:</label>
                            <input type="text" class="city" value="${work.city}">
                        </div>
                        <div class="form-field">
                            <label>Estado:</label>
                            <select class="state">
                                ${self.populateState(work.state)}
                            </select>
                        </div>
                    </div>
                    <div class="form-field">
                        <label>Sobre:</label>
                        <input type="text" class="description" value="${work.description}">
                    </div>
                    <div class="form-field line">
                        <label class="work-label">Trabalho Atual:</label>
                        <input type="checkbox" class="currently-work" ${work.currentlyWork ? "checked" : ""}>
                        <span class="material-symbols-outlined work-btn">delete</span>
                    </div>
                </div>
            `);
        });

        this.generateWorkButton();
    }

    populateLanguageInformation(candidate: Candidate): void {
        const self = this; 

        $("#table").append(`
            <div id="language-informations" class="information">
                <button type="button" id="add-language-button">Adicionar Idioma</button>
            </div>
        `); 

        candidate.languages.forEach(language => {
            $("#language-informations").append(`
                <div class="language-information information">
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Idioma:</label>
                            <input type="text" class="language-name" value="${language.name}">
                        </div>
                        <div class="form-field">
                            <label>Tipo de contrato:</label>
                            <div class="line">
                                <select class="language-proficiency">
                                    ${self.populateProficiencies(language.proficiency)}
                                </select>
                                <span class="material-symbols-outlined delete-btn">delete</span>
                            </div>
                        </div>
                    </div>
                </div>
            `); 
        });
        
        this.generateLanguageButton();
    }

    populateSkillInformation(candidate: Candidate): void {
        const self = this;

        $("#table").append(`
            <div id="skill-informations" class="information">
                <button type="button" id="add-skill-button">Adicionar Habilidade</button>
            </div>
        `);

        candidate.skills.forEach(skill => {
            $("#skill-informations").append(`
                <div class="skill-information information">
                    <div class="form-list form-text">
                        <div class="form-field">
                            <label>Habilidade:</label>
                            <input type="text" class="skill-name" value="${skill.title}">
                        </div>
                        <div class="form-field">
                            <label>Tipo de contrato:</label>
                            <div class="line">
                                <select class="skill-proficiency">
                                    ${self.populateProficiencies(skill.proficiency)}
                                </select>
                                <span class="material-symbols-outlined delete-btn">delete</span>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        });

        this.generateSkillButton();
    }

    populateCertificateInformation(candidate: Candidate): void {
        const self = this;
        
        $("#table").append(`
            <div id="certification-informations" class="information">
                <button type="button" id="add-certificate-button">Adicionar Certificado</button>
            </div>
        `);

        candidate.certificates.forEach(certificate => {
            $("#certification-informations").append(`
                <div class="certification-information information">
                    <div class="form-list">
                        <div class="form-field">
                            <label>Certificação:</label>
                            <input type="text" class="certificate-name" value="${certificate.title}">
                        </div>
                        <div class="form-field">
                            <label>Duração:</label>
                            <div class="line">
                                <input type="text" class="duration" value="${certificate.duration}">
                                <span class="material-symbols-outlined delete-btn">delete</span>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        });

        this.generateCertificateButton();
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

    populateStatus(selectedStatus: CourseStatus): string {
        let options!: string;
        Object.entries(CourseStatus).forEach(status => {
            const [key, value] = status;
            const isSelected = key == selectedStatus ? "selected" : "";
            options += `<option value="${key}" ${isSelected}>${value}</option>`
        });
        return options;
    }

    populateContractType(selectedType: ContractType): string {
        let options!: string;
        Object.entries(ContractType).forEach(contractType => {
            const [key, value] = contractType;
            const isSelected = key == selectedType ? "selected" : "";
            options += `<option value="${key}" ${isSelected}>${value}</option>`
        });
        return options;
    }

    populateLocationType(selectedType: LocationType): string {
        let options!: string;
        Object.entries(LocationType).forEach(locationType => {
            const [key, value] = locationType;
            const isSelected = key == selectedType ? "selected" : "";
            options += `<option value="${key}" ${isSelected}>${value}</option>`
        });
        return options;
    }

    populateProficiencies(selectedProficiency: Proficiency): string {
        let options!: string;
        Object.entries(Proficiency).forEach(proficiency => {
            const [key, value] = proficiency;
            const isSelected = key == selectedProficiency ? "selected" : "";
            options += `<option value="${key}" ${isSelected}>${value}</option>`
        });
        return options;
    }
    
}
