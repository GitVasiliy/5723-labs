package controller;

import auxiliary.WorkWithDirectory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SharingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("userName") == null){
            String path = req.getContextPath() + "/login";
            resp.sendRedirect(path);
        }
        else {
            String dirForSharing = (String) req.getSession().getAttribute("userName");
            String newUser = req.getParameter("user");
            String mod = req.getParameter("modification");

            if (!WorkWithDirectory.hasUserThisDirectory(newUser, dirForSharing)) {
                WorkWithDirectory.addDirectoryToUser(dirForSharing, newUser, mod);
            }

            String path = req.getContextPath() + "/main";
            resp.sendRedirect(path);
        }
    }
}
