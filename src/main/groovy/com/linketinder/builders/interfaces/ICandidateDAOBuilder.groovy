package com.linketinder.builders.interfaces

import com.linketinder.dao.candidatedao.interfaces.*

interface ICandidateDAOBuilder extends IBaseDAOBuilder<ICandidateDAO> {

    IBaseDAOBuilder<ICandidateDAO> withAcademicExperienceDAO(IAcademicExperienceDAO academicExperienceDAO)
    IBaseDAOBuilder<ICandidateDAO> withCandidateSkillDAO(ICandidateSkillDAO candidateSkillDAO)
    IBaseDAOBuilder<ICandidateDAO> withLanguageDAO(ILanguageDAO languageDAO)
    IBaseDAOBuilder<ICandidateDAO> withWorkExperienceDAO(IWorkExperienceDAO workExperienceDAO)
    IBaseDAOBuilder<ICandidateDAO> withCertificateDAO(ICertificateDAO certificateDAO)

}
