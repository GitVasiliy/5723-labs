import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

public class SortName extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        req.getRequestDispatcher("List2.html").include(req, resp);
        ArrayList<String> l = new ArrayList<String>();
        String name = new String("");
        File filesFolder = new File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 9.0/bin/ServerFiles");
        File[] fileList = filesFolder.listFiles();
        List<File> list = Arrays.asList(fileList);
        for (File i: list)
        {
            name = (String)i.getName();
            l.add(name);
        }
        Collections.sort(l);
        for(String fileName: l){
            pw.println(fileName);
            pw.println("<br>");
        }
    }
}