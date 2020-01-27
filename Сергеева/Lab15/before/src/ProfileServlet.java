import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*; 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProfileServlet extends HttpServlet {

    private class Post{
        private String authorOfPost;
        private String textOfPost;
        private LocalDateTime date;
        private int postID;

        public Post(String authorOfPost, String textOfPost, int id) {
            this.authorOfPost = authorOfPost;
            this.textOfPost = textOfPost;
            this.date = LocalDateTime.now();
            this.postID = id;
        }

        public String getAuthor() {
            return authorOfPost;
        }

        public String getText() {
            return textOfPost;
        }

        public String getTime() {
            return date.format(formatter);
        }

        public int getId() {
            return this.postID;
        }
 
    }

    private List<Post> deskOfPost = Collections.synchronizedList(new ArrayList<Post>());
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd | HH:mm");
    int id = 0;

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        request.getRequestDispatcher("link.html").include(request, response);

        HttpSession session = request.getSession(false);
        if(session != null){
            String name = (String)session.getAttribute("name");
            request.getRequestDispatcher("post.html").include(request, response);
            String tekst = request.getParameter("tekst");
            if (tekst != null && tekst.length() > 0) {
                deskOfPost.add(new Post(name, tekst, id));
                id++;  
                response.sendRedirect("/lab15/ProfileServlet"); 
            }
            out.println(genBoard(request, response));
        }
        else{
            out.print("<d>Please login first</d>");
            request.getRequestDispatcher("login.html").include(request, response);
            for(Post i : deskOfPost) {
                out.println("<b>" + i.getAuthor() + " " + i.getTime() + "</b>" + "<ul><e>" +  i.getText() + "</ul></e>");
            }
        }

        out.println("</html></body>");
        out.close();
    }



    public String genBoard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer sb = new StringBuffer(); 
        for (Post i : deskOfPost) {
            sb.append("<b>" + i.getAuthor() + " " + i.getTime() + "</b>" + "<ul><e>" +  i.getText() + "</ul></e>");
        }  
        return sb.toString();
    }

    
}
