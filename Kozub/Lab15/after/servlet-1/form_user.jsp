<%@ page language='java' session='false' %>



<%

	String action = (String)request.getParameter("action");



	String title = "";

	String submitButton = "";



	if("login".equals(action)) {

		title = "Login";

		submitButton = title;

	}

	else if("registration".equals(action)) {

		title = "Registration";

		submitButton = "Register";

	}

%>



<!DOCTYPE html>

<html>

	<head>

		<meta charset='utf-8'/>

		<title><%= title %></title>

		

		<link rel='stylesheet' href='styles/form.css'>

		<link rel='stylesheet' href='styles/theme/second/theme_form.css'>

	</head>

	<body>

		<form action='users?action=<%= action %>' method='post' class='form-user'>

			<div class='form-name'>

				<div class='label'>Name</div>

				<input type='text' name='name' class='input-user' placeholder='Input name' required/>

			</div>

			<div class='form-password'>

				<div class='label'>Password</div>

				<input type='password' name='password' class='input-user' placeholder='Input password' required/>

			</div>

			<div class='form-submit'>

				<input type='submit' value='<%= submitButton %>'/>

			</div>

		</form>

	</body>

</html>