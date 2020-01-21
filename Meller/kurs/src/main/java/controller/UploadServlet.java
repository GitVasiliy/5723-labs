package controller;

import auxiliary.WorkWithDirectory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@MultipartConfig
public class UploadServlet extends HttpServlet {
    private final long MAX_SIZE_OF_FILE_IN_BYTES = 52428800;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("userName") == null){
            String path = req.getContextPath() + "/login";
            resp.sendRedirect(path);
        }
        else {
            Part file = req.getPart("file");

            if(file.getSize() <= MAX_SIZE_OF_FILE_IN_BYTES){
                String directoryForUpload = req.getParameter("dirForUpload");
                WorkWithDirectory.uploadFile(file, directoryForUpload);
            }
            String path = req.getContextPath() + "/main";
            resp.sendRedirect(path);
        }
    }
}
