package com.linketinder.service

import com.google.gson.Gson
import com.linketinder.dao.companydao.interfaces.IJobVacancyDAO
import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.model.jobvacancy.dto.JobVacancyDTO
import com.linketinder.service.interfaces.IJobVacancyService
import com.linketinder.util.*
import groovy.json.JsonSlurper
import javax.servlet.AsyncContext
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level
import java.util.logging.Logger

class JobVacancyService implements IJobVacancyService {

    IJobVacancyDAO jobVacancyDAO
    Gson gson = new Gson()
    JsonSlurper jsonSlurper = new JsonSlurper()

    JobVacancyService(IJobVacancyDAO jobVacancyDAO) {
        this.jobVacancyDAO = jobVacancyDAO
    }

    void get(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.JOB_VACANCIES_PATH_SIZE)
        resp.setContentType("application/json")
        resp.setCharacterEncoding("UTF-8")
        PrintWriter writer = resp.getWriter()

        AsyncContext asyncContext = req.startAsync()
        Thread task = new Thread({
            try {
                if (path.size() > PathSizes.BAR_SIZE) {
                    int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                    JobVacancy jobvacancy = this.getById(id)
                    String jobvacancyJson = gson.toJson(jobvacancy)
                    writer.println(jobvacancyJson)
                    writer.flush()
                    asyncContext.complete()
                } else {
                    List<JobVacancy> jobvacancies = this.getAll()
                    String jobvacanciesJson = gson.toJson(jobvacancies)
                    writer.println(jobvacanciesJson)
                    writer.flush()
                    asyncContext.complete()
                }
            } catch (ServletException e) {
                Logger.getLogger(JobVacancyService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                throw new ServletException(e)
            }
        })
        task.start()
    }

    private List<JobVacancy> getAll() {
        return jobVacancyDAO.getAllJobVacancies()
    }

    private JobVacancy getById(int id) {
        return jobVacancyDAO.getJobVacancyById(id)
    }

    void add(HttpServletRequest req, HttpServletResponse resp) {
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        JobVacancyDTO jobVacancyDTO = jsonSlurper.parse(req.getReader()) as JobVacancyDTO
        int companyId = jobVacancyDTO.companyId
        JobVacancy jobVacancy = jobVacancyDTO.jobVacancy

        Thread task = new Thread({
            try {
                this.jobVacancyDAO.insertJobVacancy(companyId, jobVacancy)
                asyncContext.complete()
            } catch (ServletException e) {
                Logger.getLogger(JobVacancyService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                throw new ServletException(e)
            }
        })
        task.start()
    }

    void update(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.JOB_VACANCIES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()
        JobVacancyDTO jobVacancyDTO = jsonSlurper.parse(req.getReader()) as JobVacancyDTO
        JobVacancy jobVacancy = jobVacancyDTO.jobVacancy as JobVacancy

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.jobVacancyDAO.updateJobVacancy(id, jobVacancy)
                    asyncContext.complete()
                } catch (ServletException e) {
                    Logger.getLogger(JobVacancyService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                    throw new ServletException(e)
                }
            }
        })
        task.start()
    }

    void delete(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getRequestURI().substring(PathSizes.JOB_VACANCIES_PATH_SIZE)
        req.setCharacterEncoding("UTF-8")
        AsyncContext asyncContext = req.startAsync()

        Thread task = new Thread({
            if (path.size() > PathSizes.BAR_SIZE) {
                int id = Integer.parseInt(path.substring(PathSizes.BAR_SIZE))
                try {
                    this.jobVacancyDAO.deleteJobVacancy(id)
                    asyncContext.complete()
                } catch (ServletException e) {
                    Logger.getLogger(JobVacancyService.class.getName()).log(Level.INFO, ErrorMessages.CTLR_TEXT, e)
                    throw new ServletException(e)
                }
            }
        })
        task.start()
    }

}
