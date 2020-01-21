import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        request.getRequestDispatcher("link.html").include(request, response);

        BufferedReader r = new BufferedReader (new InputStreamReader(new FileInputStream("loginPassword.txt")));
        HashMap<String, String> loginPassword = new HashMap<>();
        String[] subStr;
        String tmp, delimeter = " ";
        while(r.ready()) {
            tmp = r.readLine();
            subStr = tmp.split(delimeter);
            loginPassword.put(subStr[0], subStr[1]);
        }

        if(loginPassword.containsKey(name)) {
            if (password.equals(loginPassword.get(name))) {
                out.print("<d>Welcome, " + name + "</d>");
                HttpSession session = request.getSession();
                session.setAttribute("name", name);
            }
            else {
                out.print("<d>Sorry, incorrect password!</d>");
            }
        }
        else {
            out.print("<d>Sorry, incorrect username!</d>");
            request.getRequestDispatcher("login.html").include(request, response);
        }

        out.println("</html></body>");
        out.close();
    }

}
