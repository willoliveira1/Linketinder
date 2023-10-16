package com.linketinder.dao.companydao.interfaces

import com.linketinder.model.shared.Skill

interface IRequiredSkillDAO {

    List<Skill> populateSkills(String query, int id)
    List<Skill> getSkillsByJobVacancyId(int jobVacancyId)
    void insertSkill(Skill skill, int jobVacancyId)
    void updateSkill(Skill skill, int jobVacancyId)
    void deleteSkill(int id)

}
