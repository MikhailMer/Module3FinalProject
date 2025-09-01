<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Servlet Quest</title>
</head>
<body>
<section>
    <a href="index.html">Start over</a>
    <h3>${question.content}</h3>

    <form action="${question.type == "LOST" || question.type == "WON" ? 'auth' : 'quest'}" method="get">
        <c:forEach items="${answers}" var="answer">
            <jsp:useBean id="answer" type="com.codegym.servletTask.model.Answer"/>
            <input type="radio" name="answerId" value="${answer.id}">${answer.content}<br>
        </c:forEach>
        <button type="submit">${question.type == "LOST" || question.type == "WON" ? 'Play again' : 'Answer'}</button>
    </form>
</section>
<br>
<br>
<br>
<br>
<br>
Statistics:<br>
IP address: <i>${ip}</i><br>
Name in game: <i>${userName}</i><br>
Game count: <i>${attempt}</i><br>
</body>
</html>
