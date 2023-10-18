package com.linketinder.dao.companydao

import com.linketinder.dao.companydao.interfaces.IRequiredSkillDAO
import com.linketinder.database.DatabaseConnection
import com.linketinder.database.interfaces.IDBService
import com.linketinder.database.interfaces.IDatabaseConnection
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

class RequiredSkillDAO implements IRequiredSkillDAO {

    private final String QUERY_GET_SKILLS_BY_JOB_VACANCY_ID = "SELECT jbs.id, jbs.job_vacancy_id, s.title FROM job_vacancy_skills AS jbs, skills AS s, job_vacancies AS jb WHERE jbs.skill_id = s.id AND jb.id = jbs.job_vacancy_id AND jb.id=?"
    private final String QUERY_GET_SKILL_BY_ID = "SELECT * FROM job_vacancy_skills WHERE id=?"
    private final String INSERT_JOB_VACANCY_SKILL = "INSERT INTO job_vacancy_skills (job_vacancy_id, skill_id) VALUES (?,?)"
    private final String UPDATE_JOB_VACANCY_SKILL = "UPDATE job_vacancy_skills SET job_vacancy_id=?, skill_id=? WHERE id=?"
    private final String DELETE_JOB_VACANCY_SKILL_BY_ID = "DELETE FROM job_vacancy_skills WHERE id=?"
    private final String INSERT_SKILL = "INSERT INTO skills (title) VALUES (?)"
    private final String GET_SKILL_BY_TITLE = "SELECT * FROM skills WHERE title=?"

    IDatabaseConnection databaseFactory
    IDBService dbService
    Sql sql = databaseFactory.instance()

    RequiredSkillDAO(IDBService dbService, IDatabaseConnection databaseFactory) {
        this.dbService = dbService
        this.databaseFactory = databaseFactory
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
            skills = this.populateSkills(QUERY_GET_SKILLS_BY_JOB_VACANCY_ID, jobVacancyId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return skills
    }

    private PreparedStatement setSkillStatement(PreparedStatement stmt, Skill skill, int jobVacancyId) {
        int skillId = dbService.idFinder("skills", "title", skill.getTitle())
        stmt.setInt(1, jobVacancyId)
        stmt.setInt(2, skillId)
        return stmt
    }

    private boolean isNewSkill(Skill skill) {
        PreparedStatement stmt = sql.connection.prepareStatement(GET_SKILL_BY_TITLE)
        stmt.setString(1, skill.title)
        ResultSet result = stmt.executeQuery()
        if (result.next()) {
            return false
        }
        return true
    }

    void insertSkill(Skill skill, int jobVacancyId) {
        try {
            if (isNewSkill(skill)) {
                PreparedStatement stmt = sql.connection.prepareStatement(INSERT_SKILL, Statement.RETURN_GENERATED_KEYS)
                stmt.setString(1, skill.title)
                stmt.executeUpdate()
            }

            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_JOB_VACANCY_SKILL, Statement.RETURN_GENERATED_KEYS)
            stmt = this.setSkillStatement(stmt, skill, jobVacancyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateSkill(Skill skill, int jobVacancyId) {
        try {
            if (isNewSkill(skill)) {
                PreparedStatement stmt = sql.connection.prepareStatement(INSERT_SKILL, Statement.RETURN_GENERATED_KEYS)
                stmt.setString(1, skill.title)
                stmt.executeUpdate()
            }

            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_JOB_VACANCY_SKILL)
            stmt = this.setSkillStatement(stmt, skill, jobVacancyId)
            stmt.setInt(3, skill.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void deleteSkill(int id) {
        Skill skill = new Skill()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(QUERY_GET_SKILL_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                skill.setId(result.getInt("id"))
            }

            if (skill.id != null) {
                stmt = sql.connection.prepareStatement(DELETE_JOB_VACANCY_SKILL_BY_ID)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        println NotFoundMessages.SKILL
    }

}
