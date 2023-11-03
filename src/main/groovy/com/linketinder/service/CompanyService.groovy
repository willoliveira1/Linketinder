package com.linketinder.service

import com.google.gson.Gson
import com.linketinder.dao.companydao.interfaces.ICompanyDAO
import com.linketinder.model.company.Company
import com.linketinder.service.interfaces.ICompanyService
import com.linketinder.util.*
import com.linketinder.validation.interfaces.ICompanyValidation
import groovy.json.JsonSlurper
import javax.servlet.AsyncContext
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level
import java.util.logging.Logger

class CompanyService implements ICompanyService {

    Gson gson = new Gson()
    JsonSlurper jsonSlurper = new JsonSlurper()

    ICompanyDAO companyDAO
    ICompanyValidation validation

    CompanyService(ICompanyDAO companyDAO, ICompanyValidation validation) {
        this.companyDAO = companyDAO
        this.validation = validation
    }

    void get(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.COMPANIES_PATH_SIZE)
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        PrintWriter writer = resp.getWriter()

        AsyncContext asyncContext = req.startAsync()
        Thread task = new Thread({
            try {
                if (path.size() > PathSizes.BAR_SIZE) {
                    int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                    Company company = this.getById(id)
                    String companyJson = gson.toJson(company)
                    writer.println(companyJson)
                    writer.flush()
                    asyncContext.complete()
                } else {
                    List<Company> companies = this.getAll()
                    String companiesJson = gson.toJson(companies)
                    writer.println(companiesJson)
                    writer.flush()
                    asyncContext.complete()
                }
            } catch (ServletException e) {
                Logger.getLogger(CompanyService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                throw new ServletException(e)
            }
        })
        task.start()
    }

    private List<Company> getAll() {
        return companyDAO.getAllCompanies()
    }

    private Company getById(int id) {
        return companyDAO.getCompanyById(id)
    }

    void add(HttpServletRequest req, HttpServletResponse resp) {
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        Company company = jsonSlurper.parse(req.getReader()) as Company
        validation.execute(company)

        Thread task = new Thread({
            try {
                this.companyDAO.insertCompany(company)
                asyncContext.complete()
            } catch (ServletException e) {
                Logger.getLogger(CompanyService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                throw new ServletException(e)
            }
        })
        task.start()
    }

    void update(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.COMPANIES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        Company company = jsonSlurper.parse(req.getReader()) as Company
        validation.execute(company)

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.companyDAO.updateCompany(id, company)
                    asyncContext.complete()
                } catch (ServletException e) {
                    Logger.getLogger(CompanyService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                    throw new ServletException(e)
                }
            }
        })
        task.start()
    }

    void delete(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.COMPANIES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.companyDAO.deleteCompanyById(id)
                    asyncContext.complete()
                } catch (ServletException e) {
                    Logger.getLogger(CompanyService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                    throw new ServletException(e)
                }
            }
        })
        task.start()
    }

}
