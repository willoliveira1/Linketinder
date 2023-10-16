package com.linketinder.view.interfaces

import com.linketinder.model.candidate.*
import com.linketinder.model.shared.Skill

interface ICandidatesView {

    void getAllCandidates()
    void getCandidateById()
    List<AcademicExperience> addAcademicExperiences()
    List<WorkExperience> addWorkExperiences()
    List<Language> addLanguages()
    List<Skill> addSkills()
    List<Certificate> addCertificates()
    void addCandidate()
    void updateCandidate()
    void removeCandidate()

}
