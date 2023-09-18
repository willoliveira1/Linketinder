import $ from "jquery";
import { Chart, CategoryScale, LinearScale, BarController, BarElement, Tooltip, Title } from 'chart.js';

import data from "../../../../src/main/resources/candidatos.json";

import CandidateService from "../../services/candidate-service";
import Candidate from "../../models/candidate/candidate";

export default class CompanyGraph {

    candidateService: CandidateService = new CandidateService;

    candidatesPerSkill: Map<string, number> = new Map<string, number>();

    populateSkills(): void {
        let candidates: Candidate[] = data;

        candidates.forEach(candidate => {
            candidate.skills.forEach(skill => {
                if (this.candidatesPerSkill.has(skill.title)) {
                    let value: number = this.candidatesPerSkill.get(skill.title)! + 1;
                    this.candidatesPerSkill.set(skill.title, value);
                } else {
                    this.candidatesPerSkill.set(skill.title, 1);
                }
            });
        });
    }

    generateGraphs(): void {
        this.populateSkills();

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
                    labels: [...this.candidatesPerSkill.keys()],
                    datasets: [
                        {
                            label: "Quantidade",
                            data: [...this.candidatesPerSkill.values()],
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
