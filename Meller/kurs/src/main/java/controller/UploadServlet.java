package controller;

import auxiliary.WorkWithDirectory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;

@MultipartConfig
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("userName") == null){
            String path = req.getContextPath() + "/login";
            resp.sendRedirect(path);
        }
        else {
            Part file = req.getPart("file");
            String directoryForUpload = req.getParameter("dirForUpload");

            WorkWithDirectory.uploadFile(file, directoryForUpload);

            String path = req.getContextPath() + "/main";
            resp.sendRedirect(path);
        }
    }
}
