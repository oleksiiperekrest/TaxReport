package org.taxreport.servlet.command;

import javax.servlet.ServletException;
import java.io.IOException;

public class UnknownCommand extends Command {
    @Override
    public void execute() throws ServletException, IOException {
        forward(ERROR);

    }
}
