package org.taxreport.dao;

import org.taxreport.dao.connection.ConnectionPool;

public interface DaoPool {
    ClientDao getClientDao();

    PersonnelDao getPersonnelDao();

    ReportStatusDao getReportStatusDao();

    TaxReportDao getTaxReportDao();

    UserTypeDao getUserTypeDao();

    ConnectionPool getConnectionPool();

}
