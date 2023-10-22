package com.linketinder.context.builders.interfaces

import com.linketinder.dao.candidatedao.interfaces.*

interface ICandidateDAOBuilder extends IDAOBuilder<ICandidateDAO> {

    IDAOBuilder<ICandidateDAO> withAcademicExperienceDAO(IAcademicExperienceDAO academicExperienceDAO)
    IDAOBuilder<ICandidateDAO> withCandidateSkillDAO(ICandidateSkillDAO candidateSkillDAO)
    IDAOBuilder<ICandidateDAO> withLanguageDAO(ILanguageDAO languageDAO)
    IDAOBuilder<ICandidateDAO> withWorkExperienceDAO(IWorkExperienceDAO workExperienceDAO)
    IDAOBuilder<ICandidateDAO> withCertificateDAO(ICertificateDAO certificateDAO)

}
