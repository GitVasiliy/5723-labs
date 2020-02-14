package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.ReadBase;
import io.ReadNotes;
import io.WriteNotes;
import model.Notes;

@WebServlet("/signin")
public class SinginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		startHTML(request, response);
		createBody(request, response);
		endHTML(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		ArrayList<String[]> base = readLoginBase();
		boolean check = true;
		for(int i = 0; i < base.size(); i++) {
			if(base.get(i)[0].equals(username) && base.get(i)[1].equals(password)) {
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				Notes notes = readNotes(username);
				if(notes == null) {
					writeNotes(username, notes);
				}
				session.setAttribute("notes", notes);
				RequestDispatcher dispatcher = request.getRequestDispatcher("todo");
	        	dispatcher.include(request, response);
	        	check = false;
			}
		}
		if(check) errorMessage(request, response);
	}
	
	private void createBody(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		pw.println("<div>");
		pw.println("<table ><tr><td>User name:</td><td>");
		pw.println("<form action='signin' method='post'>");
		pw.println("<input type='text' name='username'/>");
		pw.println("</td></tr><tr> <td>Password:</td> <td>");
		pw.println("<input type='password' name='password'/>");
		pw.println("</td> </tr> <tr><td colspan='2'><input type='submit' value='Sign in'/></td></tr>");
		pw.println("</tr> </form></br>");
		pw.println("<tr><td></td><td><a href='signup'>SIGN UP</a></td><td></td></tr>");
		pw.println("</table>");
		pw.println("</div>");
	}
	
	protected void startHTML(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		String pathCSS = "style/style.css";
		pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<title>signin</title>");
		pw.println("<link rel='stylesheet' href='" + pathCSS + "' type='text/css'>");
        pw.println("</head>");
		pw.println("<body>");
	}
	
	protected void endHTML(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		pw.println("</body> </html>");
	}

	private ArrayList<String []> readLoginBase(){
		String loginBasePath = "loginpassword";
		ServletContext context = getServletContext();
		loginBasePath = context.getRealPath(loginBasePath); 
		System.out.println("Путь, который тебе нужен: ");
		System.out.println(loginBasePath);
		try {
			return ReadBase.readFile(loginBasePath);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private Notes readNotes(String username){
		String notesPath = "notes/" + username;
		ServletContext context = getServletContext();
		notesPath = context.getRealPath(notesPath); 
		try {
			return ReadNotes.readFile(notesPath);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private void writeNotes(String username, Notes notes){
		String notesPath = "notes/" + username;
		ServletContext context = getServletContext();
		notesPath = context.getRealPath(notesPath); 
		try {
			WriteNotes.write(notesPath, notes);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected void errorMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		String pathCSS = "style/style.css";
		pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<title>login page</title>");
		pw.println("<link rel='stylesheet' href='" + pathCSS + "' type='text/css'>");
        pw.println("</head>");
		pw.println("<body>");
		pw.println("<p>Sorry. Incorrect login or password.</p>");
		pw.println("<p>You can try again, or sign up</p>");
		pw.println("<a href='signin'>TRY AGAIN</a>");
		pw.println("</br>");
		pw.println("<a href='signup'>SIGN UP</a>");
		pw.println("</body> </html>");
	}
	
}
