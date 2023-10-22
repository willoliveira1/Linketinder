package com.linketinder.dao.matchdao

import com.linketinder.dao.matchdao.interfaces.IMatchDAO
import com.linketinder.database.interfaces.IConnection
import com.linketinder.model.match.Match
import com.linketinder.util.ErrorMessages
import groovy.sql.Sql
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger

class MatchDAO implements IMatchDAO {

    private final String GET_ALL_MATCHES = "SELECT id, candidate_id, company_id, job_vacancy_id FROM matches WHERE company_id IS NOT NULL AND job_vacancy_id IS NOT NULL ORDER BY id"
    private final String UPDATE_MATCH = "UPDATE matches SET candidate_id=?, company_id=?, job_vacancy_id=? WHERE id=?"

    IConnection connection
    Sql sql = connection.instance()

    MatchDAO(IConnection connection) {
        this.connection = connection
    }

    Match createMatch(ResultSet result) {
        Match match = new Match()
        match.setId(result.getInt("id"))
        match.setCandidateId(result.getInt("candidate_id"))
        match.setCompanyId(result.getInt("company_id"))
        match.setJobVacancyId(result.getInt("job_vacancy_id"))
        return match
    }

    List<Match> populateMatches(String query, Integer... args) {
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
            Match match = this.createMatch(result)
            matches.add(match)
        }
        return matches
    }

    Match populateMatch(String query, int candidateId, int companyId) {
        Match match = new Match()
        PreparedStatement stmt = sql.connection.prepareStatement(query)
        stmt.setInt(1, candidateId)
        stmt.setInt(2, companyId)
        ResultSet result = stmt.executeQuery()
        while (result.next()) {
            match = this.createMatch(result)
        }
        return match
    }

    void updateMatch(Match match) {
        try {
            PreparedStatement stmt = sql.connection.prepareStatement(UPDATE_MATCH)
            stmt.setInt(1, match.candidateId)
            stmt.setInt(2, match.companyId)
            stmt.setInt(3, match.jobVacancyId)
            stmt.setInt(4, match.id)
            stmt.executeUpdate()
        } catch (SQLException e) {
            Logger.getLogger(IConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
    }

    List<Match> getAllMatches() {
        List<Match> matches = new ArrayList<>()
        try {
            matches = this.populateMatches(GET_ALL_MATCHES)
        } catch (SQLException e) {
            Logger.getLogger(IConnection.class.getName()).log(Level.SEVERE, ErrorMessages.DB_MSG, e)
        }
        return matches
    }

}
