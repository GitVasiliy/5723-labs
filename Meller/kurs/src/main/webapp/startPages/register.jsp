<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body> <link rel="stylesheet" type="text/css" href="/styles/style.css">

<div id="regFrame">
    <p id="RegMessage">
    <%
        String regStatus = (String)request.getAttribute("status");
        if(regStatus == null) out.print("Register");
        else{
            out.print("This name is not available! Please, try again.");
        }
    %>
    </p>
<FORM action = "/register" method="post" id="regForm">
    <input type = "text" name = "login" placeholder="Your login">
    <br />
    <input type = "password" name = "password" placeholder="Your password">
    <input type = "submit" value = "Register" />
</FORM>
</div>
</body>
</html>
