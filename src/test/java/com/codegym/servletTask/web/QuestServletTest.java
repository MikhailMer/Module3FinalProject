package com.codegym.servletTask.web;

import com.codegym.servletTask.model.Answer;
import com.codegym.servletTask.model.Question;
import com.codegym.servletTask.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuestServletTest {
    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;
    @Mock
    private HttpSession sessionMock;
    @Mock
    private Repository repositoryMock;
    @Mock
    private Question questionMock1;
    @Mock
    private Question questionMock2;
    @Mock
    private Answer answerMock1;
    @Mock
    private Answer answerMock2;
    @Mock
    private Answer answerMock3;
    @Mock
    private Answer answerMock4;
    @Mock
    private RequestDispatcher requestDispatcherMock;

    private QuestServlet questServlet;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        questServlet = new QuestServlet();
        Field repository = QuestServlet.class.getDeclaredField("repository");
        repository.setAccessible(true);
        repository.set(questServlet, repositoryMock);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(requestMock.getRequestDispatcher("/quest.jsp")).thenReturn(requestDispatcherMock);

        when(repositoryMock.getQuestionById(1)).thenReturn(questionMock1);
        when(questionMock1.getId()).thenReturn(1);
        when(repositoryMock.getAnswerByFromQuestionId(1)).thenReturn(List.of(answerMock1, answerMock2));
    }

    @Test
    public void testDoGetWithAnswerId() throws IOException, ServletException {
        when(requestMock.getCookies()).thenReturn(new Cookie[]{});
        when(requestMock.getParameter("answerId")).thenReturn("2");
        when(repositoryMock.getAnswerById(2)).thenReturn(answerMock1);
        when(answerMock1.getTo()).thenReturn(questionMock2);
        when(questionMock2.getId()).thenReturn(2);
        when(repositoryMock.getAnswerByFromQuestionId(2)).thenReturn(List.of(answerMock3, answerMock4));

        questServlet.doGet(requestMock, responseMock);

        verify(requestMock).setAttribute("question", questionMock2);
        verify(requestMock).setAttribute("answers", List.of(answerMock3, answerMock4));
    }

    @Test
    public void testDoGetWithoutAnswerId() throws IOException, ServletException {
    when(requestMock.getCookies()).thenReturn(new Cookie[]{});
    when(requestMock.getParameter("answerId")).thenReturn(null);

    questServlet.doGet(requestMock, responseMock);

    verify(requestMock).setAttribute("question", questionMock1);
    verify(requestMock).setAttribute("answers", List.of(answerMock1, answerMock2));
    }

    @Test
    public void testDoGetSetsIpAttribute() throws IOException, ServletException {
        when(requestMock.getCookies()).thenReturn(new Cookie[]{});
        when(requestMock.getRemoteAddr()).thenReturn("test ip address");


        questServlet.doGet(requestMock, responseMock);

        verify(requestMock).setAttribute("ip", "test ip address");
    }

    @Test
    public void testDoGetSetsUserNameAttribute() throws IOException, ServletException {
    when(requestMock.getCookies()).thenReturn(new Cookie[]{});
    when(sessionMock.getAttribute("name")).thenReturn("test name");

    questServlet.doGet(requestMock, responseMock);

    verify(requestMock).setAttribute("userName", "test name");
    }

    @Test
    public void testDoGetSetsAttemptAttribute() throws IOException, ServletException {
        Cookie[] cookies = {
                new Cookie("test_cookie_1", "test value"),
                new Cookie("gameAttempt", "65"),
                new Cookie("test_cookie_2", "qwerty")};
        when(requestMock.getCookies()).thenReturn(cookies);


        questServlet.doGet(requestMock, responseMock);

        verify(requestMock).setAttribute("attempt", "65");
    }

    @Test
    public void testDoGetForward() throws IOException, ServletException {
        when(requestMock.getCookies()).thenReturn(new Cookie[]{});


        questServlet.doGet(requestMock, responseMock);

        verify(requestDispatcherMock).forward(requestMock, responseMock);
    }
}
