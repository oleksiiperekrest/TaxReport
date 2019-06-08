package org.taxreport.servlet;

import org.apache.log4j.Logger;
import org.taxreport.servlet.command.Command;
import org.taxreport.servlet.command.UnknownCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/login", "/add_report", "/edit_report", "/", "/error"})
public class Servlet extends HttpServlet {
    private final Logger LOGGER = Logger.getLogger(getClass());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Command command = getCommand(req);
            command.init(getServletContext(), req, resp);
            command.execute();
        } catch (Exception e) {
            LOGGER.error(e);
            resp.sendRedirect("jsp/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Command command = getCommand(req);
            command.init(getServletContext(), req, resp);
            command.execute();

        } catch (Exception e) {
            LOGGER.error(e);
            resp.sendRedirect("jsp/error.jsp");
        }
    }

    private Command getCommand(HttpServletRequest request) {
        try {
            Class type = Class.forName(String.format(
                    "org.taxreport.servlet.command.%sCommand",
                    request.getParameter("command")));
            return (Command) type
                    .asSubclass(Command.class)
                    .newInstance();
        } catch (Exception e) {
            LOGGER.error(e);
            return new UnknownCommand();
        }
    }
}
