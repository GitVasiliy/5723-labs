package controller;

import auxiliary.WorkWithDirectory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GenerateLinksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("userName") == null){
            String path = req.getContextPath() + "/login";
            resp.sendRedirect(path);
        }
        else {
            String fileForLink = req.getParameter("file");
            String nameOfDir = req.getParameter("directory");

            WorkWithDirectory.generateLinkKey(nameOfDir, fileForLink, (String)session.getAttribute("userName"));
            String path = req.getContextPath() + "/main";
            resp.sendRedirect(path);
        }
    }
}
