<%-- 
    Document   : quizDetail
    Created on : May 26, 2020, 1:59:58 PM
    Author     : Mk
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="/QuizOnline/jquery-3.5.1.js"></script>
        <script>
            $(document).ready(function () {
                $("#submittedRow").css("background-color","white");
            });
        </script>
        <link rel="stylesheet" type="text/css" href="/QuizOnline/universal.css"/>
    </head>
    <body>
        <%@include file="/template/studentHeader.jsp" %>
        <h2>${param.displayName}</h2>
        <p>${param.questionAmount} questions - ${param.quizDuration} minutes</p>
        <font color="red">
        ${requestScope.ALERT}
        </font>
        <form action="SearchSubjectController" method="POST">
            <input type="hidden" name="page" value="${param.page}"/>
            <input type="hidden" name="search" value="${param.search}"/>
            <input type="hidden" name="searchBy" value="${param.searchBy}"/>
            <input type="submit" value="< Back"/>
        </form>
        <br/>
        <c:if test="${requestScope.ATTEMPT_LIST!=null}">
            <c:if test="${not empty requestScope.ATTEMPT_LIST}" var="emptyCheck">
                <table border="1" cellpadding="5">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Submitted At</th>
                            <th>Corrected Answer</th>
                            <th>Grade</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.ATTEMPT_LIST}" var="a" varStatus="counter">
                            <c:if test="${counter.count == 1 && requestScope.ALERT!=null}" var="submittedCheck">
                                <tr style="background-color:#99cc00; transition: background-color 10s" id="submittedRow">
                            </c:if>
                            <c:if test="${!submittedCheck}">
                                <tr>
                            </c:if>
                                <td>${counter.count}</td>
                                <td>${a.getSimpleSubmittedDate()}</td>
                                <td>${a.correctAmount}/${param.questionAmount}</td>
                                <td>${a.grade}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </c:if>
            <c:if test="${!emptyCheck}">
                <p style="color:gray">Your attempt history will appear here</p>
            </c:if>
        </c:if>
        <br/>
        <form action="LoadQuizController" method="POST">
            <input type="hidden" name="subjectID" value="${param.subjectID}"/>
            <input type="hidden" name="questionAmount" value="${param.questionAmount}"/>
            <input type="hidden" name="quizDuration" value="${param.quizDuration}"/>
            <input type="hidden" name="displayName" value="${param.displayName}"/>
            <input type="hidden" name="page" value="${param.page}"/>
            <input type="hidden" name="search" value="${param.search}"/>
            <input type="hidden" name="searchBy" value="${param.searchBy}"/>
            <input type="submit" value="Take the Quiz"/>
        </form>
    </body>
</html>
