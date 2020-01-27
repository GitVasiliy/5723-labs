import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

//getCookie если есть такие, то заходим
//если нет, то идем в Login, создаем кукки new Cookie("Alina", "time"), response.sendCookie
//


public class MainPage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<LINK REL=\"StyleSheet\" HREF=\"" + Styles.getCur() + "\" TYPE=\"text/css\">");
        HttpSession session = request.getSession(true);
        Cookie[] cookies = null;
        cookies = request.getCookies();
        /*if (cookies != null)
        {
            String name = (String)session.getAttribute("name");
            for (int i = 0; i < cookies.length; i++)
            {
                if (cookies[i].getName().equals(name) == true)
                {
                    out.println("last time"  + cookies[i].getValue());
                    break;
                }
            }
        }*/
        if(session != null)
        {
            request.getRequestDispatcher("linkWithAut.html").include(request, response);
            //out.println("<h2>Login version!!</h2>");
            String name = (String)session.getAttribute("name");
            if (name != null) out.println("<p>Hello " + name + "!</p>");
            else out.println("<p>Hello, guest!</p>");
            out.println("<hr>");
            Advert.printAdvert(response);
        }
        else
        {
            request.getRequestDispatcher("link.html").include(request, response);
            Advert.printAdvert(response);
        }

        out.println("</html></body>");
        out.close();
    }
}
