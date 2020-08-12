<%-- 
    Document   : studentHome
    Created on : May 20, 2020, 9:20:42 PM
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
                $(".float").css("float", "left");
                $(".float").css("margin-right", "50px");
                $(".pageLink").css("text-decoration", "none");
                $(".pageLink").css("font-size", "20px");
            });
        </script>
        <link rel="stylesheet" type="text/css" href="student/studentHome.css"/>
        <link rel="stylesheet" type="text/css" href="/QuizOnline/universal.css"/>
    </head>
    <body>
        <%@include file="/template/studentHeader.jsp" %>
        <div id="searchBar">
            <form action="SearchSubjectController" method="POST" class="float">
                <input name="search" type="text" value="${requestScope.searchTerm}" placeholder="search..."/>
                <input type="hidden" name="searchBy" value="Name"/>
                <input type="hidden" name="page" value="1"/>
                <input type="submit" value=">"/>
            </form>
            <form action="SearchSubjectController" method="POST" class="float">
                <select name="search">
                    <option hidden>---Filter by category---</option>
                    <option value="">All category</option>
                    <c:if test="${requestScope.CATEGORY_LIST!=null}">
                        <c:if test="${not empty requestScope.CATEGORY_LIST}">
                            <c:forEach items="${requestScope.CATEGORY_LIST}" var="s">
                                <option value="${s}" <c:if test="${param.searchBy == 'Category' && param.search == s}">selected</c:if>>${s}</option>
                            </c:forEach>
                        </c:if>
                    </c:if>
                </select>
                <input type="hidden" name="searchBy" value="Category"/>
                <input type="hidden" name="page" value="1"/>
                <input type="submit" value=">"/>
            </form>
        </div>
        <br/>
        <br/>
        <div id="content">
            <c:if test="${requestScope.PAGES_AMOUNT != null}">
                <c:if test="${param.page != 1}" var="notFirstPageCheck">
                    <c:url var="pageLink" value="SearchSubjectController">
                        <c:param name="search" value="${param.search}"/>
                        <c:param name="searchBy" value="${param.searchBy}"/>
                        <c:param name="page" value="${param.page - 1}"/>
                    </c:url>
                    <a href="${pageLink}" class="pageLink"><</a>
                </c:if>
                <c:if test="${!notFirstPageCheck}">
                    <a class="pageLink"><</a>
                </c:if>
            </c:if>  
            <c:forEach begin="1" end="${requestScope.PAGES_AMOUNT}" varStatus="counter">
                <c:url var="pageLink" value="SearchSubjectController">
                    <c:param name="search" value="${param.search}"/>
                    <c:param name="searchBy" value="${param.searchBy}"/>
                    <c:param name="page" value="${counter.count}"/>
                </c:url>
                <a href="${pageLink}" class="pageLink" <c:if test="${param.page == counter.count}">style="font-weight: bolder"</c:if>>${counter.count}</a>
            </c:forEach>
            <c:if test="${param.page != requestScope.PAGES_AMOUNT}" var="notLastPageCheck">
                <c:url var="pageLink" value="SearchSubjectController">
                    <c:param name="search" value="${param.search}"/>
                    <c:param name="searchBy" value="${param.searchBy}"/>
                    <c:param name="page" value="${param.page + 1}"/>
                </c:url>
                <a href="${pageLink}" class="pageLink">></a>
            </c:if>
            <c:if test="${!notLastPageCheck}">
                <a class="pageLink">></a>
            </c:if>
            <br/>
            <c:if test="${requestScope.SUBJECT_LIST != null}">
                <c:if test="${not empty requestScope.SUBJECT_LIST}" var="emptyCheck">
                    <c:forEach items="${requestScope.SUBJECT_LIST}" var="subject" varStatus="counter">
                        <c:url var="quizLink" value="LoadQuizDetailController">
                            <c:param name="subjectID" value="${subject.id}"/>
                            <c:param name="displayName" value="${subject.displayName}"/>
                            <c:param name="quizDuration" value="${subject.quizDuration}"/>
                            <c:param name="questionAmount" value="${subject.questionAmount}"/>
                            <c:param name="page" value="${param.page}"/>
                            <c:param name="search" value="${param.search}"/>
                            <c:param name="searchBy" value="${param.searchBy}"/>
                        </c:url>
                        <a href="${quizLink}">
                            <div class="subject">
                                <h2 class="subjectTitle">${subject.displayName}</h2>
                                <p class="subjectDescription">Question amount: ${subject.questionAmount} questions</p>
                                <p class="subjectDescription">Duration: ${subject.quizDuration} minutes</p>
                            </div>
                        </a>
                        <br/>
                    </c:forEach>
                </c:if>
                <c:if test="${!emptyCheck}">
                    <p>No quiz available</p>
                </c:if>
            </c:if>
        </div>
    </body>
</html>
