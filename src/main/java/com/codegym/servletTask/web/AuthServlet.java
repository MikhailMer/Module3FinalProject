package com.codegym.servletTask.web;

import javax.servlet.http.*;
import java.io.IOException;

public class AuthServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("index.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        int attemp = 1;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("gameAttempt")) {
                attemp = Integer.parseInt(cookie.getValue());
                attemp++;
                cookie.setValue("" + attemp);
                resp.addCookie(cookie);
                break;
            }
        }
        if (attemp == 1) {
            Cookie cookie = new Cookie("gameAttempt", "" + attemp);
            resp.addCookie(cookie);
        }

        HttpSession session = req.getSession(true);
        String name = req.getParameter("name");
        session.setAttribute("name", name);
        resp.sendRedirect(req.getContextPath() + "/quest");
    }
}

