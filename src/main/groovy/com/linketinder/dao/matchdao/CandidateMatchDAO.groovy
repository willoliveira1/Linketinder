package com.linketinder.dao.matchdao

import com.linketinder.dao.matchdao.interfaces.*
import com.linketinder.dao.matchdao.queries.CandidateMatchQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.IConnection
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

    IMatchDAO matchDAO
    IConnection connection
    Sql sql = connection.instance()

    CandidateMatchDAO(IMatchDAO matchDAO, IConnection connection) {
        this.matchDAO = matchDAO
        this.connection = connection
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
            companyId = this.populateCompanyId(CandidateMatchQueries.GET_COMPANY_ID_BY_JOB_VACANCY_ID, jobVacancyId)
        } catch (SQLException e) {
            Logger.getLogger(IConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return companyId
    }

    private void insertCandidateLike(int candidateId, int jobVacancyId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CandidateMatchQueries.INSERT_CANDIDATE_LIKE,
                    Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, candidateId)
            stmt.setInt(2, jobVacancyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName())
                    .log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    private boolean isExistentCandidateLike(int candidateId, int jobVacancyId) {
        Match match = new Match()
        try {
            match = matchDAO.populateMatch(CandidateMatchQueries.GET_MATCH_BY_CANDIDATE_ID_AND_JOB_VACANCY_ID,
                    candidateId, jobVacancyId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        boolean likeExist = match.getId() != null
        return likeExist
    }

    void candidateLikeJobVacancy(int candidateId, int jobVacancyId) {
        int companyId = this.getCompanyIdByJobVacancyId(jobVacancyId)
        try {
            List<Match> matches = matchDAO.populateMatches(CandidateMatchQueries.CANDIDATE_LIKE_JOB_VACANCY,
                    candidateId, companyId, jobVacancyId)
            matches.forEach {it ->
                if (it.jobVacancyId == 0) {
                    it.jobVacancyId = jobVacancyId
                    matchDAO.updateMatch(it)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
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
            matches = matchDAO.populateMatches(CandidateMatchQueries.GET_ALL_MATCHES_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return matches
    }

}
