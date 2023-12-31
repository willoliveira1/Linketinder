package com.linketinder.dao.candidatedao

import com.linketinder.dao.candidatedao.interfaces.*
import com.linketinder.dao.candidatedao.queries.CandidateQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.*
import com.linketinder.model.candidate.*
import com.linketinder.model.shared.*
import com.linketinder.util.*
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CandidateDAO implements ICandidateDAO {

    IConnection connection
    ICertificateDAO certificateDAO
    ILanguageDAO languageDAO
    ICandidateSkillDAO skillDAO
    IAcademicExperienceDAO academicExperienceDAO
    IWorkExperienceDAO workExperienceDAO
    Sql sql = connection.instance()

    CandidateDAO(IConnection connection, ICertificateDAO certificateDAO,
                 ILanguageDAO languageDAO, ICandidateSkillDAO skillDAO, IAcademicExperienceDAO academicExperienceDAO,
                 IWorkExperienceDAO workExperienceDAO) {
        this.connection = connection
        this.certificateDAO = certificateDAO
        this.languageDAO = languageDAO
        this.skillDAO = skillDAO
        this.academicExperienceDAO = academicExperienceDAO
        this.workExperienceDAO = workExperienceDAO
    }

    private Candidate createCandidate(ResultSet result) {
        Person candidate = new Candidate()
        candidate.setId(result.getInt("id"))
        candidate.setName(result.getString("name"))
        candidate.setEmail(result.getString("email"))
        candidate.setCity(result.getString("city"))
        candidate.setState(State.valueOf(result.getString("state")))
        candidate.setCountry(result.getString("country"))
        candidate.setCep(result.getString("cep"))
        candidate.setDescription(result.getString("description"))
        candidate.setCpf(result.getString("cpf"))
        candidate.setCertificates(certificateDAO.getCertificatesByCandidateId(candidate.id))
        candidate.setLanguages(languageDAO.getLanguagesByCandidateId(candidate.id))
        candidate.setSkills(skillDAO.getSkillsByCandidateId(candidate.id))
        candidate.setAcademicExperiences(academicExperienceDAO.getAcademicExperiencesByCandidateId(candidate.id))
        candidate.setWorkExperiences(workExperienceDAO.getWorkExperiencesByCandidateId(candidate.id))
        return candidate
    }

    private List<Candidate> populateCandidates(String query) {
        List<Candidate> candidates = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            Person candidate = this.createCandidate(result)
            candidates.add(candidate)
        }
        return candidates
    }

    private Candidate populateCandidate(String query, int id) {
        Person candidate = new Candidate()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            candidate = this.createCandidate(result)
        }
        return candidate
    }

    List<Candidate> getAllCandidates() {
        List<Candidate> candidates = new ArrayList<>()
        try {
            candidates = this.populateCandidates(CandidateQueries.GET_ALL_CANDIDATES)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
        return candidates
    }

    Candidate getCandidateById(int id) {
        Person candidate = new Candidate()
        try {
            candidate = this.populateCandidate(CandidateQueries.GET_CANDIDATE_BY_ID, id)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
        return candidate
    }

    private int getStateIdByTitle(String stateTitle) {
        PreparedStatement stmt = sql.connection.prepareStatement(CandidateQueries.GET_STATE_ID_BY_TITLE)
        stmt.setString(1, stateTitle)
        return QueryHelper.idFinder(stmt)
    }

    private PreparedStatement setCandidateStatement(PreparedStatement stmt, Candidate candidate) {
        int stateId = this.getStateIdByTitle(candidate.getState().toString())
        stmt.setString(1, candidate.name)
        stmt.setString(2, candidate.getEmail())
        stmt.setString(3, candidate.getCity())
        stmt.setInt(4, stateId)
        stmt.setString(5, candidate.getCountry())
        stmt.setString(6, candidate.getCep())
        stmt.setString(7, candidate.getDescription())
        stmt.setString(8, candidate.getCpf())
        return stmt
    }

    private void insertCertificates(Candidate candidate) {
        for (Certificate certificate in candidate.certificates) {
            certificateDAO.insertCertificate(certificate, candidate.id)
        }
    }

    private void insertLanguages(Candidate candidate) {
        for (Language language in candidate.languages) {
            languageDAO.insertLanguage(language, candidate.id)
        }
    }

    private void insertSkills(Candidate candidate) {
        for (Skill skill in candidate.skills) {
            skillDAO.insertSkill(skill, candidate.id)
        }
    }

    private void insertAcademicExperiences(Candidate candidate) {
        for (AcademicExperience academicExperience in candidate.academicExperiences) {
            academicExperienceDAO.insertAcademicExperience(academicExperience, candidate.id)
        }
    }

    private void insertWorkExperiences(Candidate candidate) {
        for (WorkExperience workExperience in candidate.workExperiences) {
            workExperienceDAO.insertWorkExperience(workExperience, candidate.id)
        }
    }

    void insertCandidate(Candidate candidate) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CandidateQueries.INSERT_CANDIDATE,
                    Statement.RETURN_GENERATED_KEYS)
            stmt = this.setCandidateStatement(stmt, candidate)
            stmt.executeUpdate()

            ResultSet getCandidateId = stmt.getGeneratedKeys()
            while (stmt.getGeneratedKeys().next()) {
                candidate.id = getCandidateId.getInt(1)
            }

            this.insertCertificates(candidate)
            this.insertLanguages(candidate)
            this.insertSkills(candidate)
            this.insertAcademicExperiences(candidate)
            this.insertWorkExperiences(candidate)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
    }

    private void updateCandidateCertificates(int id, Candidate candidate) {
        List<Integer> certificatesIds = new ArrayList<>()
        sql.eachRow(CandidateQueries.GET_CERTIFICATE_ID_BY_CANDIDATE_ID, [id]) {row ->
            certificatesIds << row.getInt("id")
        }

        List<Integer> persistedCertificates = certificatesIds.findAll { !candidate.certificates.contains(it) }
        persistedCertificates.each {certificatesId ->
            certificateDAO.deleteCertificate(certificatesId)
        }

        candidate.certificates.each {certificate ->
            if (persistedCertificates.contains(certificate.id)) {
                certificateDAO.updateCertificate(certificate as Certificate, id)
            } else {
                certificateDAO.insertCertificate(certificate as Certificate, id)
            }
        }
    }

    private void updateCandidateLanguages(int id, Candidate candidate) {
        List<Integer> languagesIds = new ArrayList<>()
        sql.eachRow(CandidateQueries.GET_LANGUAGE_ID_BY_CANDIDATE_ID, [id]) {row ->
            languagesIds << row.getInt("id")
        }

        List<Integer> persistedLanguages = languagesIds.findAll { !candidate.languages.contains(it) }
        persistedLanguages.each {languagesId ->
            languageDAO.deleteLanguage(languagesId)
        }

        candidate.languages.each {language ->
            if (persistedLanguages.contains(language.id)) {
                languageDAO.updateLanguage(language as Language, id)
            } else {
                languageDAO.insertLanguage(language as Language, id)
            }
        }
    }

    private void updateCandidateSkills(int id, Candidate candidate) {
        List<Integer> skillsIds = new ArrayList<>()
        sql.eachRow(CandidateQueries.GET_SKILL_ID_BY_CANDIDATE_ID, [id]) {row ->
            skillsIds << row.getInt("id")
        }

        List<Integer> persistedSkills = skillsIds.findAll { !candidate.skills.contains(it) }
        persistedSkills.each {skillsId ->
            skillDAO.deleteSkill(skillsId)
        }

        candidate.skills.each {skill ->
            if (persistedSkills.contains(skill.id)) {
                skillDAO.updateSkill(skill as Skill, id)
            } else {
                skillDAO.insertSkill(skill as Skill, id)
            }
        }
    }

    private void updateCandidateAcademicExperiences(int id, Candidate candidate) {
        List<Integer> academicExperiencesIds = new ArrayList<>()
        sql.eachRow(CandidateQueries.GET_ACADEMIC_EXPERIENCE_ID_BY_CANDIDATE_ID, [id]) {row ->
            academicExperiencesIds << row.getInt("id")
        }

        List<Integer> persistedAcademicExperiences = academicExperiencesIds.findAll {
            !candidate.academicExperiences.contains(it) }
        persistedAcademicExperiences.each {academicExperiencesId ->
            academicExperienceDAO.deleteAcademicExperience(academicExperiencesId)
        }

        candidate.academicExperiences.each {academicExperience ->
            if (persistedAcademicExperiences.contains(academicExperience.id)) {
                academicExperienceDAO.updateAcademicExperience(academicExperience as AcademicExperience, id)
            } else {
                academicExperienceDAO.insertAcademicExperience(academicExperience as AcademicExperience, id)
            }
        }
    }

    private void updateCandidateWorkExperiences(int id, Candidate candidate) {
        List<Integer> workExperiencesIds = new ArrayList<>()
        sql.eachRow(CandidateQueries.GET_WORK_EXPERIENCE_ID_BY_CANDIDATE_ID, [id]) {row ->
            workExperiencesIds << row.getInt("id")
        }

        List<Integer> persistedWorkExperiences = workExperiencesIds.findAll { !candidate.workExperiences.contains(it) }
        persistedWorkExperiences.each {workExperiencesId ->
            workExperienceDAO.deleteWorkExperience(workExperiencesId)
        }

        candidate.workExperiences.each {workExperience ->
            if (persistedWorkExperiences.contains(workExperience.id)) {
                workExperienceDAO.updateWorkExperience(workExperience as WorkExperience, id)
            } else {
                workExperienceDAO.insertWorkExperience(workExperience as WorkExperience, id)
            }
        }
    }

    void updateCandidate(int id, Candidate candidate) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CandidateQueries.UPDATE_CANDIDATE)
            stmt = this.setCandidateStatement(stmt, candidate)
            stmt.setInt(9, id)
            stmt.executeUpdate()

            this.updateCandidateCertificates(id, candidate)
            this.updateCandidateLanguages(id, candidate)
            this.updateCandidateSkills(id, candidate)
            this.updateCandidateAcademicExperiences(id, candidate)
            this.updateCandidateWorkExperiences(id, candidate)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
    }

    void deleteCandidateById(int id) {
        Person candidate = new Candidate()
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CandidateQueries.GET_CANDIDATE_BY_ID)
            stmt.setInt(1, id)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                candidate.setId(result.getInt("id"))
            }

            if (candidate.id != null) {
                stmt = sql.connection.prepareStatement(CandidateQueries.DELETE_CANDIDATE)
                stmt.setInt(1, id)
                stmt.executeUpdate()
                println "Candidato Removido"
                return
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_TEXT, e)
        }
        println NotFoundMessages.CANDIDATE
    }

}
