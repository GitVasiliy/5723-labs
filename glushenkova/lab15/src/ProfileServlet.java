import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                      throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<LINK REL=\"StyleSheet\" HREF=\"" + Styles.getCur() + "\" TYPE=\"text/css\">");

        HttpSession session = request.getSession(true);
        if(session != null)
        {
            request.getRequestDispatcher("linkWithAut.html").include(request, response);
            String name = (String)session.getAttribute("name");
            if (name != null) out.print("<p>Hello, " + name + " Welcome to Profile<br></p>");
            else out.print("<p>You are a guest<br></p>");
            Advert.printAdvert(response);
        }
        else
        {
            request.getRequestDispatcher("linkWithoutAut.html").include(request, response);
            out.println("<h2>You are guest</h2>");
            request.getRequestDispatcher("guest.html").include(request, response);
            Advert.printAdvert(response);
        }

        out.println("</html></body>");
        out.close();
    }
}
