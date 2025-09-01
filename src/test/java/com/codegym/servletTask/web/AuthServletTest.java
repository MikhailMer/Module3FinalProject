package com.codegym.servletTask.web;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthServletTest {

    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;
    @Mock
    private HttpSession sessionMock;

    private AuthServlet authServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authServlet = new AuthServlet();
        when(requestMock.getSession(true)).thenReturn(sessionMock);
    }

    @Test
    public void testDoPostCreatesNewCookie() throws IOException {
        Cookie[] cookies = {new Cookie("test_cookie_1", "test value")};
        when(requestMock.getCookies()).thenReturn(cookies);

        authServlet.doPost(requestMock, responseMock);

        verify(responseMock).addCookie(argThat(cookie -> "gameAttempt".equals(cookie.getName()) && "1".equals(cookie.getValue())));
    }

    @Test
    public void testDoPostIncrementsCookieValue() throws IOException {
        Cookie cookie = new Cookie("gameAttempt", "42");
        Cookie[] cookies = {
                new Cookie("test_cookie_1", "test value"),
                cookie,
                new Cookie("test_cookie_2", "qwerty")};
        when(requestMock.getCookies()).thenReturn(cookies);

        authServlet.doPost(requestMock, responseMock);

        assertEquals("43", cookie.getValue());
    }

    @Test
    public void testDoPostGetsOrCreatesSession() throws IOException {
        when(requestMock.getCookies()).thenReturn(new Cookie[]{});

        authServlet.doPost(requestMock, responseMock);

        verify(requestMock).getSession(true);
    }

    @Test
    public void testDoPostSetsSessionAttributeName() throws IOException {
        when(requestMock.getCookies()).thenReturn(new Cookie[]{});
        when(requestMock.getParameter("name")).thenReturn("Jane");

        authServlet.doPost(requestMock, responseMock);

        verify(sessionMock).setAttribute("name", "Jane");
    }

    @Test
    public void testDoPostRedirectsToQuest() throws IOException {
        when(requestMock.getCookies()).thenReturn(new Cookie[]{});
        when(requestMock.getContextPath()).thenReturn("test path");

        authServlet.doPost(requestMock, responseMock);

        verify(responseMock).sendRedirect("test path/quest");
    }

    @Test
    public void testDoGetRedirectsToIndex() throws IOException {
        authServlet.doGet(requestMock, responseMock);

        verify(responseMock).sendRedirect("index.html");
    }

}
