package com.linketinder.dao.companydao

import com.linketinder.database.DatabaseFactory
import com.linketinder.database.DBService
import com.linketinder.model.shared.Skill
import com.linketinder.util.ErrorText
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class RequiredSkillDAO {

    static final String QUERY_GET_SKILLS_BY_JOB_VACANCY_ID = "SELECT jbs.id, jbs.job_vacancy_id, s.title FROM job_vacancy_skills AS jbs, skills AS s, job_vacancies AS jb WHERE jbs.skill_id = s.id AND jb.id = jbs.job_vacancy_id AND jb.id=?"
    static final String QUERY_GET_SKILL_BY_ID = "SELECT * FROM job_vacancy_skills WHERE id=?"
    static final String INSERT_SKILL = "INSERT INTO job_vacancy_skills (job_vacancy_id, skill_id) VALUES (?,?)"
    static final String UPDATE_SKILL = "UPDATE job_vacancy_skills SET job_vacancy_id=?, skill_id=? WHERE id=?"
    static final String DELETE_SKILL_BY_ID = "DELETE FROM job_vacancy_skills WHERE id=?"

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()

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
            skills = populateSkills(QUERY_GET_SKILLS_BY_JOB_VACANCY_ID, jobVacancyId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
        return skills
    }

    void insertSkill(Skill skill, int jobVacancyId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_SKILL, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, jobVacancyId)

            int skillId = dbService.idFinder("skills", "title", skill.getTitle())
            stmt.setInt(2, skillId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
    }

    void updateSkill(Skill skill, int jobVacancyId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_SKILL)
            int skillId = dbService.idFinder("skills", "title", skill.getTitle())
            stmt.setInt(1, jobVacancyId)
            stmt.setInt(2, skillId)
            stmt.setInt(3, skill.id)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
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
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
        println "Habilidade não encontrada."
    }

}
