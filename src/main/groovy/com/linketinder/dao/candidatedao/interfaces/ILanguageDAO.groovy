package com.linketinder.dao.candidatedao.interfaces

import com.linketinder.model.candidate.Language

interface ILanguageDAO {

    List<Language> getLanguagesByCandidateId(int candidateId)
    void insertLanguage(Language language, int candidateId)
    void updateLanguage(Language language, int candidateId)
    void deleteLanguage(int id)

}
