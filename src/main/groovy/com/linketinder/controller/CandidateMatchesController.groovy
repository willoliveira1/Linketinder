package com.linketinder.controller

import com.google.gson.Gson
import com.linketinder.model.match.Match
import com.linketinder.service.factories.MatchServiceFactory
import com.linketinder.service.interfaces.IMatchService
import com.linketinder.util.*
import groovy.json.JsonSlurper
import javax.servlet.AsyncContext
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level
import java.util.logging.Logger

@WebServlet(urlPatterns = ["/candidatematches", "/candidatematches/*"], asyncSupported = true)
class CandidateMatchesController extends HttpServlet {

    Gson gson
    JsonSlurper jsonSlurper
    IMatchService service

    void init () {
        this.gson = new Gson()
        this.jsonSlurper = new JsonSlurper()
        this.service = MatchServiceFactory.createMatchService()
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.CANDIDATE_MATCHES_PATH_SIZE)
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        PrintWriter writer = resp.getWriter()

        AsyncContext asyncContext = req.startAsync()
        Thread task = new Thread({
            try {
                if (path.size() > PathSizes.BAR_SIZE) {
                    int candidateId = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                    List<Match> matches = this.service.getAllMatchesByCandidateId(candidateId)
                    String matchesJson = gson.toJson(matches)
                    writer.println(matchesJson)
                    writer.flush()
                    asyncContext.complete()
                }
            } catch (ServletException | IOException e) {
                Logger.getLogger(CompaniesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
                return
            }
        })
        task.start()
        resp.setStatus(HttpServletResponse.SC_OK)
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        Match match = jsonSlurper.parse(req.getReader()) as Match

        Thread task = new Thread({
            try {
                this.service.likeJobVacancy(match.candidateId, match.jobVacancyId)
                asyncContext.complete()
            } catch (ServletException | IOException e) {
                Logger.getLogger(CompaniesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
                return
            }
        })
        task.start()
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }

}
