package com.linketinder.view.interfaces

import com.linketinder.model.shared.Skill

interface IJobVacanciesView {

    void getAllJobVacancies()
    void getAllJobVacanciesByCompanyId()
    void getJobVacancyById()
    List<Skill> addRequiredSkills()
    void addJobVacancy()
    void updateJobVacancy()
    void removeJobVacancy()

}
