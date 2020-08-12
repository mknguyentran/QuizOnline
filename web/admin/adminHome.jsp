<%-- 
    Document   : adminHome
    Created on : May 20, 2020, 9:20:58 PM
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
        <link rel="stylesheet" type="text/css" href="/QuizOnline/universal.css"/>
        <link rel="stylesheet" type="text/css" href="/QuizOnline/admin/adminHome.css"/>
    </head>
    <body>
        <%@include file="/template/adminHeader.jsp" %>
        <div id="searchBar">
            <form action="SearchQuestionController" method="POST" class="float">
                <input name="search" type="text" value="${requestScope.searchTerm}" placeholder="search..."/>
                <input type="hidden" name="page" value="1"/>
                <input type="hidden" name="searchBy" value="Question"/>
                <input type="submit" value=">"/>
            </form>
            <form action="SearchQuestionController" method="POST" class="float">
                <select name="search">
                    <option hidden>---Filter by subject---</option>
                    <option value="">All subject</option>
                    <c:if test="${requestScope.SUBJECT_LIST!=null}">
                        <c:if test="${not empty requestScope.SUBJECT_LIST}">
                            <c:forEach items="${requestScope.SUBJECT_LIST}" var="s">
                                <option value="${s.id}" <c:if test="${param.searchBy == 'SubjectID' && param.search == s.id}">selected</c:if>>${s.getDisplayName()}</option>
                            </c:forEach>
                        </c:if>
                    </c:if>
                </select>
                <input type="hidden" name="page" value="1"/>
                <input type="hidden" name="searchBy" value="SubjectID"/>
                <input type="submit" value=">"/>
            </form>
            <form action="SearchQuestionController" method="POST">
                <select name="search">
                    <option hidden>---Filter by status---</option>
                    <option value="">All status</option>
                    <c:if test="${requestScope.STATUS_LIST!=null}">
                        <c:if test="${not empty requestScope.STATUS_LIST}">
                            <c:forEach items="${requestScope.STATUS_LIST}" var="s">
                                <option value="${s}" <c:if test="${param.searchBy == 'Status' && param.search == s}">selected</c:if>>${s}</option>
                            </c:forEach>
                        </c:if>
                    </c:if>
                </select>
                <input type="hidden" name="page" value="1"/>
                <input type="hidden" name="searchBy" value="Status"/>
                <input type="submit" value=">"/>
            </form>
        </div>
        <br/>
        <br/>
        <div id="content">
            <c:if test="${requestScope.PAGES_AMOUNT != null}">
                <c:if test="${param.page != 1}" var="notFirstPageCheck">
                    <c:url var="pageLink" value="SearchQuestionController">
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
                <c:url var="pageLink" value="SearchQuestionController">
                    <c:param name="search" value="${param.search}"/>
                    <c:param name="searchBy" value="${param.searchBy}"/>
                    <c:param name="page" value="${counter.count}"/>
                </c:url>
                <a href="${pageLink}" class="pageLink" <c:if test="${param.page == counter.count}">style="font-weight: bolder"</c:if>>${counter.count}</a>
            </c:forEach>
            <c:if test="${param.page != requestScope.PAGES_AMOUNT}" var="notLastPageCheck">
                <c:url var="pageLink" value="SearchQuestionController">
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
            <c:if test="${requestScope.SEARCH_RESULT!=null}">
                <c:if test="${not empty SEARCH_RESULT}" var="check">
                    <table border="1" cellpadding="5" width="1500px">
                        <colgroup>
                            <col span="1" style="width: 30%;">
                            <col span="1" style="width: 10%;">
                            <col span="1" style="width: 10%;">
                            <col span="1" style="width: 10%;">
                            <col span="1" style="width: 10%;">
                            <col span="1" style="width: 5%;">
                            <col span="1" style="width: 10%;">
                            <col span="1" style="width: 5%;">
                            <col span="1" style="width: 10%;">
                        </colgroup>
                        <thead>
                            <tr>
                                <th>Question</th>
                                <th>Answer 1</th>
                                <th>Answer 2</th>
                                <th>Answer 3</th>
                                <th>Answer 4</th>
                                <th>Subject</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.SEARCH_RESULT}" var="q">
                                <tr>
                                    <td>${q.question}</td>
                                    <td <c:if test="${q.correctAnswer == 1}">style="background-color:#99cc00"</c:if>>${q.answer1}</td>
                                    <td <c:if test="${q.correctAnswer == 2}">style="background-color:#99cc00"</c:if>>${q.answer2}</td>
                                    <td <c:if test="${q.correctAnswer == 3}">style="background-color:#99cc00"</c:if>>${q.answer3}</td>
                                    <td <c:if test="${q.correctAnswer == 4}">style="background-color:#99cc00"</c:if>>${q.answer4}</td>
                                    <td>${q.subjectID}</td>
                                    <c:if test="${q.status == 'active'}" var="statusTest">
                                        <td style="color: green">${q.status}</td>
                                    </c:if>
                                    <c:if test="${!statusTest}">
                                        <td style="color: red">${q.status}</td>
                                    </c:if>
                                    <td>
                                        <form action="EditQuestionController" method="POST">
                                            <input type="hidden" name="searchBy" value="${param.searchBy}"/>
                                            <input type="hidden" name="search" value="${param.search}"/>
                                            <input type="hidden" name="page" value="${param.page}"/>
                                            <input type="hidden" name="id" value="${q.id}"/>
                                            <input type="hidden" name="question" value="${q.question}"/>
                                            <input type="hidden" name="answer1" value="${q.answer1}"/>
                                            <input type="hidden" name="answer2" value="${q.answer2}"/>
                                            <input type="hidden" name="answer3" value="${q.answer3}"/>
                                            <input type="hidden" name="answer4" value="${q.answer4}"/>
                                            <input type="hidden" name="correctAnswer" value="${q.correctAnswer}"/>
                                            <input type="hidden" name="subjectID" value="${q.subjectID}"/>
                                            <input type="hidden" name="action" value="prepare"/>
                                            <input type="submit" value="Edit"/>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="DeactivateQuestionController" method="POST">
                                            <input type="hidden" name="searchBy" value="${param.searchBy}"/>
                                            <input type="hidden" name="search" value="${param.search}"/>
                                            <input type="hidden" name="page" value="${param.page}"/>
                                            <input type="hidden" name="id" value="${q.id}"/>
                                            <c:if test="${statusTest}">
                                                <input type="submit" name="action" value="Deactivate"/>
                                            </c:if>
                                            <c:if test="${!statusTest}">
                                                <input type="submit" name="action" value="Activate"/>
                                            </c:if>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${!check}">
                    <p>No match found</p>
                </c:if>
            </c:if>
        </div>
    </body>
</html>
