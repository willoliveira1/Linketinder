package com.linketinder.dao.candidatedao

import com.linketinder.dao.candidatedao.interfaces.ILanguageDAO
import com.linketinder.dao.candidatedao.queries.LanguageQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.*
import com.linketinder.model.candidate.Language
import com.linketinder.model.shared.Proficiency
import com.linketinder.util.*
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class LanguageDAO implements ILanguageDAO {

    IConnection connection
    Sql sql = connection.instance()

    LanguageDAO(IConnection connection) {
        this.connection = connection
    }

    private List<Language> populateLanguages(String query, int candidateId) {
        List<Language> languages = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, candidateId)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            Language language = new Language()
            language.setId(result.getInt("id"))
            language.setName(result.getString("name"))
            language.setProficiency(Proficiency.valueOf(result.getString("title")))
            languages.add(language)
        }
        return languages
    }

    List<Language> getLanguagesByCandidateId(int candidateId) {
        List<Language> languages = new ArrayList<>()
        try {
            languages = populateLanguages(LanguageQueries.GET_LANGUAGES_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return languages
    }

    private int getLanguageIdByName(String languageTitle) {
        PreparedStatement stmt = sql.connection.prepareStatement(LanguageQueries.GET_LANGUAGE_ID_BY_NAME)
        stmt.setString(1, languageTitle)
        return QueryHelper.idFinder(stmt)
    }

    private int getProficiencyIdByTitle(String proficiencyTitle) {
        PreparedStatement stmt = sql.connection.prepareStatement(LanguageQueries.GET_PROFICIENCY_ID_BY_TITLE)
        stmt.setString(1, proficiencyTitle)
        return QueryHelper.idFinder(stmt)
    }

    private PreparedStatement setCandidateLanguageStatement(PreparedStatement stmt, Language language, int candidateId) {
        int languageId = this.getLanguageIdByName(language.getName())
        int proficiencyId = this.getProficiencyIdByTitle(language.getProficiency().toString())
        stmt.setInt(1, candidateId)
        stmt.setInt(2, languageId)
        stmt.setInt(3, proficiencyId)
        return stmt
    }

    private void isNewLanguage(Language language) {
        PreparedStatement stmt = sql.connection.prepareStatement(LanguageQueries.GET_LANGUAGE_BY_NAME)
        stmt.setString(1, language.name)
        ResultSet result = stmt.executeQuery()

        if (result.next()) {
            return
        }
        stmt = sql.connection.prepareStatement(LanguageQueries.INSERT_LANGUAGE, Statement.RETURN_GENERATED_KEYS)
        stmt.setString(1, language.name)
        stmt.executeUpdate()
    }

    void insertLanguage(Language language, int candidateId) {
        try {
            this.isNewLanguage(language)

            PreparedStatement stmt = sql.connection.prepareStatement(LanguageQueries.INSERT_CANDIDATE_LANGUAGE,
                    Statement.RETURN_GENERATED_KEYS)
            stmt = this.setCandidateLanguageStatement(stmt, language, candidateId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateLanguage(Language language, int candidateId) {
        try {
            this.isNewLanguage(language)

            PreparedStatement stmt = sql.connection.prepareStatement(LanguageQueries.UPDATE_CANDIDATE_LANGUAGE)
            stmt = this.setCandidateLanguageStatement(stmt, language, candidateId)
            int languageId = this.getLanguageIdByName(language.getName())
            stmt.setInt(4, languageId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteLanguage(int id) {
        Language language = new Language()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(LanguageQueries.GET_LANGUAGES_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                language.setId(result.getInt("id"))
            }

            if (language.id != null) {
                stmt = sql.connection.prepareStatement(LanguageQueries.DELETE_CANDIDATE_LANGUAGE)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.LANGUAGE
    }

}
