import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class DropdownList extends HttpServlet 
{
    ArrayList<ArrayList<String>> list;

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
        PrintWriter out = response.getWriter();
        String first = request.getParameter("lvl1");
        String second = request.getParameter("lvl2");
        if (first.length() != 0 && second.length() == 0)
        {
            list.remove(Integer.parseInt(first) - 1);
        }
        if (first.length() != 0 && second.length() != 0)
        {
            list.get(Integer.parseInt(first) - 1).remove(Integer.parseInt(second));
            // if (list.get(Integer.parseInt(first) - 1).size() == 1)
            // {
            //     list.remove(Integer.parseInt(first) - 1);
            // }
        }
        if (first.length() == 0 && second.length() != 0)
        {
            
        }
        try
        {
            this.saveFile();
        }
        catch (Exception exp)
        {

        }
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String uri = request.getRequestURI();
        RequestDispatcher headDispatcher = request.getRequestDispatcher("f.html");
        try
        { 
            this.openFile(out);
        }
        catch(Exception exp)
        {

        }          
        out.println("<html>");
        headDispatcher.include(request, response);
        out.println("<body>");
        out.println("<div class='delete'>");
        //out.println("<form method=\"GET\" action=\"/lab16/DropdownList\">\n");
        out.println("first layer: <input type=\"text\" id=\"first\"><br>\n"); 
        out.println("second layer: <input type=\"text\" id=\"second\">\n"); 
        //out.println("</form>");
        out.println("<button id = \"btn\" onclick = 'check(this)'> delete </button>"); 
        //out.println("</form>");
        //out.println("</form>");
        out.println("</div>");
        out.println(uri);
        /*out.println("<div class='LIST'>");
        this.printList(out);
        out.println("</div>");*/
        /*String first = request.getParameter("first");
        String second = request.getParameter("second");
        if (first.length() != 0 && second.length() == 0)
        {
            list.remove(Integer.parseInt(first) - 1);
        }
        if (first.length() != 0 && second.length() != 0)
        {
            list.get(Integer.parseInt(first)).remove(Integer.parseInt(second));
        }
        if (first.length() == 0 && second.length() != 0)
        {
            out.println("errrror");
        }
        try
        {
            this.saveFile();
        }
        catch (Exception exp)
        {

        }*/
        out.println("<div class='LIST'>");
        this.printList(out);
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
    private synchronized void printList(PrintWriter out)
    {
        out.println("<ol>");
        for(int i = 0; i < list.size(); i++) 
        {
            ArrayList<String> curList = list.get(i);
            if (curList.size() != 1) 
            {
                /*out.println("/li" + curList.get(i) + "/li");*/
                out.println("<li class='dropdown'>\n");

                out.println("<div class='header'>");
                out.println(curList.get(0));
                out.println("</div>\n");

                out.println("<a class='show' onclick='showList(this)' href='#'>");
                out.println("[+]");
                out.println("</a>\n");
                out.println("<ul class='showDropList'>");
                for(int j = 1; j < curList.size(); j++){
                    out.println("<li>" + curList.get(j) + "</li>\n");
                }
                out.println("</ul>\n");
                out.println("</li>\n");
            }
            else 
                if (curList.size() == 1) 
                    out.println("<li>" + curList.get(0) + "</li>\n");
        }
        out.println("</ol>\n");
    }
    private synchronized void openFile(PrintWriter out) throws Exception
    {
        list = new ArrayList<ArrayList<String>>();
        BufferedReader file = new BufferedReader(new FileReader("/home/alng/apache-tomcat-9.0.27/webapps/lab16/file.txt"));

        String line;
        try
        {
            while((line = file.readLine()) != null)
            {
                if(line.startsWith("*"))
                {
                    list.add(new ArrayList<>());
                    list.get(list.size() - 1).add(line.substring(1));

                }
                else 
                    if(line.startsWith("    *") && list.size() != 0)
                    {
                        list.get(list.size() - 1).add(line.substring(5));
                        
                    }
                else 
                    out.println("Error");
            }

        }
        catch(Exception e)
        {
            
        }
        file.close();
    }
    private synchronized void saveFile() throws Exception
    {
        File myFileres = new File("/home/alng/apache-tomcat-9.0.27/webapps/lab16/file.txt");
        FileWriter writer = new FileWriter("/home/alng/apache-tomcat-9.0.27/webapps/lab16/file.txt", false);
        if (myFileres.isFile() == false) throw new Exception("file txt not found");
        for (int i = 0; i < list.size(); i++)
        {
            ArrayList<String> curList = list.get(i);
            writer.write("*" + curList.get(0) + "\n");
            for (int j = 1; j < curList.size(); j++)
            {
                writer.write("    *" + curList.get(j) + "\n");
            }
        }
        
        writer.close();
    }
}