package com.linketinder.dao.candidatedao

import com.linketinder.dao.candidatedao.interfaces.ILanguageDAO
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.IDBService
import com.linketinder.database.interfaces.IConnection
import com.linketinder.model.candidate.Language
import com.linketinder.model.shared.Proficiency
import com.linketinder.util.ErrorMessages
import com.linketinder.util.NotFoundMessages
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class LanguageDAO implements ILanguageDAO {

    private final String GET_LANGUAGES_BY_CANDIDATE_ID = "SELECT cl.id, l.name, p.title FROM candidates AS c, candidate_languages AS cl, languages AS l, proficiences AS p WHERE c.id = cl.candidate_id AND l.id = cl.language_id AND p.id = cl.proficiency_id AND c.id=?"
    private final String GET_LANGUAGES_BY_ID = "SELECT * FROM candidate_languages WHERE id=?"
    private final String INSERT_CANDIDATE_LANGUAGE = "INSERT INTO candidate_languages (candidate_id, language_id, proficiency_id) VALUES (?,?,?)"
    private final String UPDATE_CANDIDATE_LANGUAGE = "UPDATE candidate_languages SET candidate_id=?, language_id=?, proficiency_id=? WHERE id=?"
    private final String DELETE_CANDIDATE_LANGUAGE = "DELETE FROM candidate_languages WHERE id=?"
    private final String INSERT_LANGUAGE = "INSERT INTO languages (name) VALUES (?)"
    private final String GET_LANGUAGE_BY_NAME = "SELECT * FROM languages WHERE name=?"

    IConnection connection
    IDBService dbService
    Sql sql = connection.instance()

    LanguageDAO(IDBService dbService, IConnection connection) {
        this.dbService = dbService
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
            languages = populateLanguages(GET_LANGUAGES_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return languages
    }

    private PreparedStatement setCandidateLanguageStatement(PreparedStatement stmt, Language language, int candidateId) {
        int languageId = dbService.idFinder("languages", "name", language.getName())
        int proficiencyId = dbService.idFinder("proficiences", "title",
                language.getProficiency().toString())
        stmt.setInt(1, candidateId)
        stmt.setInt(2, languageId)
        stmt.setInt(3, proficiencyId)
        return stmt
    }

    private void isNewLanguage(Language language) {
        PreparedStatement stmt = sql.connection.prepareStatement(GET_LANGUAGE_BY_NAME)
        stmt.setString(1, language.name)
        ResultSet result = stmt.executeQuery()

        if (result.next()) {
            return
        }
        stmt = sql.connection.prepareStatement(INSERT_LANGUAGE, Statement.RETURN_GENERATED_KEYS)
        stmt.setString(1, language.name)
        stmt.executeUpdate()
    }

    void insertLanguage(Language language, int candidateId) {
        try {
            this.isNewLanguage(language)

            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_CANDIDATE_LANGUAGE, Statement.RETURN_GENERATED_KEYS)
            stmt = this.setCandidateLanguageStatement(stmt, language, candidateId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateLanguage(Language language, int candidateId) {
        try {
            this.isNewLanguage(language)

            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_CANDIDATE_LANGUAGE)
            stmt = this.setCandidateLanguageStatement(stmt, language, candidateId)
            int languageId = dbService.idFinder("languages", "name", language.getName())
            stmt.setInt(4, languageId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteLanguage(int id) {
        Language language = new Language()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(GET_LANGUAGES_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                language.setId(result.getInt("id"))
            }

            if (language.id != null) {
                stmt = sql.connection.prepareStatement(DELETE_CANDIDATE_LANGUAGE)
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
