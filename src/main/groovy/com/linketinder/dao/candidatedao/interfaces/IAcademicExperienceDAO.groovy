package com.linketinder.dao.candidatedao.interfaces

import com.linketinder.model.candidate.AcademicExperience

interface IAcademicExperienceDAO {

    List<AcademicExperience> getAcademicExperiencesByCandidateId(int candidateId)
    void insertAcademicExperience(AcademicExperience academicExperience, int candidateId)
    void updateAcademicExperience(AcademicExperience academicExperience, int candidateId)
    void deleteAcademicExperience(int id)

}
