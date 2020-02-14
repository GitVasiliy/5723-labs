package servlets;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.ReadNotes;
import io.WriteNotes;
import model.Notes;

@WebServlet("/todo")
public class ToDoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sessionUsername = (String) session.getAttribute("username");
		String sessionPassword = (String) session.getAttribute("password");
		if(sessionUsername == null || sessionPassword == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("signin");
        	dispatcher.include(request, response);
		}else {
			Notes notes = (Notes) session.getAttribute("notes");
			if(notes == null) {
				notes = readNotes(sessionUsername);
				session.setAttribute("notes", notes);
			}
			startHTML(request, response);
			createBody(request, response);
			endHTML(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newNote = request.getParameter("newnote");
		HttpSession session = request.getSession();
		Notes notes = (Notes) session.getAttribute("notes");
		String username = (String) session.getAttribute("username");
		if(notes != null) {
			if(newNote != null) {
				notes.addNote(newNote);
				writeNotes(username, notes);
			}
		}
		doGet(request, response);
	}

	protected void startHTML(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		String pathCSS = "style/style.css";
		pw.println("<!DOCTYPE html> <html> ");
        pw.println("<head>");
        pw.println("<title>to-do-list</title>");
		pw.println("<link rel='stylesheet' href='" + pathCSS + "' type='text/css'>");
        pw.println("</head>");
		pw.println("<body>");
	}
	
	protected void createBody(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		pw.println("<a href='signout'>SIGN OUT</a>");
		HttpSession session = request.getSession();
		Notes notes = (Notes) session.getAttribute("notes");
		pw.println("<form action='todo' method='post'>");
		pw.println("<input type='text' name='newnote'/>");
		pw.println("<input type='submit' value='Enter'/>");
		pw.println("</form>");
		pw.println("<ul style='list-style-type:none'>");
		for(int i = 0; i < notes.size(); i++) {
			pw.println("<li id='upli" + i +"'>");
			pw.println("<form action='delete' method='get'>");
			pw.println("<input type='hidden' name='hiddenget' value='" + i + "'>");
		    pw.println("<label>" + notes.getNote(i) + "</label>");
			pw.println("<label><input type='checkbox' id='upcheck" + i +"' onchange='test()'></label>");
			pw.println("<label><input type='submit'value='[X]'></label>");
		    pw.println("</form>");
		    pw.println("</li>");
		}
		pw.println("</ul>"); 
		
		pw.println("<ul style='list-style-type:none'>");
		for(int i = 0; i < notes.size(); i++) {
		    pw.println("<li id='downli" + i +"' style='display:none'>");
		    pw.println("<form action='delete' method='get'>");
		    pw.println("<label><s>" + notes.getNote(i) + "</s></label>");
		    pw.println("<input type='hidden' name='hiddenget' value='" + i + "'>");
			pw.println("<label><input type='checkbox' id='downcheck" + i +"' onchange='test()'></label>");
			pw.println("<label><input type='submit'value='[X]'></label>");
		    pw.println("</form>");
		    pw.println("</li>");
		}
		pw.println("<div id='size' style='display:none'>" + notes.size() + "</div>");
		pw.println("</ul>"); 
		
		pw.println("<div id='deleteall' style='display:none'>");
		pw.println("<form action='delete' method='post'>");
		pw.println("<input type='hidden' id='hidden' name ='hiddenpost' value ='test' >");
		pw.println("<input type='submit' value='delete all completed'>");
		pw.println("</form>");
		pw.println("</div>");	
	}
	
	protected void endHTML(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		pw.println("<script  type='text/javascript' src = 'js/script.js'></script>");
		pw.println("</body> </html>");
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
	
}
