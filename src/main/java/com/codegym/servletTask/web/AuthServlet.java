package com.codegym.servletTask.web;

import javax.servlet.http.*;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
  private static final String ATTEMPT_COOKIE_NAME = "gameAttempt";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.sendRedirect("index.html");
  }

  private void setAttemptCookie(HttpServletRequest req, HttpServletResponse resp) {
    Cookie attemptCookie = CookieUtils.findCookieByName(ATTEMPT_COOKIE_NAME, req.getCookies());

    if(attemptCookie != null) {
      String attemptValueStr = attemptCookie.getValue();
      int attemptValue = Integer.parseInt(attemptValueStr);
      attemptCookie.setValue(String.valueOf(attemptValue + 1));
    } else {
      attemptCookie = new Cookie(ATTEMPT_COOKIE_NAME, "1");
    }

    resp.addCookie(attemptCookie);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    req.setCharacterEncoding("UTF-8");

    // To improve the method readability, let's break it down into some more methods
    // Also, consolidate the logic into a more readable execution path
    // These changes are not a modification of the business logic at all
    // They only try to make the code easier to read and maintain.
    setAttemptCookie(req, resp);

    // This part, I leave it as is
    HttpSession session = req.getSession(true);
    String name = req.getParameter("name");
    session.setAttribute("name", name);
    resp.sendRedirect(req.getContextPath() + "/quest");
  }
}
