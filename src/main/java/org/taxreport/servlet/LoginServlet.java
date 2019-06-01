package org.taxreport.servlet;

import org.taxreport.dao.DaoPool;
import org.taxreport.dao.jdbc.DaoPoolImpl;
import org.taxreport.entity.User;
import org.taxreport.service.UserService;
import org.taxreport.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.taxreport.utils.MD5Util.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private DaoPool daoPool = DaoPoolImpl.getInstance();
    private UserService userService = new UserServiceImpl(daoPool);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userService.getByEmail(email).get();
        HttpSession session = req.getSession();
        if (userService.login(email, getMd5(password))) {

            session.setAttribute("role", user.getType());
            session.setAttribute("user", user);
            session.setAttribute("email", email);
            resp.sendRedirect("/welcome");
        }
        else {
            session.setAttribute("error", "login_failed");
            resp.sendRedirect("/login");
        }
    }
}
