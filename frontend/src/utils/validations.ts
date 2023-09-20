import { regexCep, regexCity, regexCnpj, regexCpf, regexDescription, regexEmail, regexText } from "./regex";

export default class Validations {

    static validateBasicCandidateData(): void {
        const nameInput = document.getElementById("name") as HTMLInputElement;
        nameInput.addEventListener("input", () => {
            if (regexText.test(nameInput.value)) {
                nameInput.classList.remove("error");
            } else {
                nameInput.classList.add("error");
            }
        });

        const emailInput = document.getElementById("email") as HTMLInputElement;
        emailInput.addEventListener("input", () => {
            if (regexEmail.test(emailInput.value)) {
                emailInput.classList.remove("error");
            } else {
                emailInput.classList.add("error");
            }
        });

        const cpfInput = document.getElementById("cpf") as HTMLInputElement;
        cpfInput.addEventListener("input", () => {
            if (regexCpf.test(cpfInput.value)) {
                cpfInput.classList.remove("error");
            } else {
                cpfInput.classList.add("error");
            }
        });

        const cityInput = document.getElementById("city") as HTMLInputElement;
        cityInput.addEventListener("input", () => {
            if (regexText.test(cityInput.value)) {
                cityInput.classList.remove("error");
            } else {
                cityInput.classList.add("error");
            }
        });

        const cepInput = document.getElementById("cep") as HTMLInputElement;
        cepInput.addEventListener("input", () => {
            if (regexCep.test(cepInput.value)) {
                cepInput.classList.remove("error");
            } else {
                cepInput.classList.add("error");
            }
        });

        const descriptionInput = document.getElementById("description") as HTMLInputElement;
        descriptionInput.addEventListener("input", () => {
            if (regexDescription.test(descriptionInput.value)) {
                descriptionInput.classList.remove("error");
            } else {
                descriptionInput.classList.add("error");
            }
        });

    }
    
    static validateAcademicInfos(): void {
        const educationalInstitutionInputs = document.querySelectorAll(".educationalInstitution");
        educationalInstitutionInputs.forEach(educationalInstitutionInput => {
            educationalInstitutionInput.addEventListener("input", () => {
                const input = educationalInstitutionInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    educationalInstitutionInput.classList.remove("error");
                } else {
                    educationalInstitutionInput.classList.add("error");
                }
            });
        });

        const degreeTypeInputs = document.querySelectorAll(".degreeType");
        degreeTypeInputs.forEach(degreeTypeInput => {
            degreeTypeInput.addEventListener("input", () => {
                const input = degreeTypeInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    degreeTypeInput.classList.remove("error");
                } else {
                    degreeTypeInput.classList.add("error");
                }
            });
        });

        const fieldOfStudyInputs = document.querySelectorAll(".fieldOfStudy");
        fieldOfStudyInputs.forEach(fieldOfStudyInput => {
            fieldOfStudyInput.addEventListener("input", () => {
                const input = fieldOfStudyInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    fieldOfStudyInput.classList.remove("error");
                } else {
                    fieldOfStudyInput.classList.add("error");
                }
            });
        });
    }
      
    static validateWorkInfos(): void {
        const titleInputs = document.querySelectorAll(".title");
        titleInputs.forEach(titleInput => {
            titleInput.addEventListener("input", () => {
                const input = titleInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    titleInput.classList.remove("error");
                } else {
                    titleInput.classList.add("error");
                }
            });
        });

        const companyNameInputs = document.querySelectorAll(".companyName");
        companyNameInputs.forEach(companyNameInput => {
            companyNameInput.addEventListener("input", () => {
                const input = companyNameInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    companyNameInput.classList.remove("error");
                } else {
                    companyNameInput.classList.add("error");
                }
            });
        });

        const cityInputs = document.querySelectorAll(".work-city");
        cityInputs.forEach(cityInput => {
            cityInput.addEventListener("input", () => {
                const input = cityInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    cityInput.classList.remove("error");
                } else {
                    cityInput.classList.add("error");
                }
            });
        });

        const descriptionInputs = document.querySelectorAll(".description");
        descriptionInputs.forEach(descriptionInput => {
            descriptionInput.addEventListener("input", () => {
                const input = descriptionInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    descriptionInput.classList.remove("error");
                } else {
                    descriptionInput.classList.add("error");
                }
            });
        });
    }
   
    static validateLanguageInfos(): void {
        const nameInputs = document.querySelectorAll(".language-name");
        nameInputs.forEach(nameInput => {
            nameInput.addEventListener("input", () => {
                const input = nameInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    nameInput.classList.remove("error");
                } else {
                    nameInput.classList.add("error");
                }
            });
        });
    }
    
    static validateSkillInfos(): void {
        const skillInputs = document.querySelectorAll(".skill-name");
        skillInputs.forEach(skillInput => {
            skillInput.addEventListener("input", () => {
                const input = skillInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    skillInput.classList.remove("error");
                } else {
                    skillInput.classList.add("error");
                }
            });
        });
    }
           
    static validateCertificateInfos(): void {
        const titleInputs = document.querySelectorAll(".certificate-name");
        titleInputs.forEach(titleInput => {
            titleInput.addEventListener("input", () => {
                const input = titleInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    titleInput.classList.remove("error");
                } else {
                    titleInput.classList.add("error");
                }
            });
        });

        const durationInputs = document.querySelectorAll(".duration");
        durationInputs.forEach(durationInput => {
            durationInput.addEventListener("input", () => {
                const input = durationInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    durationInput.classList.remove("error");
                } else {
                    durationInput.classList.add("error");
                }
            });
        });
    }

    static validateBasicCompanyData(): void {
        const nameInput = document.getElementById("name") as HTMLInputElement;
        nameInput.addEventListener("input", () => {
            if (regexText.test(nameInput.value)) {
                nameInput.classList.remove("error");
            } else {
                nameInput.classList.add("error");
            }
        });

        const emailInput = document.getElementById("email") as HTMLInputElement;
        emailInput.addEventListener("input", () => {
            if (regexEmail.test(emailInput.value)) {
                emailInput.classList.remove("error");
            } else {
                emailInput.classList.add("error");
            }
        });

        const cnpjInput = document.getElementById("cnpj") as HTMLInputElement;
        cnpjInput.addEventListener("input", () => {
            if (regexCnpj.test(cnpjInput.value)) {
                cnpjInput.classList.remove("error");
            } else {
                cnpjInput.classList.add("error");
            }
        });

        const cityInput = document.getElementById("city") as HTMLInputElement;
        cityInput.addEventListener("input", () => {
            if (regexCity.test(cityInput.value)) {
                cityInput.classList.remove("error");
            } else {
                cityInput.classList.add("error");
            }
        });

        const cepInput = document.getElementById("cep") as HTMLInputElement;
        cepInput.addEventListener("input", () => {
            if (regexCep.test(cepInput.value)) {
                cepInput.classList.remove("error");
            } else {
                cepInput.classList.add("error");
            }
        });

        const descriptionInput = document.getElementById("description") as HTMLInputElement;
        descriptionInput.addEventListener("input", () => {
            if (regexDescription.test(descriptionInput.value)) {
                descriptionInput.classList.remove("error");
            } else {
                descriptionInput.classList.add("error");
            }
        });
    }

    static validateBenefits(): void {
        const benefitInputs = document.querySelectorAll(".benefit-name");
        benefitInputs.forEach(benefitInput => {
            benefitInput.addEventListener("input", () => {
                const input = benefitInput as HTMLInputElement;
    
                if (regexText.test(input.value)) {
                    benefitInput.classList.remove("error");
                } else {
                    benefitInput.classList.add("error");
                }
            });
        });
    }

}
