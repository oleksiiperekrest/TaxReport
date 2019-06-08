package org.taxreport.servlet.command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Command {

    protected static final String LOGIN = "login";
    protected static final String WELCOME = "welcome";
    protected static final String ERROR = "error";
    protected static final String LOGOUT = "logout";

    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public void init(ServletContext servletContext,
                        HttpServletRequest servletRequest,
                        HttpServletResponse servletResponse) {

        this.context = servletContext;
        this.request = servletRequest;
        this.response = servletResponse;

    }
    public abstract void execute() throws ServletException, IOException;

    protected void forward(String target) throws ServletException, IOException {
//        target = String.format("/%s", target);
        target = String.format("/jsp/%s.jsp", target);
        RequestDispatcher dispatcher = context.getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }

    protected void redirect(String target) throws IOException {
        response.sendRedirect(String.format("/%s", target));
//        response.sendRedirect(String.format("/jsp/%s.jsp", target));

    }
}
