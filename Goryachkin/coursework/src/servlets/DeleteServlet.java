package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.WriteNotes;
import model.Notes;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sessionUsername = (String) session.getAttribute("username");
		Notes notes = (Notes) session.getAttribute("notes");
		
		String indexToDeleteS = request.getParameter("hiddenget");
		Integer indexToDelete = Integer.parseInt(indexToDeleteS);
		
		notes.removeNote(indexToDelete);
		writeNotes(sessionUsername, notes);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("todo");
    	dispatcher.include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sessionUsername = (String) session.getAttribute("username");
		Notes notes = (Notes) session.getAttribute("notes");
		
		String indexToDeleteS = request.getParameter("hiddenpost");
		String [] numbers = indexToDeleteS.split(" ");
		
		for(int i = numbers.length - 1; i >= 0; i--) {
			notes.removeNote(Integer.parseInt(numbers[i]));
		}
		writeNotes(sessionUsername, notes);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("todo");
    	dispatcher.include(request, response);
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

}
