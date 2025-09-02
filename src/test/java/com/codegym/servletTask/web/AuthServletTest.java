package com.codegym.servletTask.web;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
      // IMPORTANT NOTE ON THIS TEST
      // And its associated servlet code
      // This implementation relies on the Cookie instance to be modified "in place"
      // Due to the mutable nature of the Cookie type (class), we can do that, and the initial
      // implementation of this test is relying on that (only passes if the cookie instance is modified)
      // If for whatever reason we implement this using an immutable approach, the application will still work,
      // but the test won't pass, even though the business logic is correct.
      // However, there is no trivial way to verify the cookie placed in the response is the correct one
      // without resorting to dirty hacks (such as creating our own subclass of Cookie just for testing).
      // This is an important detail.
      // I think It could be even useful to bring it up in class for everyone to think about it.
      // It clearly shows how problematic testing code that relies on mutable data types is.
      // Also, it shows how problematic becomes introducing business logic into controller's (servlets) code.
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
