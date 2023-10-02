package com.linketinder.dao.candidatedao

import com.linketinder.database.DBService
import com.linketinder.database.DatabaseFactory
import com.linketinder.domain.candidate.Language
import com.linketinder.domain.shared.Proficiency

import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class LanguageDAO {

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()

    List<Language> getAllLanguages() {
        List<Language> languages = new ArrayList<>()
        try {
            String query = """
                SELECT cl.id, l.name, p.title
                    FROM candidates AS c,
                         candidate_languages AS cl,
                         languages AS l,
                         proficiences AS p
                    WHERE c.id = cl.candidate_id
                    AND l.id = cl.language_id
                    AND p.id = cl.proficiency_id
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                Language language = populateLanguage(result)
                languages.add(language)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return languages
    }

    List<Language> getLanguagesByCandidateId(int candidateId) {
        List<Language> languages = new ArrayList<>()
        try {
            String query = """
                SELECT cl.id, l.name, p.title
                    FROM candidates AS c,
                         candidate_languages AS cl,
                         languages AS l,
                         proficiences AS p
                    WHERE c.id = cl.candidate_id
                    AND l.id = cl.language_id
                    AND p.id = cl.proficiency_id
                    AND c.id = ${candidateId}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                Language language = populateLanguage(result)
                languages.add(language)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return languages
    }

    Language getLanguageById(int id) {
        Language language = new Language()
        try {
            String query = """
                SELECT cl.id, l.name, p.title
                    FROM candidates AS c,
                         candidate_languages AS cl,
                         languages AS l,
                         proficiences AS p
                    WHERE c.id = cl.candidate_id
                    AND l.id = cl.language_id
                    AND p.id = cl.proficiency_id
                    AND cl.id = ${id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                language = populateLanguage(result)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return language
    }

    void insertLanguage(Language language, int candidateId) {
        try {
            String insertLanguage = "INSERT INTO candidate_languages (candidate_id, language_id, proficiency_id) " +
                    "VALUES (?,?,?)"
            PreparedStatement stmt = sql.connection.prepareStatement(insertLanguage, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, candidateId)

            int languageId = dbService.idFinder("languages", "name", language.getName())
            stmt.setInt(2, languageId)

            int proficiencyId = dbService.idFinder("proficiences", "title",
                    language.getProficiency().toString())
            stmt.setInt(3, proficiencyId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateLanguage(Language language, int candidateId) {
        try {
            String updateLanguage = """
                UPDATE candidate_languages 
                    SET candidate_id=${candidateId}, language_id=?, proficiency_id=?
                    WHERE id=${language.id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(updateLanguage)
            int languageId = dbService.idFinder("languages", "name", language.getName())
            stmt.setInt(1, languageId)

            int proficiencyId = dbService.idFinder("proficiences", "title",
                    language.getProficiency().toString())
            stmt.setInt(2, proficiencyId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteLanguage(int id) {
        Language language = new Language()
        try {
            String query = "SELECT * FROM candidate_languages WHERE id = ${id};"
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                language.setId(result.getInt("id"))
            }

            if (language.id != null) {
                query = "DELETE FROM candidate_languages WHERE id = ${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
            } else {
                println "[Exclusão] Idioma não encontrado."
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    Language populateLanguage(ResultSet result) {
        Language language = new Language()
        language.setId(result.getInt("id"))
        language.setName(result.getString("name"))
        language.setProficiency(Proficiency.valueOf(result.getString("title")))

        return language
    }

}
