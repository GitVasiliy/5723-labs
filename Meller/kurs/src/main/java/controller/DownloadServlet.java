package controller;

import auxiliary.WorkWithDirectory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String link = req.getParameter("link");

        if((session == null || session.getAttribute("userName") == null) && link == null){
            String path = req.getContextPath() + "/login";
            resp.sendRedirect(path);
        }
        else {
            PrintWriter writer = new PrintWriter(resp.getWriter());
            String fileForDownload = req.getParameter("file");
            String nameOfDir = req.getParameter("directory");

            String[] infoForDownload = new String[2];
            infoForDownload[0] = nameOfDir;
            infoForDownload[1] = fileForDownload;


            if(link != null){
                infoForDownload = WorkWithDirectory.prepareDownloadFileWithLink(link);
            }
            else if (!WorkWithDirectory.hasUserThisDirectory((String) session.getAttribute("userName"), nameOfDir) ||
                    !WorkWithDirectory.hasUserThisFile(fileForDownload, nameOfDir)) {
                String path = req.getContextPath() + "/main";
                resp.sendRedirect(path);
            }

            resp.setContentType("APPLICATION/OCTET-STREAM");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + infoForDownload[1] + "\"");

            WorkWithDirectory.downloadFile(infoForDownload[1], infoForDownload[0], writer);
        }
    }
}
