package com.linketinder.dao.matchdao

import com.linketinder.database.DatabaseFactory
import com.linketinder.database.IDatabaseFactory
import com.linketinder.model.match.Match
import com.linketinder.util.ErrorText
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class MatchDAO {

    static final String QUERY_GET_ALL_MATCHES = "SELECT id, candidate_id, company_id, job_vacancy_id FROM matches WHERE company_id IS NOT NULL AND job_vacancy_id IS NOT NULL ORDER BY id"
    static final String QUERY_GET_ALL_MATCHES_BY_COMPANY_ID = "SELECT id, candidate_id, company_id, job_vacancy_id FROM matches WHERE company_id=? AND job_vacancy_id IS NOT NULL ORDER BY id"
    static final String QUERY_GET_ALL_MATCHES_BY_CANDIDATE_ID = "SELECT id, candidate_id, company_id, job_vacancy_id FROM matches WHERE candidate_id=? AND company_id IS NOT NULL AND job_vacancy_id IS NOT NULL ORDER BY id"
    static final String QUERY_GET_COMPANY_ID_BY_JOB_VACANCY_ID = "SELECT DISTINCT company_id FROM job_vacancies WHERE id=?"
    static final String QUERY_GET_MATCH_BY_CANDIDATE_ID_AND_JOB_VACANCY_ID = "SELECT id, candidate_id, company_id, job_vacancy_id FROM matches WHERE candidate_id=? AND job_vacancy_id=?"
    static final String QUERY_GET_MATCH_BY_CANDIDATE_ID_AND_COMPANY_ID = "SELECT id, candidate_id, company_id, job_vacancy_id FROM matches WHERE candidate_id=? AND company_id=?"
    static final String QUERY_CANDIDATE_LIKE_JOB_VACANCY = "SELECT id, candidate_id, job_vacancy_id, company_id FROM matches WHERE (candidate_id=? AND company_id=?) OR (candidate_id=? AND job_vacancy_id=?)"
    static final String QUERY_COMPANY_LIKE_CANDIDATE = "SELECT DISTINCT m.id, m.candidate_id, m.job_vacancy_id, m.company_id FROM matches AS m, job_vacancies AS j WHERE m.candidate_id=? AND (m.job_vacancy_id = j.id OR m.job_vacancy_id IS NULL) AND (m.company_id=? OR m.company_id IS NULL)"
    static final String INSERT_CANDIDATE_LIKE = "INSERT INTO matches (candidate_id, job_vacancy_id, company_id) VALUES (?,?,null)"
    static final String INSERT_COMPANY_LIKE = "INSERT INTO matches (candidate_id, job_vacancy_id, company_id) VALUES (?,null,?)"
    static final String UPDATE_MATCH = "UPDATE matches SET candidate_id=?, company_id=?, job_vacancy_id=? WHERE id=?"

//    IDatabaseFactory databaseFactory
    Sql sql = DatabaseFactory.instance()

    private Match populateMatch(String query, int id1, int id2) {
        Match match = new Match()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, id1)
        stmt.setInt(2, id2)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            match.setId(result.getInt("id"))
            match.setCandidateId(result.getInt("candidate_id"))
            match.setCompanyId(result.getInt("company_id"))
            match.setJobVacancyId(result.getInt("job_vacancy_id"))
        }
        return match
    }

    private List<Match> populateMatches(String query, Integer... args) {
        List<Match> matches = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)

        switch (args.size()) {
            case 1:
                stmt.setInt(1, args[0])
                break
            case 2:
                stmt.setInt(1, args[0])
                stmt.setInt(2, args[1])
                break
            case 3:
                stmt.setInt(1, args[0])
                stmt.setInt(2, args[1])
                stmt.setInt(3, args[0])
                stmt.setInt(4, args[2])
                break
        }

        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            Match match = new Match()
            match.setId(result.getInt("id"))
            match.setCandidateId(result.getInt("candidate_id"))
            match.setCompanyId(result.getInt("company_id"))
            match.setJobVacancyId(result.getInt("job_vacancy_id"))
            matches.add(match)
        }
        return matches
    }

    private void updateMatch(Match match) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_MATCH)
            stmt.setInt(1, match.candidateId)
            stmt.setInt(2, match.companyId)
            stmt.setInt(3, match.jobVacancyId)
            stmt.setInt(4, match.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(IDatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
    }

    List<Match> getAllMatches() {
        List<Match> matches = new ArrayList<>()
        try {
            matches = this.populateMatches(QUERY_GET_ALL_MATCHES)
        } catch (SQLException e) {
            Logger.getLogger(IDatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
        return matches
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
            companyId = this.populateCompanyId(QUERY_GET_COMPANY_ID_BY_JOB_VACANCY_ID, jobVacancyId)
        } catch (SQLException e) {
            Logger.getLogger(IDatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
        return companyId
    }

    private void insertCandidateLike(int candidateId, int jobVacancyId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(
                    INSERT_CANDIDATE_LIKE,
                    Statement.RETURN_GENERATED_KEYS
            )
            stmt.setInt(1, candidateId)
            stmt.setInt(2, jobVacancyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName())
                    .log(Level.SEVERE, ErrorText.DbMsg, e)
        }
    }

    boolean isExistentCandidateLike(int candidateId, int jobVacancyId) {
        Match match = new Match()
        try {
            match = this.populateMatch(QUERY_GET_MATCH_BY_CANDIDATE_ID_AND_JOB_VACANCY_ID, candidateId, jobVacancyId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
        boolean likeExist = match.getId() != null
        return likeExist
    }

    void candidateLikeJobVacancy(int candidateId, int jobVacancyId) {
        int companyId = this.getCompanyIdByJobVacancyId(jobVacancyId)
        try {
            List<Match> matches = populateMatches(QUERY_CANDIDATE_LIKE_JOB_VACANCY, candidateId, companyId, jobVacancyId)
            matches.forEach {it ->
                if (it.jobVacancyId == 0) {
                    it.jobVacancyId = jobVacancyId
                    this.updateMatch(it)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }

        boolean existentCandidateLike = this.isExistentCandidateLike(candidateId, jobVacancyId)
        if (existentCandidateLike) {
            return
        }

        this.insertCandidateLike(candidateId, jobVacancyId)
    }

    private void insertCompanyLike(int companyId, int candidateId) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(
                    INSERT_COMPANY_LIKE,
                    Statement.RETURN_GENERATED_KEYS
            )
            stmt.setInt(1, candidateId)
            stmt.setInt(2, companyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
    }

    boolean isExistentCompanyLike(int companyId, int candidateId) {
        Match match = new Match()
        try {
            match = this.populateMatch(QUERY_GET_MATCH_BY_CANDIDATE_ID_AND_COMPANY_ID, candidateId, companyId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
        boolean likeExist = match.getId() != null
        return likeExist
    }

    void companyLikeCandidate(int companyId, int candidateId) {
        try {
            List<Match> matches = this.populateMatches(QUERY_COMPANY_LIKE_CANDIDATE , candidateId, companyId)
            matches.forEach {it ->
                if (it.companyId == 0) {
                    it.companyId = companyId
                    this.updateMatch(it)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
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
            matches = this.populateMatches(QUERY_GET_ALL_MATCHES_BY_COMPANY_ID, companyId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
        return matches
    }

    List<Match> getAllMatchesByCandidateId(int candidateId) {
        List<Match> matches = new ArrayList<>()
        try {
            matches = this.populateMatches(QUERY_GET_ALL_MATCHES_BY_CANDIDATE_ID, candidateId)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, ErrorText.DbMsg, e)
        }
        return matches
    }

}
