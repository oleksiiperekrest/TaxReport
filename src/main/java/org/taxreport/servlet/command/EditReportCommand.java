package org.taxreport.servlet.command;

import org.taxreport.dao.DaoPool;
import org.taxreport.dao.jdbc.DaoPoolImpl;
import org.taxreport.entity.TaxReport;
import org.taxreport.service.TaxReportService;
import org.taxreport.service.impl.TaxReportServiceImpl;

import javax.servlet.ServletException;
import java.io.IOException;

public class EditReportCommand extends Command {


    DaoPool daoPool = DaoPoolImpl.getInstance();
    TaxReportService taxReportService = new TaxReportServiceImpl(daoPool);

    @Override
    public void execute() throws ServletException, IOException {
        if (request.getMethod().equals("GET")) {
            long id = Long.parseLong(request.getParameter("id"));
            TaxReport report = taxReportService.getById(id);
            context.setAttribute("report", report);
            forward("edit_report");
        } else if (request.getMethod().equals("POST")) {

        }

    }
}
