package com.linketinder.dao.companydao

import com.linketinder.dao.companydao.interfaces.IRequiredSkillDAO
import com.linketinder.dao.companydao.queries.RequiredSkillQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.*
import com.linketinder.model.shared.Skill
import com.linketinder.util.*
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class RequiredSkillDAO implements IRequiredSkillDAO {

    IConnection connection
    Sql sql = connection.instance()

    RequiredSkillDAO(IConnection connection) {
        this.connection = connection
    }

    List<Skill> populateSkills(String query, int id) {
        List<Skill> skills = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            Skill skill = new Skill()
            skill.setId(result.getInt("id"))
            skill.setTitle(result.getString("title"))
            skills.add(skill)
        }
        return skills
    }

    List<Skill> getSkillsByJobVacancyId(int jobVacancyId) {
        List<Skill> skills = new ArrayList<>()
        try {
            skills = this.populateSkills(RequiredSkillQueries.GET_SKILLS_BY_JOB_VACANCY_ID, jobVacancyId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
        return skills
    }

    private int getSkillIdByTitle(String skillTitle) {
        PreparedStatement stmt = sql.connection.prepareStatement(RequiredSkillQueries.GET_SKILL_ID_BY_TITLE)
        stmt.setString(1, skillTitle)
        return QueryHelper.idFinder(stmt)
    }

    private PreparedStatement setSkillStatement(PreparedStatement stmt, Skill skill, int jobVacancyId) {
        int skillId = this.getSkillIdByTitle(skill.getTitle())
        stmt.setInt(1, jobVacancyId)
        stmt.setInt(2, skillId)
        return stmt
    }

    private void isNewSkill(Skill skill) {
        PreparedStatement stmt = sql.connection.prepareStatement(RequiredSkillQueries.GET_SKILL_BY_TITLE)
        stmt.setString(1, skill.title)
        ResultSet result = stmt.executeQuery()

        if (result.next()) {
            return
        }
        stmt = sql.connection.prepareStatement(RequiredSkillQueries.INSERT_SKILL, Statement.RETURN_GENERATED_KEYS)
        stmt.setString(1, skill.title)
        stmt.executeUpdate()
    }

    void insertSkill(Skill skill, int jobVacancyId) {
        try {
            this.isNewSkill(skill)

            PreparedStatement stmt = sql.connection.prepareStatement(RequiredSkillQueries.INSERT_JOB_VACANCY_SKILL,
                    Statement.RETURN_GENERATED_KEYS)
            stmt = this.setSkillStatement(stmt, skill, jobVacancyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
    }

    void updateSkill(Skill skill, int jobVacancyId) {
        try {
            this.isNewSkill(skill)

            PreparedStatement stmt = sql.connection.prepareStatement(RequiredSkillQueries.UPDATE_JOB_VACANCY_SKILL)
            stmt = this.setSkillStatement(stmt, skill, jobVacancyId)
            stmt.setInt(3, skill.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
    }

    void deleteSkill(int id) {
        Skill skill = new Skill()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(RequiredSkillQueries.GET_SKILL_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                skill.setId(result.getInt("id"))
            }

            if (skill.id != null) {
                stmt = sql.connection.prepareStatement(RequiredSkillQueries.DELETE_JOB_VACANCY_SKILL_BY_ID)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
        println NotFoundMessages.SKILL
    }

}
