package com.linketinder.controller

import com.linketinder.service.factories.MatchServiceFactory
import com.linketinder.service.interfaces.IMatchService
import com.linketinder.util.ErrorMessages
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level
import java.util.logging.Logger

@WebServlet(urlPatterns = ["/matches", "/matches/"], asyncSupported = true)
class MatchesController extends HttpServlet {

    IMatchService service

    void init () {
        this.service = MatchServiceFactory.createMatchService()
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.service.getAllMatches(req, resp)
        } catch (ServletException | IOException e) {
            Logger.getLogger(CompaniesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            return
        }
        resp.setStatus(HttpServletResponse.SC_OK)
    }

}
