package com.linketinder.dao.candidatedao

import com.linketinder.dao.candidatedao.interfaces.ICandidateSkillDAO
import com.linketinder.dao.candidatedao.queries.CandidateSkillQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.IDBService
import com.linketinder.database.interfaces.IConnection
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

    IConnection connection
    IDBService dbService
    Sql sql = connection.instance()

    CandidateSkillDAO(IDBService dbService, IConnection connection) {
        this.dbService = dbService
        this.connection = connection
    }

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
            skills = this.populateSkills(CandidateSkillQueries.GET_SKILLS_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
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

    private void isNewSkill(Skill skill) {
        PreparedStatement stmt = sql.connection.prepareStatement(CandidateSkillQueries.GET_SKILL_BY_TITLE)
        stmt.setString(1, skill.title)
        ResultSet result = stmt.executeQuery()

        if (result.next()) {
            return
        }
        stmt = sql.connection.prepareStatement(CandidateSkillQueries.INSERT_SKILL, Statement.RETURN_GENERATED_KEYS)
        stmt.setString(1, skill.title)
        stmt.executeUpdate()
    }

    void insertSkill(Skill skill, int candidateId) {
        try {
            this.isNewSkill(skill)

            PreparedStatement stmt = sql.connection.prepareStatement(
                    CandidateSkillQueries.INSERT_CANDIDATE_SKILL, Statement.RETURN_GENERATED_KEYS)
            stmt = this.setCandidateSkillStatement(stmt, skill, candidateId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateSkill(Skill skill, int candidateId) {
        try {
            this.isNewSkill(skill)

            PreparedStatement stmt = sql.connection.prepareStatement(CandidateSkillQueries.UPDATE_CANDIDATE_SKILL)
            stmt = this.setCandidateSkillStatement(stmt, skill, candidateId)
            stmt.setInt(4, skill.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteSkill(int id) {
        Skill skill = new Skill()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CandidateSkillQueries.GET_SKILL_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                skill.setId(result.getInt("id"))
            }

            if (skill.id != null) {
                stmt = sql.connection.prepareStatement(CandidateSkillQueries.DELETE_CANDIDATE_SKILL)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.SKILL
    }

}
