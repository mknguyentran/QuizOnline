<%-- 
    Document   : error
    Created on : May 20, 2020, 8:15:08 PM
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
        <h1>Error Page</h1>
        <font color="red">
        ${ERROR}
        </font>
        <br/>
        <a href="index.jsp">< Back to home page</a>
    </body>
</html>
