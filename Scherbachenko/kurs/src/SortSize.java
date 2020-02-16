import java.io.*;
import java.util.*;
import java.lang.Long;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class SortSize extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        req.getRequestDispatcher("List1.html").include(req, resp);
        TreeMap<Long, String> map = new TreeMap<Long, String>();//сортирует ключи автоматически
        long size = 0;
        String name = new String("");
        File filesFolder = new File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 9.0/bin/ServerFiles");
        File[] fileList = filesFolder.listFiles();
        List<File> list = Arrays.asList(fileList);
        for (File i: list)
        {
           size = i.length();
           Long s = new Long(size);
           name = (String)i.getName();
           map.put(s, name);//ключ - размер, значение - имя
        }
        for(Map.Entry<Long, String> pair : map.entrySet())
        {
            String value = pair.getValue();//получаем имя
            //Long sizes = pair.getKey();//получаем ключ - размер
            pw.println(value + "<br>");
        }
    }
}