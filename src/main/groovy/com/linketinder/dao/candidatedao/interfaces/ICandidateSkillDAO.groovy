package com.linketinder.dao.candidatedao.interfaces

import com.linketinder.model.shared.Skill

interface ICandidateSkillDAO {

    List<Skill> getSkillsByCandidateId(int candidateId)
    void insertSkill(Skill skill, int candidateId)
    void updateSkill(Skill skill, int candidateId)
    void deleteSkill(int id)

}
