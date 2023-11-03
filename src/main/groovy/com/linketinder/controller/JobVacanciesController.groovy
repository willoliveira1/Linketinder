package com.linketinder.controller

import com.google.gson.Gson
import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.model.jobvacancy.dto.JobVacancyDTO
import com.linketinder.service.factories.JobVacancyServiceFactory
import com.linketinder.service.interfaces.IJobVacancyService
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

@WebServlet(urlPatterns = ["/jobvacancies", "/jobvacancies/*"], asyncSupported = true)
class JobVacanciesController extends HttpServlet {

    Gson gson
    JsonSlurper jsonSlurper
    IJobVacancyService service

    void init () {
        this.gson = new Gson()
        this.jsonSlurper = new JsonSlurper()
        this.service = JobVacancyServiceFactory.createJobVacancyService()
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.JOB_VACANCIES_PATH_SIZE)
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        PrintWriter writer = resp.getWriter()

        AsyncContext asyncContext = req.startAsync()
        Thread task = new Thread({
            try {
                if (path.size() > PathSizes.BAR_SIZE) {
                    int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                    JobVacancy jobvacancy = this.service.getById(id)
                    String jobvacancyJson = gson.toJson(jobvacancy)
                    writer.println(jobvacancyJson)
                    writer.flush()
                    asyncContext.complete()
                } else {
                    List<JobVacancy> jobvacancies = this.service.getAll()
                    String jobvacanciesJson = gson.toJson(jobvacancies)
                    writer.println(jobvacanciesJson)
                    writer.flush()
                    asyncContext.complete()
                }
            } catch (ServletException | IOException e) {
                Logger.getLogger(JobVacanciesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
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
        JobVacancyDTO jobVacancyDTO = jsonSlurper.parse(req.getReader()) as JobVacancyDTO
        int companyId = jobVacancyDTO.companyId
        JobVacancy jobVacancy = jobVacancyDTO.jobVacancy

        Thread task = new Thread({
            try {
                this.service.add(companyId, jobVacancy)
                asyncContext.complete()
            } catch (ServletException | IOException e) {
                Logger.getLogger(JobVacanciesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)
                return
            }
        })
        task.start()
        resp.setStatus(HttpServletResponse.SC_CREATED)
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.JOB_VACANCIES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        JobVacancyDTO jobVacancyDTO = jsonSlurper.parse(req.getReader()) as JobVacancyDTO
        JobVacancy jobVacancy = jobVacancyDTO.jobVacancy as JobVacancy

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.service.update(id, jobVacancy)
                    asyncContext.complete()
                } catch (ServletException | IOException e) {
                    Logger.getLogger(JobVacanciesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
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
        String path = req.getRequestURI().substring(PathSizes.JOB_VACANCIES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.service.delete(id)
                    asyncContext.complete()
                } catch (ServletException | IOException e) {
                    Logger.getLogger(JobVacanciesController.class.getName()).log(Level.INFO, ErrorMessages.CTLR_MSG, e)
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
