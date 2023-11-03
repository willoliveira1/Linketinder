package com.linketinder.service

import com.google.gson.Gson
import com.linketinder.dao.matchdao.interfaces.*
import com.linketinder.model.match.Match
import com.linketinder.service.interfaces.IMatchService
import com.linketinder.util.*
import groovy.json.JsonSlurper
import javax.servlet.AsyncContext
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level
import java.util.logging.Logger

class MatchService implements IMatchService {

    IMatchDAO matchDAO
    ICandidateMatchDAO candidateMatchDAO
    ICompanyMatchDAO companyMatchDAO
    Gson gson = new Gson()
    JsonSlurper jsonSlurper = new JsonSlurper()

    MatchService(IMatchDAO matchDAO, ICandidateMatchDAO candidateMatchDAO, ICompanyMatchDAO companyMatchDAO) {
        this.matchDAO = matchDAO
        this.candidateMatchDAO = candidateMatchDAO
        this.companyMatchDAO = companyMatchDAO
    }

    void getAllMatches(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        PrintWriter writer = resp.getWriter()

        AsyncContext asyncContext = req.startAsync()
        Thread task = new Thread({
            try {
                List<Match> matches = this.matchDAO.getAllMatches()
                String matchesJson = gson.toJson(matches)
                writer.println(matchesJson)
                writer.flush()
                asyncContext.complete()
            } catch (ServletException e) {
                Logger.getLogger(MatchService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                throw new ServletException(e)
            }
        })
        task.start()
    }

    void getAllMatchesByCandidateId(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.CANDIDATE_MATCHES_PATH_SIZE)
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        PrintWriter writer = resp.getWriter()

        AsyncContext asyncContext = req.startAsync()
        Thread task = new Thread({
            try {
                if (path.size() > PathSizes.BAR_SIZE) {
                    int candidateId = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                    List<Match> matches = this.candidateMatchDAO.getAllMatchesByCandidateId(candidateId)
                    String matchesJson = gson.toJson(matches)
                    writer.println(matchesJson)
                    writer.flush()
                    asyncContext.complete()
                }
            } catch (ServletException e) {
                Logger.getLogger(MatchService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                throw new ServletException(e)
            }
        })
        task.start()
    }

    void getAllMatchesByCompanyId(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.COMPANY_MATCHES_PATH_SIZE)
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        PrintWriter writer = resp.getWriter()

        AsyncContext asyncContext = req.startAsync()
        Thread task = new Thread({
            try {
                if (path.size() > PathSizes.BAR_SIZE) {
                    int companyId = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                    List<Match> matches = this.companyMatchDAO.getAllMatchesByCompanyId(companyId)
                    String matchesJson = gson.toJson(matches)
                    writer.println(matchesJson)
                    writer.flush()
                    asyncContext.complete()
                }
            } catch (ServletException | IOException e) {
                Logger.getLogger(MatchService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                throw new ServletException(e)
            }
        })
        task.start()
    }

    void likeJobVacancy(HttpServletRequest req, HttpServletResponse resp) {
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        Match match = jsonSlurper.parse(req.getReader()) as Match

        Thread task = new Thread({
            try {
                this.candidateMatchDAO.candidateLikeJobVacancy(match.candidateId, match.jobVacancyId)
                asyncContext.complete()
            } catch (ServletException | IOException e) {
                Logger.getLogger(MatchService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                throw new ServletException(e)
            }
        })
        task.start()
    }

    void likeCandidate(HttpServletRequest req, HttpServletResponse resp) {
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        Match match = jsonSlurper.parse(req.getReader()) as Match

        Thread task = new Thread({
            try {
                this.companyMatchDAO.companyLikeCandidate(match.companyId, match.candidateId)
                asyncContext.complete()
            } catch (ServletException | IOException e) {
                Logger.getLogger(MatchService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                throw new ServletException(e)
            }
        })
        task.start()
    }

}
