package com.linketinder.dao.candidatedao

import com.linketinder.dao.candidatedao.interfaces.ICandidateSkillDAO
import com.linketinder.database.DBService
import com.linketinder.database.DatabaseFactory
import com.linketinder.model.shared.Proficiency
import com.linketinder.model.shared.Skill
import com.linketinder.util.ErrorMessages
import com.linketinder.util.NotFoundMessages
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CandidateSkillDAO implements ICandidateSkillDAO {

    private final String GET_SKILLS_BY_CANDIDATE_ID = "SELECT cs.id, s.title, p.title AS proficiency_title FROM candidates AS c, candidate_skills AS cs, skills AS s, proficiences AS p WHERE c.id = cs.candidate_id AND s.id = cs.skill_id AND p.id = cs.proficiency_id AND c.id=?"
    private final String GET_SKILL_BY_ID = "SELECT * FROM candidate_skills WHERE id=?"
    private final String INSERT_SKILL = "INSERT INTO candidate_skills (candidate_id, skill_id, proficiency_id) VALUES (?,?,?)"
    private final String UPDATE_SKILL = "UPDATE candidate_skills SET candidate_id=?, skill_id=?, proficiency_id=? WHERE id=?"
    private final String DELETE_SKILL = "DELETE FROM candidate_skills WHERE id=?"

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()

    private List<Skill> populateSkills(String query, int candidateId) {
        List<Skill> skills = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, candidateId)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            Skill skill = new Skill()
            skill.setId(result.getInt("id"))
            skill.setTitle(result.getString("title"))
            skill.setProficiency(Proficiency.valueOf(result.getString("proficiency_title")))
            skills.add(skill)
        }
        return skills
    }

    List<Skill> getSkillsByCandidateId(int candidateId) {
        List<Skill> skills = new ArrayList<>()
        try {
            skills = this.populateSkills(GET_SKILLS_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return skills
    }

    private PreparedStatement setCandidateSkillStatement(PreparedStatement stmt, Skill skill, int candidateId) {
        int skillId = dbService.idFinder("skills", "title", skill.getTitle())
        int proficiencyId = dbService.idFinder("proficiences", "title", skill.getProficiency().toString())
        stmt.setInt(1, candidateId)
        stmt.setInt(2, skillId)
        stmt.setInt(3, proficiencyId)
        return stmt
    }

    void insertSkill(Skill skill, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_SKILL, Statement.RETURN_GENERATED_KEYS)
            stmt = this.setCandidateSkillStatement(stmt, skill, candidateId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateSkill(Skill skill, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_SKILL)
            stmt = this.setCandidateSkillStatement(stmt, skill, candidateId)
            stmt.setInt(4, skill.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteSkill(int id) {
        Skill skill = new Skill()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(GET_SKILL_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                skill.setId(result.getInt("id"))
            }

            if (skill.id != null) {
                stmt = sql.connection.prepareStatement(DELETE_SKILL)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.SKILL
    }

}
