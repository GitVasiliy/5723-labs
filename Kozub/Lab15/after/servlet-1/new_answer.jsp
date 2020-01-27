<%@ page language='java' session='false' %>

<%
	String id = (String)request.getParameter("id");
%>



<!DOCTYPE html>

<html>

	<head>

		<meta charset='utf-8'/>

		<title>New answer</title>

		<link rel='icon' href='images/icon/favicon.ico'/>

		<link rel='stylesheet' href='styles/form.css'/>

		<link rel='stylesheet' href='styles/theme/second/theme_form.css'/>

	</head>

	<body>

		<form action='board?action=add-answer' method='post' class='form-new-answer'>

			<input type="hidden" name="id" value='<%= id %>'>

			<div class='form-text'>

				<div class='label'>Answer</div>

				<textarea name="text" class='textarea-new-answer' placeholder='Input answer for ad' required></textarea>

			</div>

			<div class='form-submit'>

				<input type='submit' value='Create new answer'/>

			</div>

		</form>

	</body>

</html>