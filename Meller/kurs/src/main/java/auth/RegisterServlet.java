package auth;

import auxiliary.WorkWithUsers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            if (WorkWithUsers.findForbiddenSymbols(login).equals("Login is OK") &&
                    !WorkWithUsers.isUserRegistered(login)) {

                WorkWithUsers.addNewUser(login, password);
                String path = req.getContextPath() + "/login";
                resp.sendRedirect(path);

            } else {
                req.setAttribute("status", "notSuccess");

                ServletContext servletContext = getServletContext();
                String path = req.getContextPath() + "/startPages/register.jsp";
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
                requestDispatcher.forward(req, resp);
            }
    }
}
