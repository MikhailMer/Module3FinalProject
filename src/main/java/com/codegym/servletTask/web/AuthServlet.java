package com.codegym.servletTask.web;

import javax.servlet.http.*;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
  private static final String ATTEMPT_COOKIE_NAME = "gameAttempt";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.sendRedirect("index.html");
  }

  private Cookie createAttemptCookie(String attemptValueStr) {
    if (attemptValueStr != null) {
      int attemptValue = Integer.parseInt(attemptValueStr);
      return new Cookie(ATTEMPT_COOKIE_NAME, String.valueOf(attemptValue + 1));
    } else return new Cookie(ATTEMPT_COOKIE_NAME, "1");
  }

  private void setAttemptCookie(HttpServletRequest req, HttpServletResponse resp) {
    String attemptValueStr =
        CookieUtils.findCookiesValueByName(ATTEMPT_COOKIE_NAME, req.getCookies());
    Cookie attemptCookie = createAttemptCookie(attemptValueStr);
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
