<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello, ${sessionScope.NAME}!</h1>
        <form action="LoginController" method="POST">
            <input type="submit" name="action" value="Logout"/>
        </form>
        <br/>
        
