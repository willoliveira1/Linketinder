package com.linketinder.service

import com.google.gson.Gson
import com.linketinder.dao.candidatedao.interfaces.ICandidateDAO
import com.linketinder.model.candidate.Candidate
import com.linketinder.service.interfaces.ICandidateService
import com.linketinder.util.*
import com.linketinder.validation.CandidateValidation
import groovy.json.JsonSlurper
import javax.servlet.AsyncContext
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level
import java.util.logging.Logger

class CandidateService implements ICandidateService {

    ICandidateDAO candidateDAO
    Gson gson = new Gson()
    JsonSlurper jsonSlurper = new JsonSlurper()

    CandidateService(ICandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO
    }

    void get(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.CANDIDATES_PATH_SIZE)
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        PrintWriter writer = resp.getWriter()

        AsyncContext asyncContext = req.startAsync()
        Thread task = new Thread({
            try {
                if (path.size() > PathSizes.BAR_SIZE) {
                    int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                    Candidate candidate = this.getById(id)
                    String candidateJson = gson.toJson(candidate)
                    writer.println(candidateJson)
                    writer.flush()
                    asyncContext.complete()
                } else {
                    List<Candidate> candidates = this.getAll()
                    String candidatesJson = gson.toJson(candidates)
                    writer.println(candidatesJson)
                    writer.flush()
                    asyncContext.complete()
                }
            } catch (ServletException e) {
                Logger.getLogger(CandidateService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                throw new ServletException(e)
            }
        })
        task.start()
    }

    private List<Candidate> getAll() {
        return this.candidateDAO.getAllCandidates()
    }

    private Candidate getById(int id) {
        return this.candidateDAO.getCandidateById(id)
    }

    void add(HttpServletRequest req, HttpServletResponse resp) {
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        Candidate candidate = jsonSlurper.parse(req.getReader()) as Candidate
        CandidateValidation.execute(candidate)

        Thread task = new Thread({
            try {
                this.candidateDAO.insertCandidate(candidate)
                asyncContext.complete()
            } catch (ServletException e) {
                Logger.getLogger(CandidateService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                throw new ServletException(e)
            }
        })
        task.start()
    }

    void update(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.CANDIDATES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        Candidate candidate = jsonSlurper.parse(req.getReader()) as Candidate
        CandidateValidation.execute(candidate)

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.candidateDAO.updateCandidate(id, candidate)
                    asyncContext.complete()
                } catch (ServletException e) {
                    Logger.getLogger(CandidateService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                    throw new ServletException(e)
                }
            }
        })
        task.start()
    }

    void delete(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.CANDIDATES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.candidateDAO.deleteCandidateById(id)
                    asyncContext.complete()
                } catch (ServletException e) {
                    Logger.getLogger(CandidateService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                    throw new ServletException(e)
                }
            }
        })
        task.start()
    }

}
