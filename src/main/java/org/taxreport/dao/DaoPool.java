package org.taxreport.dao;

public interface DaoPool {
    ClientDao getClientDao();

    PersonnelDao getPersonnelDao();

    ReportStatusDao getReportStatusDao();

    TaxReportDao getTaxReportDao();

    UserTypeDao getUserTypeDao();

}
