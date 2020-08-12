<%-- 
    Document   : index
    Created on : May 19, 2020, 10:34:24 PM
    Author     : Mk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="/QuizOnline/universal.css"/>
    </head>
    <body>
        <h1>Welcome!</h1>
        <font color="red">
        ${ALERT}
        </font>
        <form action="LoginController" method="POST">
            Email: <input type="text" name="email" value="${param.email}"/>
            <font color="red">
            ${requestScope.INVALID.emailError}
            </font>
            </br>
            Password: <input type="password" name="password"/>
            <font color="red">
            ${requestScope.INVALID.passwordError}
            </font>
            </br>
            <input type="hidden" name="page" value="1"/>
            <input type="submit" name="action" value="Login"/>
        </form>
        <a href="account/insertAccount.jsp">Sign up</a>
    </body>
</html>
