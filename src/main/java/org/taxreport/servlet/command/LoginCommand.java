package org.taxreport.servlet.command;

import org.apache.log4j.Logger;
import org.taxreport.dao.DaoPool;
import org.taxreport.dao.jdbc.DaoPoolImpl;
import org.taxreport.entity.User;
import org.taxreport.service.TaxReportService;
import org.taxreport.service.UserService;
import org.taxreport.service.impl.TaxReportServiceImpl;
import org.taxreport.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.taxreport.utils.MD5Util.getMd5;

public class LoginCommand extends Command {

    private DaoPool daoPool = DaoPoolImpl.getInstance();
    private UserService userService = new UserServiceImpl(daoPool);
    private TaxReportService taxReportService = new TaxReportServiceImpl(daoPool);
    private final Logger LOGGER = Logger.getLogger(getClass());


    @Override
    public void execute() throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (request.getMethod().equals("GET")) {
            if (session.getAttribute("role") != null) {
                forward(WELCOME);
            }
            try {
                forward(LOGIN);
            } catch (Exception e) {
                LOGGER.error(e);
                redirect(ERROR);
            }

        } else if (request.getMethod().equals("POST")) {

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = userService.getByEmail(email);
            if (userService.login(email, getMd5(password))) {

                session.setAttribute("role", user.getType());
                session.setAttribute("user", user);
                session.setAttribute("reports", taxReportService.getByClientId(user.getId()));
                session.setAttribute("email", email);
                try {
                    forward(WELCOME);
                } catch (Exception e) {
                    LOGGER.error(e);
                    redirect(ERROR);
                }
            } else {
                session.setAttribute("error", "login_failed");
                forward("login");
            }
        }

    }
}
