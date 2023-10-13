package com.linketinder.dao.matchdao

import com.linketinder.database.DatabaseFactory
import com.linketinder.model.match.Match
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger

class MatchDAO {

    static final String QUERY_GET_ALL_MATCHES = """
        SELECT id, candidate_id, company_id, job_vacancy_id
            FROM matches
            WHERE company_id IS NOT NULL
            AND job_vacancy_id IS NOT NULL
            ORDER BY id
    """
    static final String INSERT_CANDIDATE_LIKE = "INSERT INTO matches (candidate_id, job_vacancy_id, company_id) " +
            "VALUES (?,?,null)"
    static final String INSERT_COMPANY_LIKE = "INSERT INTO matches (candidate_id, job_vacancy_id, company_id) " +
            "VALUES (?,null,?)"

    Sql sql = DatabaseFactory.instance()

    private Match populateMatch(String query) {
        Match match = new Match()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            match.setId(result.getInt("id"))
            match.setCandidateId(result.getInt("candidate_id"))
            match.setCompanyId(result.getInt("company_id"))
            match.setJobVacancyId(result.getInt("job_vacancy_id"))
        }
        return match
    }

    private List<Match> populateMatches(String query) {
        List<Match> matches = new ArrayList<>()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
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
        String updateMatch = """
            UPDATE matches
                SET candidate_id=?, company_id=?, job_vacancy_id=?
                WHERE id=${match.id}
        """
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(updateMatch)
            stmt.setInt(1, match.candidateId)
            stmt.setInt(2, match.companyId)
            stmt.setInt(3, match.jobVacancyId)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    List<Match> getAllMatches() {
        List<Match> matches = new ArrayList<>()
        try {
            matches = this.populateMatches(QUERY_GET_ALL_MATCHES)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return matches
    }

    private int populateCompanyId(String query) {
        int companyId = 0
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            companyId = result.getInt("company_id")
        }
        return companyId
    }

    private int getCompanyIdByJobVacancyId(int jobVacancyId) {
        int companyId = 0
        String query = """
            SELECT DISTINCT company_id
                FROM job_vacancies
                WHERE id=${jobVacancyId}
        """
        try {
            companyId = this.populateCompanyId(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
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
                    .log(Level.SEVERE, "Não foi possível concluir a operação no banco de dados.", e)
        }
    }

    boolean isExistentCandidateLike(int candidateId, int jobVacancyId) {
        Match match = new Match()
        String query = """
            SELECT id, candidate_id, company_id, job_vacancy_id
                FROM matches
                WHERE candidate_id=${candidateId}
                AND job_vacancy_id=${jobVacancyId}
        """
        try {
            match = this.populateMatch(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        boolean likeExist = match.getId() != null
        return likeExist
    }

    void candidateLikeJobVacancy(int candidateId, int jobVacancyId) {
        int companyId = this.getCompanyIdByJobVacancyId(jobVacancyId)
        String query = """
            SELECT id, candidate_id, job_vacancy_id, company_id
                FROM matches
                WHERE candidate_id=${candidateId}
                AND company_id=${companyId}
            UNION
            SELECT id, candidate_id, job_vacancy_id, company_id
                FROM matches
                WHERE candidate_id=${candidateId}
                AND job_vacancy_id=${jobVacancyId}
        """
        try {
            List<Match> matches = populateMatches(query)
            matches.forEach {it ->
                if (it.jobVacancyId == 0) {
                    it.jobVacancyId = jobVacancyId
                    this.updateMatch(it)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
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
            Logger.getLogger(DatabaseFactory.class.getName())
                    .log(Level.SEVERE, "Não foi possível concluir a operação no banco de dados.", e)
        }
    }

    boolean isExistentCompanyLike(int companyId, int candidateId) {
        Match match = new Match()
        String query = """
            SELECT id, candidate_id, company_id, job_vacancy_id
                FROM matches
                WHERE candidate_id=${candidateId}
                AND company_id=${companyId}
        """
        try {
            match = this.populateMatch(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        boolean likeExist = match.getId() != null
        return likeExist
    }

    void companyLikeCandidate(int companyId, int candidateId) {
        String query = """
            SELECT DISTINCT m.id, m.candidate_id, m.job_vacancy_id, m.company_id
                FROM matches AS m,
                     job_vacancies AS j
                WHERE m.candidate_id = ${candidateId}
                AND (m.job_vacancy_id = j.id OR m.job_vacancy_id IS NULL)
                AND (m.company_id = ${companyId} OR m.company_id IS NULL)
        """
        try {
            List<Match> matches = this.populateMatches(query)
            matches.forEach {it ->
                if (it.companyId == 0) {
                    it.companyId = companyId
                    this.updateMatch(it)
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        boolean existCandidateLike = this.isExistentCompanyLike(companyId, candidateId)
        if (existCandidateLike) {
            return
        }
        this.insertCompanyLike(companyId, candidateId)
    }

    List<Match> getAllMatchesByCompanyId(int companyId) {
        List<Match> matches = new ArrayList<>()
        String query = """
            SELECT id, candidate_id, company_id, job_vacancy_id
                FROM matches
                WHERE company_id = ${companyId}
                AND job_vacancy_id IS NOT NULL
                ORDER BY id
        """
        try {
            matches = this.populateMatches(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return matches
    }

    List<Match> getAllMatchesByCandidateId(int candidateId) {
        List<Match> matches = new ArrayList<>()
        String query = """
            SELECT id, candidate_id, company_id, job_vacancy_id
                FROM matches
                WHERE candidate_id = ${candidateId} 
                AND company_id IS NOT NULL
                AND job_vacancy_id IS NOT NULL
                ORDER BY id
        """
        try {
            matches = this.populateMatches(query)
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
        return matches
    }

}
