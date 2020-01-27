<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Sharing system</title>
</head>
<body id="bodyIndex"> <link rel="stylesheet" type="text/css" href="/styles/style.css">
<div id="loginFrame"><p id="LogMessage">
    <%
        String loginStatus = (String)request.getAttribute("status");
        if(loginStatus == null) out.print("Log In!");
        else{
            out.print("Your name or password is wrong!");
        }
    %>
</p>
      <FORM action = "/login" method="post" id="loginForm">
        <input type = "text" name = "login" placeholder="Your login">
        <br />
        <input type = "password" name = "password" placeholder="Your password">
        <input type = "submit" value = "Login" />
      </FORM>
      <button type = "button" onclick = "location.href = 'startPages/register.jsp'" id="regButton"> Register </button>
</div>
</body>
</html>