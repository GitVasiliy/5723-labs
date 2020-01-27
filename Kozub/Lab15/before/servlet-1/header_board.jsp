<%@ page language='java' session='false' %>



<%

	String buttons = "";

	if(request.getSession(false) == null) {

		buttons = "<a href='form_user.jsp?action=login' class='menu-button'>Login</a>";

		buttons += "<a href='form_user.jsp?action=registration' class='menu-button'>Registration</a>";

	}

	else {

		buttons = "<a href='new_ad.html' class='menu-button'>New ad</a>";

		buttons += "<a href='logout' class='menu-button'>Logout</a>";

	}

%>



<div class='header'>

	

	<div class='header-menu'>

		<%= buttons %>

	</div>

</div>