package com.linketinder.dao.candidatedao

import com.linketinder.database.DBService
import com.linketinder.database.DatabaseFactory
import com.linketinder.domain.shared.Proficiency
import com.linketinder.domain.shared.Skill

import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CandidateSkillDAO {

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()

    List<Skill> getAllSkills() {
        List<Skill> skills = new ArrayList<>()
        try {
            String query = """
                SELECT cs.id, s.title, p.title AS proficiency_title
                    FROM candidates AS c,
                         candidate_skills AS cs,
                         skills AS s,
                         proficiences AS p
                    WHERE c.id = cs.candidate_id
                    AND s.id = cs.skill_id
                    AND p.id = cs.proficiency_id
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                Skill skill = populateSkill(result)
                skills.add(skill)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return skills
    }

    List<Skill> getSkillsByCandidateId(int candidateId) {
        List<Skill> skills = new ArrayList<>()
        try {
            String query = """
                SELECT cs.id, s.title, p.title AS proficiency_title
                    FROM candidates AS c,
                         candidate_skills AS cs,
                         skills AS s,
                         proficiences AS p
                    WHERE c.id = cs.candidate_id
                    AND s.id = cs.skill_id
                    AND p.id = cs.proficiency_id
                    AND c.id = ${candidateId}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                Skill skill = populateSkill(result)
                skills.add(skill)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return skills
    }

    Skill getSkillById(int id) {
        Skill skill = new Skill()
        try {
            String query = """
                SELECT cs.id, s.title, p.title AS proficiency_title
                    FROM candidates AS c,
                         candidate_skills AS cs,
                         skills AS s,
                         proficiences AS p
                    WHERE c.id = cs.candidate_id
                    AND s.id = cs.skill_id
                    AND p.id = cs.proficiency_id
                    AND cs.id = ${id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                skill = populateSkill(result)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return skill
    }

    void insertSkill(Skill skill, int candidateId) {
        try {
            String insertSkill = "INSERT INTO candidate_skills (candidate_id, skill_id, proficiency_id) VALUES (?,?,?)"
            PreparedStatement stmt = sql.connection.prepareStatement(insertSkill, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, candidateId)

            int skillId = dbService.idFinder("skills", "title", skill.getTitle())
            stmt.setInt(2, skillId)

            int proficiencyId = dbService.idFinder("proficiences", "title", skill.getProficiency().toString())
            stmt.setInt(3, proficiencyId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateSkill(Skill skill, int candidateId) {
        try {
            String updateLanguage = """
                UPDATE candidate_skills 
                    SET candidate_id=${candidateId}, skill_id=?, proficiency_id=?
                    WHERE id=${skill.id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(updateLanguage)
            int skillId = dbService.idFinder("skills", "title", skill.getTitle())
            stmt.setInt(1, skillId)

            int proficiencyId = dbService.idFinder("proficiences", "title", skill.getProficiency().toString())
            stmt.setInt(2, proficiencyId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteSkill(int id) {
        Skill skill = new Skill()
        try {
            String query = "SELECT * FROM candidate_skills WHERE id = ${id};"
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                skill.setId(result.getInt("id"))
            }

            if (skill.id != null) {
                query = "DELETE FROM candidate_skills WHERE id = ${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
            } else {
                println "[Exclusão] Habilidade não encontrada."
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    Skill populateSkill(ResultSet result) {
        Skill skill = new Skill()
        skill.setId(result.getInt("id"))
        skill.setTitle(result.getString("title"))
        skill.setProficiency(Proficiency.valueOf(result.getString("proficiency_title")))

        return skill
    }

}