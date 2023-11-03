package com.linketinder.controller

import com.google.gson.Gson
import com.linketinder.model.match.Match
import com.linketinder.service.factories.MatchServiceFactory
import com.linketinder.service.interfaces.IMatchService
import com.linketinder.util.ErrorMessages
import groovy.json.JsonSlurper
import javax.servlet.AsyncContext
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level
import java.util.logging.Logger

@WebServlet(urlPatterns = ["/matches", "/matches/"], asyncSupported = true)
class MatchesController extends HttpServlet {

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
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        PrintWriter writer = resp.getWriter()

        AsyncContext asyncContext = req.startAsync()
        Thread task = new Thread({
            try {
                List<Match> matches = this.service.getAllMatches()
                String matchesJson = gson.toJson(matches)
                writer.println(matchesJson)
                writer.flush()
                asyncContext.complete()
            } catch (ServletException | IOException e) {
                Logger.getLogger(CompaniesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
                return
            }
        })
        task.start()
        resp.setStatus(HttpServletResponse.SC_OK)
    }

}
