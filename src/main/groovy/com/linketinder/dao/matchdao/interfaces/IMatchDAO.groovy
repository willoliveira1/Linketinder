package com.linketinder.dao.matchdao.interfaces

import com.linketinder.model.match.Match
import java.sql.ResultSet

interface IMatchDAO {

    Match createMatch(ResultSet result)
    List<Match> populateMatches(String query, Integer... args)
    Match populateMatch(String query, int candidateId, int companyId)
    void updateMatch(Match match)
    List<Match> getAllMatches()

}
