import java.io.*;
import java.lang.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogoutServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                                throws ServletException, IOException {
            response.setContentType("text/html");

            HttpSession session = request.getSession(true);

            String name = (String)session.getAttribute("name");
            session.invalidate();

            PrintWriter out=response.getWriter();
            out.println("<html><body>");
            out.println("<LINK REL=\"StyleSheet\" HREF=\"" + Styles.getCur() + "\" TYPE=\"text/css\">");
            request.getRequestDispatcher("link.html").include(request, response);
            Cookie[] cookies = null;
            cookies = request.getCookies();
            boolean flag = false;
            String date = new Date().toString();
            date = date.replaceAll(" ", "");
            if (cookies != null)
            {
                for (int i = 0; i < cookies.length; i++)
                {
                    if (cookies[i].getName().equals(name) == true)
                    {
                        Cookie cookie = new Cookie(name, date);
                        try
                        {
                            response.addCookie(cookie);
                            
                        }
                        catch (Exception exp)
                        {
                            out.println(exp.getMessage());
                        }
                        flag = true;
                    }
                }
            }

            out.println("You are successfully logged out!");

            out.println("</html></body>");

            out.close();
    }
}
