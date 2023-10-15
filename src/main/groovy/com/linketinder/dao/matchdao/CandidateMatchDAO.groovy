package com.linketinder.dao.matchdao

import com.linketinder.dao.matchdao.interfaces.ICandidateMatchDAO
import com.linketinder.dao.matchdao.interfaces.IMatchDAO
import com.linketinder.database.DatabaseFactory
import com.linketinder.database.interfaces.IDatabaseFactory
import com.linketinder.model.match.Match
import com.linketinder.util.ErrorMessages
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CandidateMatchDAO implements ICandidateMatchDAO {

    private final String GET_ALL_MATCHES_BY_CANDIDATE_ID = "SELECT id, candidate_id, company_id, job_vacancy_id FROM matches WHERE candidate_id=? AND company_id IS NOT NULL AND job_vacancy_id IS NOT NULL ORDER BY id"
    private final String GET_COMPANY_ID_BY_JOB_VACANCY_ID = "SELECT DISTINCT company_id FROM job_vacancies WHERE id=?"
    private final String GET_MATCH_BY_CANDIDATE_ID_AND_JOB_VACANCY_ID = "SELECT id, candidate_id, company_id, job_vacancy_id FROM matches WHERE candidate_id=? AND job_vacancy_id=?"
    private final String CANDIDATE_LIKE_JOB_VACANCY = "SELECT id, candidate_id, job_vacancy_id, company_id FROM matches WHERE (candidate_id=? AND company_id=?) OR (candidate_id=? AND job_vacancy_id=?)"
    private final String INSERT_CANDIDATE_LIKE = "INSERT INTO matches (candidate_id, job_vacancy_id, company_id) VALUES (?,?,null)"

    Sql sql = DatabaseFactory.instance()
    IMatchDAO matchDAO

    CandidateMatchDAO(IMatchDAO matchDAO) {
        this.matchDAO = matchDAO
    }

    private int populateCompanyId(String query, int id) {
        int companyId = 0
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, id)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            companyId = result.getInt("company_id")
        }
        return companyId
    }

    private int getCompanyIdByJobVacancyId(int jobVacancyId) {
        int companyId = 0
        try {
            companyId = this.populateCompanyId(GET_COMPANY_ID_BY_JOB_VACANCY_ID, jobVacancyId)
        } catch (SQLException e) {
            Logger.getLogger(IDatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return companyId
    }

    private void insertCandidateLike(int candidateId, int jobVacancyId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_CANDIDATE_LIKE, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, candidateId)
            stmt.setInt(2, jobVacancyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName())
                    .log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    private boolean isExistentCandidateLike(int candidateId, int jobVacancyId) {
        Match match = new Match()
        try {
            match = matchDAO.populateMatch(GET_MATCH_BY_CANDIDATE_ID_AND_JOB_VACANCY_ID, candidateId, jobVacancyId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        boolean likeExist = match.getId() != null
        return likeExist
    }

    void candidateLikeJobVacancy(int candidateId, int jobVacancyId) {
        int companyId = this.getCompanyIdByJobVacancyId(jobVacancyId)
        try {
            List<Match> matches = matchDAO.populateMatches(CANDIDATE_LIKE_JOB_VACANCY, candidateId, companyId, jobVacancyId)
            matches.forEach {it ->
                if (it.jobVacancyId == 0) {
                    it.jobVacancyId = jobVacancyId
                    matchDAO.updateMatch(it)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }

        boolean existentCandidateLike = this.isExistentCandidateLike(candidateId, jobVacancyId)
        if (existentCandidateLike) {
            return
        }

        this.insertCandidateLike(candidateId, jobVacancyId)
    }

    List<Match> getAllMatchesByCandidateId(int candidateId) {
        List<Match> matches = new ArrayList<>()
        try {
            matches = matchDAO.populateMatches(GET_ALL_MATCHES_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return matches
    }

}
