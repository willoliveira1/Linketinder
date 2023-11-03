package com.linketinder.service.interfaces

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface IBaseService {

    void get(HttpServletRequest req, HttpServletResponse resp)
    void add(HttpServletRequest req, HttpServletResponse resp)
    void update(HttpServletRequest req, HttpServletResponse resp)
    void delete(HttpServletRequest req, HttpServletResponse resp)

}
