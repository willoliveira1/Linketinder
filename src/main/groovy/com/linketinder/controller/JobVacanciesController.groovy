package com.linketinder.controller

import com.linketinder.service.factories.JobVacancyServiceFactory
import com.linketinder.service.interfaces.IJobVacancyService
import com.linketinder.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level
import java.util.logging.Logger

@WebServlet(urlPatterns = ["/jobvacancies", "/jobvacancies/*"], asyncSupported = true)
class JobVacanciesController extends HttpServlet {

    IJobVacancyService service

    void init () {
        this.service = JobVacancyServiceFactory.createJobVacancyService()
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.service.get(req, resp)
        } catch (ServletException | IOException e) {
            Logger.getLogger(JobVacanciesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            return
        }
        resp.setStatus(HttpServletResponse.SC_OK)
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.service.add(req, resp)
        } catch (ServletException | IOException e) {
            Logger.getLogger(JobVacanciesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            return
        }
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.service.update(req, resp)
        } catch (ServletException | IOException e) {
            Logger.getLogger(JobVacanciesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            return
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.service.delete(req, resp)
        } catch (ServletException | IOException e) {
            Logger.getLogger(JobVacanciesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            return
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
    }

}
