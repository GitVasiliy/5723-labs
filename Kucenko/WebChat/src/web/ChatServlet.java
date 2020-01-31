package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Chat;
import model.Message;

@WebServlet("/webChat")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		if(username == null) {
			session.setAttribute("username", "Enter name");
		}
		PrintWriter pw = response.getWriter();
		String pathCSS = "style/style.css";
		pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<title>Chat</title>");
		pw.println("<link rel='stylesheet' href='" + pathCSS + "' type='text/css'>");
        pw.println("</head>");
		pw.println("<body>");
		
		pw.println("<h1>Chat</h1>");
		
		pw.println("<div class='chatbox'>");
		for(int i = 0; i < Chat.size(); i++) {
			Message message = Chat.getMessage(i);
			pw.println("<div class='message'>");
			pw.println("<strong>" + message.getFromWhom() + "</strong> <small>" + message.getDate() + "</small></br>");
			pw.println(message.getMessage());
			pw.println("</div>");
		}
		pw.println("</div>");
		
		pw.println("<form action='webChat' method='post'>");
		pw.println("<table class = 'txt'><tr><td>Name:</td><td>");
		pw.println("<input type='text' name='username' value = '" + session.getAttribute("username") + "'/>");
		pw.println("</td></tr><tr> <td>Message:</td> <td>");
		pw.println("<textarea class='inputtext' name='message' placeholder='input your message..'></textarea>");
		pw.println("</td> </tr><td ><input type='submit' value='Enter' class='button'/></td></form>");
		pw.println("<form action='webChat' method='get'>");
		pw.println("<td><input type='submit' value='Refresh' class='button'/></td>");
		pw.println("</tr> </table>");
		pw.println("</form>");
		
		pw.println("</body>");
		pw.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String textMessage = request.getParameter("message");
		String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		if(username != null && textMessage != null && currentDate != null) {
			session.setAttribute("username", username);
			if(!textMessage.equals(" ")) {
				Message message = new Message(username, currentDate, textMessage);
				Chat.addMessage(message);
			}
			request.removeAttribute("username");
			request.removeAttribute("message");
		}
		doGet(request, response);
	}

}
