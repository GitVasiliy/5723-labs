<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sharing system</title>
    <link rel="stylesheet" href="/styles/style.css">
</head>
<body>
    <p> Welcome in your disk! </p>
    <div id="mainFrame">
    <ul class="tree" id="tree">
        <% String[][] dirs = (String[][])request.getAttribute("files");
            for(String[] nameOfDirectory : dirs){
                out.println("<li>" + nameOfDirectory[0] + "<ul>");

                for(int i = 1; i < nameOfDirectory.length; i++){
                    out.print("<li>" + nameOfDirectory[i]);
                    out.print("<ul><li> <a href=\"/download?file=" + nameOfDirectory[i] + "&directory=" +
                            nameOfDirectory[0] + "\">Download</a></li>");
                    out.print("<li><a href=\"/delete?file=" + nameOfDirectory[i] + "&directory=" +
                            nameOfDirectory[0] +"\"> Delete </a></li>");
                    out.print("<li><a href=\"/generate?file=" + nameOfDirectory[i] + "&directory=" +
                            nameOfDirectory[0] +"\"> Generate link! </a></li></ul></li>");
                }

                out.println("</ul></li>");
            } %>
        <li>Your links:
            <ul>
        <%
            ArrayList<String> links = (ArrayList<String>)request.getAttribute("links");
            if(links != null){

                for(String link : links){
                    out.print("<li>" + link + "</li>");
                }
            }
        %>
            </ul>
        </li>
    </ul>

    <form action="upload" method="post" enctype = "multipart/form-data">
        <input type="file" name="file" />
        <select name="dirForUpload">
        <%
            ArrayList<String> editableDirs = (ArrayList<String>)request.getAttribute("dirs");
            for(String tmp : editableDirs) {
                out.println("<option>" + tmp + "</option>");
            }
        %>
        <input type="submit" value="Upload" />
    </form>

    <form action="sharing" method="post">
        Give access:
        <select name="user">
        <%
            ArrayList<String> users = (ArrayList<String>)request.getAttribute("users");
            for(String tmp : users) {
                if(!tmp.equals(session.getAttribute("userName"))){
                     out.println("<option>" + tmp + "</option>");
                 }
            }
        %>
        </select>
        <input type="radio" name="modification" value="onlyRead" checked /> Only read
        <input type="radio" name="modification" value="readAndMod" /> Read and modification
        <input type="submit" value="Share" />
    </form>

    <a href="/logout">Log out</a>

    </div>

    <div id="searchFrame">
        <form action="search" method="get">
                <input type="search" placeholder="Поиск файла" name="searchRequest">
                <input type="submit" value="Search">
        </form>
        <p>
                <%
                    ArrayList<String> resOfSearch = (ArrayList<String>)request.getAttribute("searchResult");
                    if(resOfSearch != null){
                        for(String nameOfFoundedFile : resOfSearch){
                            out.println("<a href=\"/download?file=" + nameOfFoundedFile + "\">" + nameOfFoundedFile + "</a></br>");
                        }
                    }
                %>
            </p>
    </div>

    <script src="/scripts/viewDirectories.js"></script>
</body>
</html>
