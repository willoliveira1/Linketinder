package com.linketinder.dao.companydao

import com.linketinder.database.DBService
import com.linketinder.database.DatabaseFactory
import com.linketinder.domain.shared.Skill
import groovy.sql.Sql

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class RequiredSkillDAO {

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()

    List<Skill> getAllSkills() {
        List<Skill> skills = new ArrayList<>()
        try {
            String query = """
                SELECT jbs.id, jbs.job_vacancy_id, s.title
                    FROM job_vacancy_skills AS jbs,
                         skills AS s
                    WHERE jbs.skill_id = s.id
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

    List<Skill> getSkillsByJobVacancyId(int jobVacancyId) {
        List<Skill> skills = new ArrayList<>()
        try {
            String query = """
                SELECT jbs.id, jbs.job_vacancy_id, s.title
                    FROM job_vacancy_skills AS jbs,
                         skills AS s,
                         job_vacancies AS jb
                    WHERE jbs.skill_id = s.id
                    AND jb.id = jbs.job_vacancy_id
                    AND jb.id = ${jobVacancyId}
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
                SELECT jbs.id, jbs.job_vacancy_id, s.title
                    FROM job_vacancy_skills AS jbs,
                         skills AS s,
                         job_vacancies AS jb
                    WHERE jbs.skill_id = s.id
                    AND jb.id = jbs.job_vacancy_id
                    AND jbs.id = ${id}
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

    void insertSkill(Skill skill, int jobVacancyId) {
        try {
            String insertSkill = "INSERT INTO job_vacancy_skills (job_vacancy_id, skill_id) VALUES (?,?)"
            PreparedStatement stmt = sql.connection.prepareStatement(insertSkill, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, jobVacancyId)

            int skillId = dbService.idFinder("skills", "title", skill.getTitle())
            stmt.setInt(2, skillId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateSkill(Skill skill, int jobVacancyId) {
        try {
            String updateLanguage = """
                UPDATE job_vacancy_skills
                    SET job_vacancy_id=${jobVacancyId}, skill_id=?
                    WHERE id=${skill.id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(updateLanguage)
            int skillId = dbService.idFinder("skills", "title", skill.getTitle())
            stmt.setInt(1, skillId)

            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteSkill(int id) {
        Skill skill = new Skill()
        try {
            String query = "SELECT * FROM job_vacancy_skills WHERE id = ${id};"
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                skill.setId(result.getInt("id"))
            }

            if (skill.id != null) {
                query = "DELETE FROM job_vacancy_skills WHERE id = ${id};"
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

        return skill
    }

}
