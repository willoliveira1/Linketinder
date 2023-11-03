package com.linketinder.controller

import com.google.gson.Gson
import com.linketinder.model.company.Company
import com.linketinder.service.factories.CompanyServiceFactory
import com.linketinder.service.interfaces.ICompanyService
import com.linketinder.util.ErrorMessages
import com.linketinder.util.PathSizes
import groovy.json.JsonSlurper
import javax.servlet.AsyncContext
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level
import java.util.logging.Logger

@WebServlet(urlPatterns = ["/companies", "/companies/*"], asyncSupported = true)
class CompaniesController extends HttpServlet {

    Gson gson
    JsonSlurper jsonSlurper
    ICompanyService service

    void init () {
        this.gson = new Gson()
        this.jsonSlurper = new JsonSlurper()
        this.service = CompanyServiceFactory.createCompanyService()
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.COMPANIES_PATH_SIZE)
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        PrintWriter writer = resp.getWriter()

        AsyncContext asyncContext = req.startAsync()
        Thread task = new Thread({
            try {
                if (path.size() > PathSizes.BAR_SIZE) {
                    int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                    Company company = this.service.getById(id)
                    String companyJson = gson.toJson(company)
                    writer.println(companyJson)
                    writer.flush()
                    asyncContext.complete()
                } else {
                    List<Company> companies = this.service.getAll()
                    String companiesJson = gson.toJson(companies)
                    writer.println(companiesJson)
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
        Company company = jsonSlurper.parse(req.getReader()) as Company

        Thread task = new Thread({
            try {
                this.service.add(company)
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.COMPANIES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        Company company = jsonSlurper.parse(req.getReader()) as Company

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.service.update(id, company)
                    asyncContext.complete()
                } catch (ServletException | IOException e) {
                    Logger.getLogger(CompaniesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
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
        String path = req.getRequestURI().substring(PathSizes.COMPANIES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.service.delete(id)
                    asyncContext.complete()
                } catch (ServletException | IOException e) {
                    Logger.getLogger(CompaniesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
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
