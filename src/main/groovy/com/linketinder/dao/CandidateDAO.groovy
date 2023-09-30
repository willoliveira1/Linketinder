package com.linketinder.dao

import com.linketinder.database.DatabaseFactory
import com.linketinder.database.DBService
import com.linketinder.domain.candidate.AcademicExperience
import com.linketinder.domain.candidate.Candidate
import com.linketinder.domain.candidate.Certificate
import com.linketinder.domain.candidate.WorkExperience
import com.linketinder.domain.shared.Language
import com.linketinder.domain.shared.Person
import com.linketinder.domain.shared.Skill
import com.linketinder.domain.shared.State
import groovy.sql.Sql

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CandidateDAO {

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()
    CertificateDAO certificateDAO = new CertificateDAO()
    LanguageDAO languageDAO = new LanguageDAO()
    SkillDAO skillDAO = new SkillDAO()
    AcademicExperienceDAO academicExperienceDAO = new AcademicExperienceDAO()
    WorkExperienceDAO workExperienceDAO = new WorkExperienceDAO()

    List<Candidate> getAllCandidates() {
        List<Candidate> candidates = new ArrayList<>()

        try {
            String query = """
                SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cpf
                    FROM candidates AS c,
                        states AS s
                    WHERE c.state_id = s.id
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                Person candidate = new Candidate()
                int candidateId = result.getInt("id")
                candidate.setId(candidateId)
                candidate.setName(result.getString("name"))
                candidate.setEmail(result.getString("email"))
                candidate.setCity(result.getString("city"))
                candidate.setState(State.valueOf(result.getString("state")))
                candidate.setCountry(result.getString("country"))
                candidate.setCep(result.getString("cep"))
                candidate.setDescription(result.getString("description"))
                candidate.setCpf(result.getString("cpf"))

                candidate.setCertificates(certificateDAO.getCertificatesByCandidateId(candidateId))

                candidate.setLanguages(languageDAO.getLanguagesByCandidateId(candidateId))

                candidate.setSkills(skillDAO.getSkillsByCandidateId(candidateId))

                candidate.setAcademicExperiences(academicExperienceDAO.getAcademicExperiencesByCandidateId(candidateId))

                candidate.setWorkExperiences(workExperienceDAO.getWorkExperiencesByCandidateId(candidateId))

                candidates.add(candidate)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return candidates
    }

    Candidate getCandidateById(int id) {
        Person candidate = new Candidate()

        try {
            String query = """
                SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cpf
                    FROM candidates AS c,
                        states AS s
                    WHERE c.state_id = s.id
                    AND c.id = ${id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                candidate.setId(result.getInt("id"))
                candidate.setName(result.getString("name"))
                candidate.setEmail(result.getString("email"))
                candidate.setCity(result.getString("city"))
                candidate.setState(State.valueOf(result.getString("state")))
                candidate.setCountry(result.getString("country"))
                candidate.setCep(result.getString("cep"))
                candidate.setDescription(result.getString("description"))
                candidate.setCpf(result.getString("cpf"))

                candidate.setCertificates(certificateDAO.getCertificatesByCandidateId(id))

                candidate.setLanguages(languageDAO.getLanguagesByCandidateId(id))

                candidate.setSkills(skillDAO.getSkillsByCandidateId(id))

                candidate.setAcademicExperiences(academicExperienceDAO.getAcademicExperiencesByCandidateId(id))

                candidate.setWorkExperiences(workExperienceDAO.getWorkExperiencesByCandidateId(id))
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return candidate
    }

    void insertCandidate(Candidate candidate) {
        try {
            String insertCandidate = "INSERT INTO candidates (name, email, city, state_id, country, cep, " +
                    "description, cpf) VALUES (?,?,?,?,?,?,?,?)"
            PreparedStatement stmt = sql.connection.prepareStatement(insertCandidate,
                    Statement.RETURN_GENERATED_KEYS)
            stmt.setString(1, candidate.name)
            stmt.setString(2, candidate.getEmail())
            stmt.setString(3, candidate.getCity())
            stmt.setString(5, candidate.getCountry())
            stmt.setString(6, candidate.getCep())
            stmt.setString(7, candidate.getDescription())
            stmt.setString(8, candidate.getCpf())

            int stateId = dbService.idFinder("states", "acronym", candidate.getState().toString())
            stmt.setInt(4, stateId)

            stmt.executeUpdate()

            int candidateId = -1
            ResultSet getCandidateId = stmt.getGeneratedKeys()
            while (stmt.getGeneratedKeys().next()) {
                candidateId = getCandidateId.getInt(1)
            }

            if (candidateId != -1) {
                for (Certificate certificate in candidate.certificates) {
                    certificateDAO.insertCertificate(certificate, candidateId)
                }

                for (Language language in candidate.languages) {
                    languageDAO.insertLanguage(language, candidateId)
                }

                for (Skill skill in candidate.skills) {
                    skillDAO.insertSkill(skill, candidateId)
                }

                for (AcademicExperience academicExperience in candidate.academicExperiences) {
                    academicExperienceDAO.insertAcademicExperience(academicExperience, candidateId)
                }

                for (WorkExperience workExperience in candidate.workExperiences) {
                    workExperienceDAO.insertWorkExperience(workExperience, candidateId)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateCandidate(int id, Candidate candidate) {
        try {
            String updateCandidate = """
                UPDATE candidates
                    SET name=?, email=?, city=?, state_id=?, country=?, cep=?, description=?, cpf=?
                    WHERE id=${id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(updateCandidate)
            stmt.setString(1, candidate.name)
            stmt.setString(2, candidate.getEmail())
            stmt.setString(3, candidate.getCity())
            stmt.setString(5, candidate.getCountry())
            stmt.setString(6, candidate.getCep())
            stmt.setString(7, candidate.getDescription())
            stmt.setString(8, candidate.getCpf())

            int stateId = dbService.idFinder("states", "acronym", candidate.getState().toString())
            stmt.setInt(4, stateId)

            stmt.executeUpdate()

            updateCandidateCertificates(id, candidate)
            updateCandidateLanguages(id, candidate)
            updateCandidateSkills(id, candidate)
            updateCandidateAcademicExperiences(id, candidate)
            updateCandidateWorkExperiences(id, candidate)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteCandidateById(int id) {
        Person candidate = new Candidate()

        try {
            String query = "SELECT * FROM candidates WHERE id = ${id};"
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                    candidate.setId(result.getInt("id"))
            }

            if (candidate.id != null) {
                query = "DELETE FROM candidates WHERE id = ${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
            } else {
                println "[Exclusão] Candidato não encontrado."
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateCandidateCertificates(int id, Candidate candidate) {
        List<Integer> certificatesIds = new ArrayList<>()
        sql.eachRow("SELECT id FROM certificates WHERE candidate_id=${id}") {row ->
            certificatesIds << row.getInt("id")
        }

        List<Integer> persistedCertificates = certificatesIds.findAll { !candidate.certificates.contains(it) }
        persistedCertificates.each {certificatesId ->
            certificateDAO.deleteCertificate(certificatesId)
        }

        candidate.certificates.each {certificate ->
            if (persistedCertificates.contains(certificate.id)) {
                certificateDAO.updateCertificate(certificate, id)
            } else {
                certificateDAO.insertCertificate(certificate, id)
            }
        }
    }

    void updateCandidateLanguages(int id, Candidate candidate) {
        List<Integer> languagesIds = new ArrayList<>()
        sql.eachRow("SELECT id FROM candidate_languages WHERE candidate_id=${id}") {row ->
            languagesIds << row.getInt("id")
        }

        List<Integer> persistedLanguages = languagesIds.findAll { !candidate.languages.contains(it) }
        persistedLanguages.each {languagesId ->
            languageDAO.deleteLanguage(languagesId)
        }

        candidate.languages.each {language ->
            if (persistedLanguages.contains(language.id)) {
                languageDAO.updateLanguage(language, id)
            } else {
                languageDAO.insertLanguage(language, id)
            }
        }
    }

    void updateCandidateSkills(int id, Candidate candidate) {
        List<Integer> skillsIds = new ArrayList<>()
        sql.eachRow("SELECT id FROM candidate_skills WHERE candidate_id=${id}") {row ->
            skillsIds << row.getInt("id")
        }

        List<Integer> persistedSkills = skillsIds.findAll { !candidate.skills.contains(it) }
        persistedSkills.each {skillsId ->
            skillDAO.deleteSkill(skillsId)
        }

        candidate.skills.each {skill ->
            if (persistedSkills.contains(skill.id)) {
                skillDAO.updateSkill(skill, id)
            } else {
                skillDAO.insertSkill(skill, id)
            }
        }
    }

    void updateCandidateAcademicExperiences(int id, Candidate candidate) {
        List<Integer> academicExperiencesIds = new ArrayList<>()
        sql.eachRow("SELECT id FROM academic_experiences WHERE candidate_id=${id}") {row ->
            academicExperiencesIds << row.getInt("id")
        }

        List<Integer> persistedAcademicExperiences = academicExperiencesIds.findAll { !candidate.academicExperiences.contains(it) }
        persistedAcademicExperiences.each {academicExperiencesId ->
            academicExperienceDAO.deleteAcademicExperience(academicExperiencesId)
        }

        candidate.academicExperiences.each {academicExperience ->
            if (persistedAcademicExperiences.contains(academicExperience.id)) {
                academicExperienceDAO.updateAcademicExperience(academicExperience, id)
            } else {
                academicExperienceDAO.insertAcademicExperience(academicExperience, id)
            }
        }
    }

    void updateCandidateWorkExperiences(int id, Candidate candidate) {
        List<Integer> workExperiencesIds = new ArrayList<>()
        sql.eachRow("SELECT id FROM work_experiences WHERE candidate_id=${id}") {row ->
            workExperiencesIds << row.getInt("id")
        }

        List<Integer> persistedWorkExperiences = workExperiencesIds.findAll { !candidate.workExperiences.contains(it) }
        persistedWorkExperiences.each {workExperiencesId ->
            workExperienceDAO.deleteWorkExperience(workExperiencesId)
        }

        candidate.workExperiences.each {workExperience ->
            if (persistedWorkExperiences.contains(workExperience.id)) {
                workExperienceDAO.updateWorkExperience(workExperience, id)
            } else {
                workExperienceDAO.insertWorkExperience(workExperience, id)
            }
        }
    }

}
