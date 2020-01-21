package auth;

import auxiliary.WorkWithUsers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        String path = req.getContextPath() + "/index.jsp";
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        if(WorkWithUsers.isAuthDataValid(login, pass)){
            HttpSession session = req.getSession();
            session.setAttribute("userName", login);

            String path = req.getContextPath() + "/main";
            resp.sendRedirect(path);
        }

        else{
            req.setAttribute("status", "notSuccess");

            ServletContext servletContext = getServletContext();
            String path = req.getContextPath() + "/index.jsp";
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
            requestDispatcher.forward(req, resp);
        }
    }
}
