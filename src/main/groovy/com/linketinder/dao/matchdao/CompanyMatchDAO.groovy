package com.linketinder.dao.matchdao

import com.linketinder.dao.matchdao.interfaces.ICompanyMatchDAO
import com.linketinder.database.DatabaseFactory
import com.linketinder.model.match.Match
import com.linketinder.util.ErrorMessages
import groovy.sql.Sql

import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class CompanyMatchDAO implements ICompanyMatchDAO {

    private final String GET_ALL_MATCHES_BY_COMPANY_ID = "SELECT id, candidate_id, company_id, job_vacancy_id FROM matches WHERE company_id=? AND job_vacancy_id IS NOT NULL ORDER BY id"
    private final String GET_MATCH_BY_CANDIDATE_ID_AND_COMPANY_ID = "SELECT id, candidate_id, company_id, job_vacancy_id FROM matches WHERE candidate_id=? AND company_id=?"
    private final String COMPANY_LIKE_CANDIDATE = "SELECT DISTINCT m.id, m.candidate_id, m.job_vacancy_id, m.company_id FROM matches AS m, job_vacancies AS j WHERE m.candidate_id=? AND (m.job_vacancy_id = j.id OR m.job_vacancy_id IS NULL) AND (m.company_id=? OR m.company_id IS NULL)"
    private final String INSERT_COMPANY_LIKE = "INSERT INTO matches (candidate_id, job_vacancy_id, company_id) VALUES (?,null,?)"

    Sql sql = DatabaseFactory.instance()
    MatchDAO matchDAO = new MatchDAO()

    private void insertCompanyLike(int companyId, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(INSERT_COMPANY_LIKE, Statement.RETURN_GENERATED_KEYS)
            stmt.setInt(1, candidateId)
            stmt.setInt(2, companyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    private boolean isExistentCompanyLike(int companyId, int candidateId) {
        Match match = new Match()
        try {
            match = matchDAO.populateMatch(GET_MATCH_BY_CANDIDATE_ID_AND_COMPANY_ID, candidateId, companyId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        boolean likeExist = match.getId() != null
        return likeExist
    }

    void companyLikeCandidate(int companyId, int candidateId) {
        try {
            List<Match> matches = matchDAO.populateMatches(COMPANY_LIKE_CANDIDATE , candidateId, companyId)
            matches.forEach {it ->
                if (it.companyId == 0) {
                    it.companyId = companyId
                    matchDAO.updateMatch(it)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
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
            matches = matchDAO.populateMatches(GET_ALL_MATCHES_BY_COMPANY_ID, companyId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return matches
    }

}
