import $ from "jquery";
import { Chart, CategoryScale, LinearScale, BarController, BarElement, Tooltip, Title } from 'chart.js';

import candidatesData from "../../../../src/main/resources/candidatos.json";
import companiesData from "../../../../src/main/resources/empresas.json";
import jobsData from "../../../../src/main/resources/vagas.json";

import CandidateService from "../../services/candidate-service";
import Candidate from "../../models/candidate/candidate";
import CompanyService from "../../services/company-service";
import JobVacancyService from "../../services/job-vacancy-service";
import JobVacancy from "../../models/job-vacancy/job-vacancy";
import Company from "../../models/company/company";

export default class AdminGraph {

    candidateService: CandidateService = new CandidateService;
    companyService: CompanyService = new CompanyService;
    jobVacancyService: JobVacancyService = new JobVacancyService;

    candidatesVsCompaniesVsJobs: Map<string, number> = new Map<string, number>();

    populatePersons(): void {
        let candidates: Candidate[] = candidatesData;
        let companies: Company[] = companiesData;
        let jobs: JobVacancy[] = jobsData;

        this.candidatesVsCompaniesVsJobs.set("Candidatos", candidates.length);
        this.candidatesVsCompaniesVsJobs.set("Empresas", companies.length);
        this.candidatesVsCompaniesVsJobs.set("Vagas", jobs.length);
    }

    generateGraph(): void {
        this.populatePersons();

        Chart.register(CategoryScale, LinearScale, BarController, BarElement, Tooltip, Title);

        document.querySelector("#container")?.remove();

        $("main").append(`
            <div id="container"">
                <canvas id="chart"></canvas>
            </div>
        `);
        
        const canvas: HTMLCanvasElement = document.getElementById("chart") as HTMLCanvasElement;
        canvas.width = 400;
        canvas.height = 400;

        new Chart(
            canvas!,
            {
                type: "bar",
                data: {
                    labels: [...this.candidatesVsCompaniesVsJobs.keys()],
                    datasets: [
                        {
                            label: "Quantidade",
                            data: [...this.candidatesVsCompaniesVsJobs.values()],
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(255, 159, 64, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(153, 102, 255, 0.2)'
                            ],
                            borderColor: [
                            'rgb(255, 99, 132)',
                            'rgb(255, 159, 64)',
                            'rgb(75, 192, 192)',
                            'rgb(54, 162, 235)',
                            'rgb(153, 102, 255)'
                            ],
                            borderWidth: 1
                        }
                    ]
                },
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: "Candidatos por Habilidade",
                        },
                        tooltip: {
                            enabled: true,
                            yAlign: "bottom",
                        }
                    },
                    scales: {
                        y: {
                            ticks: {
                                stepSize: 1,
                            },
                            beginAtZero: true
                        }
                    }
                }
            }
        );
    }

}
