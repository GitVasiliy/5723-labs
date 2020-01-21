import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*; 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProfileServlet extends HttpServlet {

    private class Post implements Comparable<Post> {
        private String authorOfPost;
        private String textOfPost;
        private LocalDateTime date;
        private int likes;
        private int dislikes;
        private int postID;

        public Post(String authorOfPost, String textOfPost, int id) {
            this.authorOfPost = authorOfPost;
            this.textOfPost = textOfPost;
            this.date = LocalDateTime.now();
            this.likes = 0;
            this.dislikes = 0;
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

        public void addLikes() {
            this.likes++;
        }

        public void addDislikes() {
            this.dislikes++;
        }

        public int getLikes() {
            return this.likes;
        }

        public int getDislikes() {
            return this.dislikes;
        }

        public int getId() {
            return this.postID;
        }

        @Override
        public int compareTo(Post emp) {
            return (this.likes - emp.likes);
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
            }
            out.println(genBoard(request,response));
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

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("/lab15dop/like/")) {
            PrintWriter out = response.getWriter();
            int postID = Integer.parseInt(uri.substring(16));
            out.println(postID);
            
            for(Post i : deskOfPost) {
                if (i.getId() == postID) {
                    i.addLikes();
                }
            }
            response.sendRedirect("/lab15dop/ProfileServlet");
        }
        else if (uri.contains("/lab15dop/dislike/")) {
            PrintWriter out = response.getWriter();
            int postID = Integer.parseInt(uri.substring(19));
            out.println(postID);
            
            for(Post i : deskOfPost) {
                if (i.getId() == postID) {
                    i.addDislikes();
                }
            }
            response.sendRedirect("/lab15dop/ProfileServlet");
        }
        else {
            response.sendRedirect("/lab15dop/ProfileServlet/");
        }
    }


    public String genBoard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer sb = new StringBuffer(); 
        Collections.sort(deskOfPost);
        Collections.reverse(deskOfPost);
        for (Post i : deskOfPost) {
            sb.append("<b>" + i.getAuthor() + " " + i.getTime() + "</b>" + "<ul><e>" +  i.getText() + "</ul></e>");
            sb.append("<b>" + i.getLikes() + "</b>");
            sb.append("<form action=\"/lab15dop/like/"+i.getId()+"\" method=\"post\">");
            sb.append("<br><input type=\"submit\" name =\"  "+request.getSession().getAttribute("name")+"\" value=\"like\"></form>\n");
            sb.append("<b>" + i.getDislikes() + "</b>");
            sb.append("<form action=\"/lab15dop/dislike/"+i.getId()+"\" method=\"post\">");
            sb.append("<br><input type=\"submit\" name =\"  "+request.getSession().getAttribute("name")+"\" value=\"dislike\"></form>\n");
        }    
        return sb.toString();
    }

    
}
