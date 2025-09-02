package com.codegym.servletTask.web;

import javax.servlet.http.Cookie;

class CookieUtils {
  static String findCookiesValueByName(String name, Cookie[] cookies) {
    Cookie cookie = findCookieByName(name, cookies);
    if (cookie != null) return cookie.getValue();
    else return null;
  }

  static Cookie findCookieByName(String name, Cookie[] cookies) {
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          return cookie;
        }
      }
    }
    return null;
  }
}
