<%-- 
    Document   : insertAccount
    Created on : May 20, 2020, 11:50:11 AM
    Author     : Mk
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="/QuizOnline/universal.css"/>
    </head>
    <body>
        <h1>Register</h1>
        <form action="/QuizOnline/InsertAccountController" method="POST">
            Email: <input type="text" name="email" value="${param.email}"/>
            <font color="red">
            ${requestScope.ERROROBJ.emailError}
            </font>
            <br/>
            Name: <input type="text" name="name" value="${param.name}"/>
            <font color="red">
            ${requestScope.ERROROBJ.nameError}
            </font>
            <br/>
            Password: <input type="password" name="password"/>
            <font color="red">
            ${requestScope.ERROROBJ.passwordError}
            </font>
            <br/>
            Confirm Password: <input type="password" name="confirmPassword"/>
            <font color="red">
            ${requestScope.ERROROBJ.confirmError}
            </font>
            <br/>
            <input type="submit"/>
        </form>
    </body>
</html>
