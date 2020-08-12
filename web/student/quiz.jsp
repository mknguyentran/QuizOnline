<%-- 
    Document   : quiz
    Created on : May 26, 2020, 3:07:33 PM
    Author     : Mk
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="student/quizCSS.css"/>
        <link rel="stylesheet" type="text/css" href="/QuizOnline/universal.css"/>
        <script src="/QuizOnline/jquery-3.5.1.js"></script>
        <script>
            var quizDuration = ${param.quizDuration} * 60;
            var currentTime = quizDuration;
            var timePercentage = 99;
            setInterval(function () {
                if (currentTime > 0)
                {
                    if (timePercentage < 10) {
                        document.getElementById("timerBar").style.backgroundColor = 'firebrick';
                    } else if (timePercentage < 30) {
                        document.getElementById("timerBar").style.backgroundColor = 'indianred';
                    } else if (timePercentage < 50) {
                        document.getElementById("timerBar").style.backgroundColor = 'goldenrod';
                    } else if (timePercentage < 70) {
                        document.getElementById("timerBar").style.backgroundColor = 'gold';
                    }

            <%--document.getElementById("timer").innerHTML = '' + currentTime + ' ' + timePercentage + '%';--%>
                    document.getElementById("timerBar").style.width = '' + timePercentage + '%';
                    currentTime -= 0.1;
                    timePercentage = (currentTime / quizDuration) * 100.0;
                }
            }, 100);

            var minutes = ${param.quizDuration};
            var seconds = 0;
            setInterval(function () {
                // Display the result in the element with id="demo"
                document.getElementById("timer").innerHTML = minutes + "m " + seconds + "s ";
                if(seconds == 0){
                    minutes --;
                    seconds = 59;
                }
                seconds--;
            }, 1000);
            var auto_refresh = setInterval(function () {
                submitform();
            }, currentTime * 1000);
            function submitform() {
                document.getElementById("quiz").submit();
            }
        </script>
        <script>
            var currentQuestion = 1;
            var questionAmount = ${param.questionAmount};
            $(document).ready(function () {
                $(".questionBox").each(function () {
                    if ($(this).attr("id") != currentQuestion) {
                        $(this).hide();
                    }
                })
                $("#nextButton").click(function () {
                    $("#prevButton").removeAttr("disabled");
                    if (currentQuestion != questionAmount) {
                        $("#" + currentQuestion).hide();
                        currentQuestion++;
                        $("#" + currentQuestion).show();
                    }
                    if (currentQuestion == questionAmount) {
                        $("#nextButton").attr("disabled", "disabled");
                    }
                })
                $("#prevButton").click(function () {
                    $("#nextButton").removeAttr("disabled");
                    if (currentQuestion != 1) {
                        $("#" + currentQuestion).hide();
                        currentQuestion--;
                        $("#" + currentQuestion).show();
                    }
                    if (currentQuestion == 1) {
                        $("#prevButton").attr("disabled", "disabled");
                    }
                })
            })
        </script>
    </head>
    <body>
        <div id="timerBar" style="width: 100%; height: 20px; position: fixed; top: 0; background-color: green">
        </div>
        <br/>
        <p id="timer">hello</p>
        <br/>
        <div id="content">
            <%@include file="/template/studentHeader.jsp"%>
            <h2>Quiz for ${param.displayName}</h2>
            <p>${param.questionAmount} questions</p>
            <form action="LoadQuizDetailController" method="POST">
                <input type="hidden" name="subjectID" value="${param.subjectID}"/>
                <input type="hidden" name="displayName" value="${param.displayName}"/>
                <input type="hidden" name="quizDuration" value="${param.quizDuration}"/>
                <input type="hidden" name="questionAmount" value="${param.questionAmount}"/>
                <input type="hidden" name="page" value="${param.page}"/>
                <input type="hidden" name="search" value="${param.search}"/>
                <input type="hidden" name="searchBy" value="${param.searchBy}"/>
                <input type="submit" value="< Back"/>
            </form>
            <c:if test="${requestScope.QUESTION_LIST!=null}">
                <c:if test="${not empty requestScope.QUESTION_LIST}">
                    <form id="quiz" action="SubmitQuizController" method="POST">
                        <input type="hidden" name="questionAmount" value="${param.questionAmount}"/>
                        <input type="hidden" name="quizDuration" value="${param.quizDuration}"/>
                        <input type="hidden" name="subjectID" value="${param.subjectID}"/>
                        <c:forEach items="${requestScope.QUESTION_LIST}" var="q" varStatus="counter">
                            <div class="questionBox" id="${counter.count}" style="height: 300px">
                                <h3><pre class="question">${counter.count}. ${q.question}</pre></h3>
                                <input type="hidden" name="q${counter.count}" value="${q.id}"/>
                                <input type="radio" name="a${counter.count}" value="1"/>
                                <label for="1">${q.answer1}</label> <br/>
                                <input type="radio" name="a${counter.count}" value="2"/>
                                <label for="2">${q.answer2}</label> <br/>
                                <input type="radio" name="a${counter.count}" value="3"/>
                                <label for="3">${q.answer3}</label> <br/>
                                <input type="radio" name="a${counter.count}" value="4"/>
                                <label for="4">${q.answer4}</label> <br/>
                                <br/>
                            </div>
                        </c:forEach>
                        <button type="button" id="prevButton" disabled="disabled">Previous Question</button>
                        <button type="button" id="nextButton">Next Question</button>
                        <br/><br/>
                        <input type="submit" value="Submit"/>
                    </form>
                </c:if>
            </c:if>
        </div>
    </body>
</html>
