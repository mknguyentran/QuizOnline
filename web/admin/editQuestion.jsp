<%-- 
    Document   : editQuestion
    Created on : May 25, 2020, 12:50:52 PM
    Author     : Mk
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="/QuizOnline/universal.css"/>
    </head>
    <body>
        <%@include file="/template/adminHeader.jsp" %>
        <form action="SearchQuestionController" method="POST">
            <input type="hidden" name="search" value=""/>
            <input type="hidden" name="searchBy" value="Question"/>
            <input type="hidden" name="page" value="${param.page}"/>
            <input type="submit" value="< Back"/>
        </form>
        <form action="EditQuestionController" method="POST">
            <input type="hidden" name="id" value="${param.id}"/>
            Subject 
            <select name="subjectID">
                <c:if test="${requestScope.SUBJECT_LIST!=null}">
                    <c:if test="${not empty requestScope.SUBJECT_LIST}">
                        <c:forEach items="${requestScope.SUBJECT_LIST}" var="subject">
                                <option value="${subject.id}" <c:if test="${param.subjectID == subject.id}">selected</c:if>>${subject.getDisplayName()}</option>
                        </c:forEach>
                    </c:if>
                </c:if>
            </select>
            <br/>
            <p>Question:</p> 
            <textarea name="question" style="width: 400px; height: 100px" required>${param.question}</textarea> <br/>
            <input type="radio" name="correctAnswer" value="1" required <c:if test="${param.correctAnswer == 1}">checked</c:if>/>
            <label for="1"><input type="text" name="answer1" value="${param.answer1}" placeholder="answer 1"/></label> <br/>
            <input type="radio" name="correctAnswer" value="2" <c:if test="${param.correctAnswer == 2}">checked</c:if>/>
            <label for="2"><input type="text" name="answer2" value="${param.answer2}" placeholder="answer 2"/></label> <br/>
            <input type="radio" name="correctAnswer" value="3" <c:if test="${param.correctAnswer == 3}">checked</c:if>/>
            <label for="3"><input type="text" name="answer3" value="${param.answer3}" placeholder="answer 3"/></label> <br/>
            <input type="radio" name="correctAnswer" value="4" <c:if test="${param.correctAnswer == 4}">checked</c:if>/>
            <label for="4"><input type="text" name="answer4" value="${param.answer4}" placeholder="answer 4"/></label> <br/>
            <input type="hidden" name="search" value="${param.search}"/>
            <input type="hidden" name="searchBy" value="${param.searchBy}"/>
            <input type="hidden" name="page" value="${param.page}"/>
            <input type="submit" name="action" value="Edit"/>
        </form>
    </body>
</html>
