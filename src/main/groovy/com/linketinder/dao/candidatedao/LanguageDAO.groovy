package com.linketinder.dao.candidatedao

import com.linketinder.database.DBService
import com.linketinder.database.DatabaseFactory
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

class LanguageDAO {

    private final String QUERY_GET_LANGUAGES_BY_CANDIDATE_ID = "SELECT cl.id, l.name, p.title FROM candidates AS c, candidate_languages AS cl, languages AS l, proficiences AS p WHERE c.id = cl.candidate_id AND l.id = cl.language_id AND p.id = cl.proficiency_id AND c.id=?"
    private final String QUERY_LANGUAGES_BY_ID = "SELECT * FROM candidate_languages WHERE id=?"
    private final String INSERT_LANGUAGE = "INSERT INTO candidate_languages (candidate_id, language_id, proficiency_id) VALUES (?,?,?)"
    private final String UPDATE_LANGUAGE = "UPDATE candidate_languages SET candidate_id=?, language_id=?, proficiency_id=? WHERE id=?"
    private final String DELETE_LANGUAGE = "DELETE FROM candidate_languages WHERE id=?"

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()

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
            languages = populateLanguages(QUERY_GET_LANGUAGES_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
        return languages
    }

    void insertLanguage(Language language, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_LANGUAGE, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, candidateId)

            int languageId = dbService.idFinder("languages", "name", language.getName())
            stmt.setInt(2, languageId)

            int proficiencyId = dbService.idFinder("proficiences", "title",
                    language.getProficiency().toString())
            stmt.setInt(3, proficiencyId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
    }

    void updateLanguage(Language language, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_LANGUAGE)
            int languageId = dbService.idFinder("languages", "name", language.getName())
            stmt.setInt(1, candidateId)
            stmt.setInt(2, languageId)

            int proficiencyId = dbService.idFinder("proficiences", "title",
                    language.getProficiency().toString())
            stmt.setInt(3, proficiencyId)
            stmt.setInt(4, languageId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
    }

    void deleteLanguage(int id) {
        Language language = new Language()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(QUERY_LANGUAGES_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                language.setId(result.getInt("id"))
            }

            if (language.id != null) {
                stmt = sql.connection.prepareStatement(DELETE_LANGUAGE)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DBMSG, e)
        }
        println NotFoundMessages.LANGUAGE
    }

}
