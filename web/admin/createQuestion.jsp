<%-- 
    Document   : createQuestion
    Created on : May 21, 2020, 1:46:18 PM
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
            <input type="hidden" name="search" value="${param.search}"/>
            <input type="hidden" name="searchBy" value="${param.searchBy}"/>
            <input type="hidden" name="page" value="${param.page}"/>
            <input type="submit" value="< Back"/>
        </form>
        <form action="CreateQuestionController" method="POST">
            Subject 
            <select name="subjectID">
                <option value="" hidden>---Choose a subject---</option>
                <c:if test="${requestScope.SUBJECT_LIST!=null}">
                    <c:if test="${not empty requestScope.SUBJECT_LIST}">
                        <c:forEach items="${requestScope.SUBJECT_LIST}" var="subject">
                            <option value="${subject.id}">${subject.getDisplayName()}</option>
                        </c:forEach>
                    </c:if>
                </c:if>
            </select>
            <font color="red">
            ${requestScope.ERROR.subjectIDError}
            </font>
            <br/>
            <p>Question:</p> <textarea name="question" style="width: 400px; height: 100px" required>${param.question}</textarea> <br/>
            <input type="radio" name="correctAnswer" value="1" required/>
            <label for="1"><input type="text" name="answer1" value="${param.answer1}" placeholder="answer 1" required/></label> <br/>
            <input type="radio" name="correctAnswer" value="2"/>
            <label for="2"><input type="text" name="answer2" value="${param.answer2}" placeholder="answer 2" required/></label> <br/>
            <input type="radio" name="correctAnswer" value="3"/>
            <label for="3"><input type="text" name="answer3" value="${param.answer3}" placeholder="answer 3" required/></label> <br/>
            <input type="radio" name="correctAnswer" value="4"/>
            <label for="4"><input type="text" name="answer4" value="${param.answer4}" placeholder="answer 4" required/></label> <br/>
            <input type="hidden" name="search" value="${param.search}"/>
            <input type="hidden" name="searchBy" value="${param.searchBy}"/>
            <input type="hidden" name="page" value="${param.page}"/>
            <input type="submit" name="action" value="Add"/>
        </form>
    </body>
</html>
