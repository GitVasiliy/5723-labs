import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


public class MainPage extends HttpServlet {

	private HashMap<String,ArrayList<String>> headers = new HashMap<>();
    private String name;
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher("helloworld.jsp").forward(request,response);
    }

    public void getFromFile(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            StringBuilder sb = new StringBuilder();
            Scanner br = new Scanner(new InputStreamReader(new FileInputStream(filename), "UTF8"));
            while (br.hasNext()) {
                String tmp = br.nextLine();
                sb.append(tmp);
                sb.append("\n");
            }
            name = sb.toString();
            String[] strs = sb.toString().split("\n");  
            int count  = 0;
            int cur = 0;
            for (int i = 0; i < strs.length; i++){
                if(strs[i].charAt(0) == '*') {
                    headers.put(strs[i],new ArrayList<String>());
                    cur = i;
                } else {
                    headers.get(strs[cur]).add(strs[i]);
                }
            }

        }
        catch(Exception e){}
    }
    public String getList() {

        StringBuffer sb = new StringBuffer();
        int count = 0;
        for (Map.Entry<String,ArrayList<String>> tmp  : headers.entrySet()) {
            sb.append("<h2 id = " + tmp.getKey().replace(" ","_")+">" + tmp.getKey());
            sb.append("<a id = "+tmp.getKey().replace(" ","_")+"0");
            sb.append(" OnClick=\"on('" + tmp.getKey().replace(" ","_") + "');\">[+]</a>");
            sb.append("<a OnClick=\"off('-1','" + tmp.getKey() + "');\">[x]</a>");
            sb.append("</h2>");
            ArrayList<String> a = tmp.getValue();
            for (int i = 0; i < a.size(); i++){
                sb.append("<h3 style = \" display: none\" class = \"" + tmp.getKey() + "\"><pre> " + a.get(i));
                sb.append("<a OnClick=\"off('" + i + "','"+tmp.getKey()+"');\">[x]</a></pre></h3>");
               // sb.append("");
            }
        }
        return sb.toString();

    }

}
