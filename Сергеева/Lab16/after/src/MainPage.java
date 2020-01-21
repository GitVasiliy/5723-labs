import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


public class MainPage extends HttpServlet {

	HashMap<String,ArrayList<String>> headers;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher("helloworld.jsp").forward(request,response);
    }


    public String getFromFile(String filename) {
        try {
            StringBuilder sb = new StringBuilder();
            Scanner sc = new Scanner(new FileInputStream(filename));
            while (sc.hasNext()){
                sb.append(sc.next());
            }
            return sb.toString();  
        }
        catch(Exception e){
            return "error";
        }

    }

  
}