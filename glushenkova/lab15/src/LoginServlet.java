import java.io.*;
import java.lang.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<LINK REL=\"StyleSheet\" HREF=\"" + Styles.getCur() + "\" TYPE=\"text/css\">");
        request.getRequestDispatcher("linkWithAut.html").include(request, response);
        Cookie[] cookies = null;
        cookies = request.getCookies();
        boolean flag = false;
        String uri = request.getRequestURI();
        try
        {
            String date = new Date().toString();
            date = date.replaceAll(" ", "");
            if (checkLogin(name, password))
            {
                out.println("Welcome, " + name);
                if (cookies != null)
                {
                    for (int i = 0; i < cookies.length; i++)
                    {
                        //out.println("p>" + cookies[i].getName() + " " + cookies[i].getValue());
                        if (cookies[i].getName().equals(name) == true)
                        {
                            out.println("<p>last time: "  + cookies[i].getValue() + "</p>");
                            flag = true;
                        }
                    }
                }
                if (flag == false || cookies == null)
                {
                    Cookie cookie = new Cookie(name, date);
                    try
                    {
                        response.addCookie(cookie);
                        out.println("<br>This is the first time you visit this site");
                    }
                    catch (Exception exp)
                    {
                        out.println(exp.getMessage());
                    }
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("name",  name);
            }
            else
            {
                out.print("Sorry, username or password error!");
                request.getRequestDispatcher("login.html").include(request, response);
            }
        }
        catch (Exception e)
        {

        }
        
        /*try
        {
            if (checkLogin(name, password))
            {
                out.print("Welcome, " + name);
                HttpSession session = request.getSession();
                session.setAttribute("name",  name);
            }
            else
            {
                out.print("Sorry, username or password error!");
                request.getRequestDispatcher("login.html").include(request, response);
            }
        }
        catch (Exception e)
        {

        }*/
        out.println("</html></body>");
        out.close();
    }

    boolean checkLogin(String name, String pass) throws Exception
    {
        BufferedReader in = new BufferedReader(new FileReader("/home/alng/apache-tomcat-9.0.27/webapps/lab15/users.txt"));

        String str;
        String [] info;
        try
        {
            while((str = in.readLine()) != null)
            {
                info = str.split(";");
                if (info[0].equals(name) == true && info[1].equals(pass) == true)
                    return true;
            }
        }
        catch(Exception e)
        {
            
        }
        in.close();
        return false;
    }

}