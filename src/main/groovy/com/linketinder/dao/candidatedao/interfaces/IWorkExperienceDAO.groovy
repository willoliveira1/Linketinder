package com.linketinder.dao.candidatedao.interfaces

import com.linketinder.model.candidate.WorkExperience

interface IWorkExperienceDAO {

    List<WorkExperience> getWorkExperiencesByCandidateId(int candidateId)
    void insertWorkExperience(WorkExperience workExperience, int candidateId)
    void updateWorkExperience(WorkExperience workExperience, int candidateId)
    void deleteWorkExperience(int id)

}
