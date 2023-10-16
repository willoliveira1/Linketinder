package com.linketinder.dao.companydao

import com.linketinder.dao.companydao.interfaces.IRequiredSkillDAO
import com.linketinder.database.DatabaseFactory
import com.linketinder.database.DBService
import com.linketinder.database.interfaces.IDBService
import com.linketinder.database.interfaces.IDatabaseFactory
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
    private final String INSERT_SKILL = "INSERT INTO job_vacancy_skills (job_vacancy_id, skill_id) VALUES (?,?)"
    private final String UPDATE_SKILL = "UPDATE job_vacancy_skills SET job_vacancy_id=?, skill_id=? WHERE id=?"
    private final String DELETE_SKILL_BY_ID = "DELETE FROM job_vacancy_skills WHERE id=?"

    IDatabaseFactory databaseFactory
    IDBService dbService
    Sql sql = databaseFactory.instance()

    RequiredSkillDAO(IDBService dbService, IDatabaseFactory databaseFactory) {
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
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return skills
    }

    private PreparedStatement setSkillStatement(PreparedStatement stmt, Skill skill, int jobVacancyId) {
        int skillId = dbService.idFinder("skills", "title", skill.getTitle())
        stmt.setInt(1, jobVacancyId)
        stmt.setInt(2, skillId)
        return stmt
    }

    void insertSkill(Skill skill, int jobVacancyId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_SKILL, Statement.RETURN_GENERATED_KEYS)
            stmt = this.setSkillStatement(stmt, skill, jobVacancyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    void updateSkill(Skill skill, int jobVacancyId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_SKILL)
            stmt = this.setSkillStatement(stmt, skill, jobVacancyId)
            stmt.setInt(3, skill.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
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
                stmt = sql.connection.prepareStatement(DELETE_SKILL_BY_ID)
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
