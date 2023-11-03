package com.linketinder.dao.matchdao

import com.linketinder.dao.matchdao.interfaces.*
import com.linketinder.dao.matchdao.queries.CompanyMatchQueries
import com.linketinder.database.PostgreSqlConnection
import com.linketinder.database.interfaces.IConnection
import com.linketinder.model.match.Match
import com.linketinder.util.ErrorMessages
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CompanyMatchDAO implements ICompanyMatchDAO {

    IMatchDAO matchDAO
    IConnection connection
    Sql sql = connection.instance()

    CompanyMatchDAO(IMatchDAO matchDAO, IConnection connection) {
        this.matchDAO = matchDAO
        this.connection = connection
    }

    private void insertCompanyLike(int companyId, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(CompanyMatchQueries.INSERT_COMPANY_LIKE,
                    Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, candidateId)
            stmt.setInt(2, companyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    private boolean isExistentCompanyLike(int companyId, int candidateId) {
        Match match = new Match()
        try {
            match = matchDAO.populateMatch(CompanyMatchQueries.GET_MATCH_BY_CANDIDATE_ID_AND_COMPANY_ID,
                    candidateId, companyId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        boolean likeExist = match.getId() != null
        return likeExist
    }

    void companyLikeCandidate(int companyId, int candidateId) {
        try {
            List<Match> matches = matchDAO.populateMatches(CompanyMatchQueries.COMPANY_LIKE_CANDIDATE , candidateId,
                    companyId)
            matches.forEach {it ->
                if (it.companyId == 0) {
                    it.companyId = companyId
                    matchDAO.updateMatch(it)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }

        boolean existCandidateLike = this.isExistentCompanyLike(companyId, candidateId)
        if (existCandidateLike) {
            return
        }

        this.insertCompanyLike(companyId, candidateId)
    }

    List<Match> getAllMatchesByCompanyId(int companyId) {
        List<Match> matches = new ArrayList<>()
        try {
            matches = matchDAO.populateMatches(CompanyMatchQueries.GET_ALL_MATCHES_BY_COMPANY_ID, companyId)
        } catch (SQLException e) {
            Logger.getLogger(PostgreSqlConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return matches
    }

}
