package org.taxreport.servlet.command;

import org.taxreport.dao.DaoPool;
import org.taxreport.dao.jdbc.DaoPoolImpl;
import org.taxreport.service.TaxReportService;
import org.taxreport.service.impl.TaxReportServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreateReportCommand extends Command {


    @Override
    public void execute() throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        DaoPool daoPool = DaoPoolImpl.getInstance();
        TaxReportService taxReportService = new TaxReportServiceImpl(daoPool);
//        return "jsp/add_report.jsp";
    }
}
