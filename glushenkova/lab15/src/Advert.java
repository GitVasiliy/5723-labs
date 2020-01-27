import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.*;
import java.util.*;

public class Advert extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<LINK REL=\"StyleSheet\" HREF=\"" + Styles.getCur() + "\" TYPE=\"text/css\">");
        request.getRequestDispatcher("linkWithAutNewAd.html").include(request, response);
        HttpSession session = request.getSession(true);
        if (session != null)
        {
            String name = (String)session.getAttribute("name");

            String header = request.getParameter("header");
            String advert = request.getParameter("advert");
            if (header.indexOf("<") != -1 || advert.indexOf(">") != -1)
            {
                out.println("Error: html code");
            }
            else
            {
                Ad curAd = new Ad(header, advert, name, new Date().toString());
                saveFile(curAd);

                String uri = request.getRequestURI();
                if (uri.equals("/lab15/Advert") && header != "" && advert != "") 
                {
                    request.getRequestDispatcher("Advert.html").include(request, response);
                }
                if (uri.equals("/lab15/Advert") && (header == "" || advert == ""))
                {
                    out.println("Your advert is empty! Please repeat your enter");
                    request.getRequestDispatcher("Advert.html").include(request, response);
                }
            }
        }
        out.println("</html></body>");
        out.close();
    }

    public static void printAdvert(HttpServletResponse response) throws IOException
    {

        PrintWriter out = response.getWriter();
        BufferedReader in = new BufferedReader(new FileReader("/home/alng/apache-tomcat-9.0.27/webapps/lab15/startBoard.txt"));

        String str;
        String [] info;
        try
        {
            while((str = in.readLine()) != null)
            {
                info = str.split(";");
                out.println("<h3>" + info[0].replaceAll("<", "") + "</h3>");
                out.print("<h4>" + info[1].replaceAll("<", "")  + "</h4><br>");
                out.print("<h5><br>"+info[2].replaceAll("<", "")  + "</h5>");
                out.print("<h5><br>"+info[3].replaceAll("<", "")  + "</h5>");
                out.println("<hr>");
            }
        }
        catch(Exception e)
        {
            
        }
        in.close();
    }
    public void saveFile(Ad curAd) throws IOException
    {

        try
        {
            String file = "/home/alng/apache-tomcat-9.0.27/webapps/lab15/startBoard.txt";
            File myFile = new File(file);
            if (myFile.isFile() == false) throw new Exception("file txt not found");
            FileReader reader = new FileReader(file);
            int c = 0;
            String text = "";
            while ((c = reader.read()) != -1)
            {
                text = text + (char)c;
            }
            reader.close();
            FileWriter writer = new FileWriter(file, false);
            if (myFile.isFile() == false) throw new Exception("file txt not found");
            writer.write(text);
            String res = "";
            res = curAd.header + ";" + curAd.text + ";" + curAd.author + ";" + curAd.time + "\n";
            writer.write(res);
            writer.close();
        }
        catch(Exception exp)
        {

        }
    }
    public class Ad
    {
        String header;
        String text;
        String author;
        String time;
        
        public Ad(String h, String te, String a, String t)
        {
            header = h;
            text = te;
            author = a;
            time = t;
        }
    }
}