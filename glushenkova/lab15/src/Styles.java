import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Styles extends HttpServlet
{
    static int cur = 0;
    static String[] styles = {"style.css", "style2.css"};

    static String changeStyle()
    {
        cur = (cur + 1) % styles.length;
        return styles[cur];
    }
    static String getCur()
    {
        return styles[cur];
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        changeStyle();
        out.println("<LINK REL=\"StyleSheet\" HREF=\"" + Styles.getCur() + "\" TYPE=\"text/css\">");
        HttpSession session = request.getSession(true);
        if(session != null)
        {
            request.getRequestDispatcher("linkWithAut.html").include(request, response);
            String name = (String)session.getAttribute("name");
            if (name != null) out.print("<p>Hello, " + name + " Welcome to Profile<br></p>");
            else out.print("<p>Hello, guest Welcome to Profile<br></p>");
            out.println("<h2>Style changed</h2>");
            out.println("<hr>");
            Advert.printAdvert(response);
        }
        else
        {
            request.getRequestDispatcher("link.html").include(request, response);
            out.println("<h2>You are guest</h2>");
            out.println("<h2>Style changed</h2>");
            Advert.printAdvert(response);
        }

        out.println("</html></body>");
        out.close();
    }
}