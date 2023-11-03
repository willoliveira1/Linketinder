package com.linketinder.service.interfaces

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface IMatchService {

    void getAllMatches(HttpServletRequest req, HttpServletResponse resp)
    void getAllMatchesByCandidateId(HttpServletRequest req, HttpServletResponse resp)
    void getAllMatchesByCompanyId(HttpServletRequest req, HttpServletResponse resp)
    void likeJobVacancy(HttpServletRequest req, HttpServletResponse resp)
    void likeCandidate(HttpServletRequest req, HttpServletResponse resp)

}
