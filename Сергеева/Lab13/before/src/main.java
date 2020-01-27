import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class main extends HttpServlet {
    Names names = new Names();

    public void init(ServletConfig config) {
       try {
			BufferedReader r = new BufferedReader (new InputStreamReader(new FileInputStream("C:/apache-tomcat-9.0.29/bin/list.txt")));
			String[] subStr;
			String tmp, delimeter = " ";
			while(r.ready()) {
				tmp = r.readLine();
				subStr = tmp.split(delimeter);
				ArrayList<String> temp = new ArrayList<String>();
				for (int i = 1; i < subStr.length; i++) {
					temp.add(subStr[i]);
				}
				names.add(subStr[0], temp);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
		PrintWriter out = response.getWriter();
		out.println("<html>\n<body>\n");
		out.println("</lab13/servlet/lab13/>My Notebook\n");
       	if( uri.equals("/lab13/servlet/lab13/addName") ) {
       		out.println(getAddNamePage());    
       	}
		else if( uri.equals("/lab13/servlet/lab13/addPhone") ) {
        	out.println(getAddPhonePage());    
        }
		else if( uri.equals("/lab13/servlet/lab13/addName/add") ) {
			if(!names.contain(request.getParameter("name"))){
				String curName = request.getParameter("name");
				String curPhone = request.getParameter("phone");
				boolean flag = true;
				for(int i = 0; i < curPhone.length(); i++){
						if(Character.isDigit(curPhone.charAt(i)) || curPhone.charAt(i) == '+'){}
						else{
							flag = false;
							break;
						}
				}
				if (Character.isLetter(curName.charAt(0)) && flag == true){
					names.add(request.getParameter("name"),request.getParameter("phone"));
				}
				else{
					out.println("Error\n");
				}
			}
			else{
				out.println("This user was already add\n");
			}
			out.println(getMainPage());
        }
		else if( uri.equals("/lab13/servlet/lab13/addPhone/add") ) {
			if(names.contain(request.getParameter("name"))){
				String curName = request.getParameter("name");
				String curPhone = request.getParameter("phone");
				boolean flag = true;
				for(int i = 0; i < curPhone.length(); i++){
						if(Character.isDigit(curPhone.charAt(i)) || curPhone.charAt(i) == '+'){}
						else{
							flag = false;
							break;
						}
				}
				if (Character.isLetter(curName.charAt(0)) && flag == true){
					names.add(request.getParameter("name"),request.getParameter("phone"));
				}
				else{
					out.println("Error\n");
				}
			}
			else{
				out.println("This user is not in the list\n");
			}
			out.println(getMainPage());
        }
		else{
        	out.println(getMainPage());  
		}
		out.println("</body>\n</html>");
    }
    

    public String getMainPage() {
		StringBuilder sb = new StringBuilder();
 		sb.append("<form method=\"GET\" action=\"/lab13/servlet/lab13/addName\">\n");
        sb.append("<input type=\"submit\" value=\"add user\">\n");
        sb.append("</form>");
		sb.append("<form method=\"GET\" action=\"/lab13/servlet/lab13/addPhone\">\n");
        sb.append("<input type=\"submit\" value=\"add phone\">\n");
        sb.append("</form>");
        HashMap<String, ArrayList<String>> tmp = names.getNamesStrings();
        	for(String n: tmp.keySet()){
        		//if(Character.isLetter(n.charAt(0))){
					sb.append("<p>" + n  + "</p>\n");
				//}
				for(String p: tmp.get(n)){
					sb.append("<p>" + p + "</p>\n");
				}	
			}	 
        	return sb.toString();
    }
	
	public String getAddNamePage() {
		StringBuilder sb = new StringBuilder();
		sb.append("<form method=\"GET\" action=\"/lab13/servlet/lab13/addName/add\">\n");
		sb.append("Name: <input type=\"text\" name=\"name\">\n"); 
		sb.append("Phone: <input type=\"text\" name=\"phone\">\n"); 
        sb.append("<input type=\"submit\" value=\"add\">\n");
       	sb.append("</form>");
		return sb.toString();
	}

	public String getAddPhonePage(){
		StringBuilder sb = new StringBuilder();
		sb.append("<form method=\"GET\" action=\"/lab13/servlet/lab13/addPhone/add\">\n");
		sb.append("Name: <input type=\"text\" name=\"name\">\n"); 
		sb.append("Phone: <input type=\"text\" name=\"phone\">\n"); 
        sb.append("<input type=\"submit\" value=\"add\">\n");
       	sb.append("</form>");
		return sb.toString();
	}
}
