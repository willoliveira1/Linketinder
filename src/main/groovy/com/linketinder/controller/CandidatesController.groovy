package com.linketinder.controller

import com.google.gson.Gson
import com.linketinder.model.candidate.Candidate
import com.linketinder.service.factories.CandidateServiceFactory
import com.linketinder.service.interfaces.ICandidateService
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

@WebServlet(urlPatterns = ["/candidates", "/candidates/*"], asyncSupported = true)
class CandidatesController extends HttpServlet {

    Gson gson
    JsonSlurper jsonSlurper
    ICandidateService service

    void init () {
        this.gson = new Gson()
        this.jsonSlurper = new JsonSlurper()
        this.service = CandidateServiceFactory.createCandidateService()
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.CANDIDATES_PATH_SIZE)
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        PrintWriter writer = resp.getWriter()

        AsyncContext asyncContext = req.startAsync()
        Thread task = new Thread({
            try {
                if (path.size() > PathSizes.BAR_SIZE) {
                    int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                    Candidate candidate = this.service.getById(id)
                    String candidateJson = gson.toJson(candidate)
                    writer.println(candidateJson)
                    writer.flush()
                    asyncContext.complete()
                } else {
                    List<Candidate> candidates = this.service.getAll()
                    String candidatesJson = gson.toJson(candidates)
                    writer.println(candidatesJson)
                    writer.flush()
                    asyncContext.complete()
                }
            } catch (ServletException | IOException e) {
                Logger.getLogger(CandidatesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
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
        Candidate candidate = jsonSlurper.parse(req.getReader()) as Candidate

        Thread task = new Thread({
            try {
                this.service.add(candidate)
                asyncContext.complete()
            } catch (ServletException | IOException e) {
                Logger.getLogger(CandidatesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
                return
            }
        })
        task.start()
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.CANDIDATES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        Candidate candidate = jsonSlurper.parse(req.getReader()) as Candidate

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.service.update(id, candidate)
                    asyncContext.complete()
                } catch (ServletException | IOException e) {
                    Logger.getLogger(CandidatesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    return
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            }
        })
        task.start()
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.CANDIDATES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.service.delete(id)
                    asyncContext.complete()
                } catch (ServletException | IOException e) {
                    Logger.getLogger(CandidatesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
                    return
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            }
        })
        task.start()
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
    }

}
