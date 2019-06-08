package org.taxreport.servlet.command;

import javax.servlet.ServletException;
import java.io.IOException;

public class LogoutCommand extends Command {
    @Override
    public void execute() throws ServletException, IOException {
        request.getSession().invalidate();
        forward(LOGOUT);
    }
}
