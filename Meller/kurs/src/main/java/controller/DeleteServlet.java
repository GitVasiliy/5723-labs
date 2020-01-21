package controller;

import auxiliary.WorkWithDirectory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("userName") == null){
            String path = req.getContextPath() + "/login";
            resp.sendRedirect(path);
        }
        else {
            String fileForDelete = req.getParameter("file");
            String nameOfDir = req.getParameter("directory");
            String userName = (String) session.getAttribute("userName");

            if(WorkWithDirectory.isDirectoryEditable(userName ,nameOfDir)) {
                WorkWithDirectory.doDelete(fileForDelete, nameOfDir);
            }

            String path = req.getContextPath() + "/main";
            resp.sendRedirect(path);
        }
    }
}
