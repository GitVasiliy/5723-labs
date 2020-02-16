import java.io.*;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetList extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();       
        req.getRequestDispatcher("List.html").include(req, resp);
        File filesFolder = new File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 9.0/bin/ServerFiles");
        File[] fileList = filesFolder.listFiles();
        List<File> list = Arrays.asList(fileList);
        for (File i: list)
        {
            pw.println(i.getName());
            pw.println("<br>");
        }
    }
}