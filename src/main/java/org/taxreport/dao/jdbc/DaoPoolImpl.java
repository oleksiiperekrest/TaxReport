package org.taxreport.dao.jdbc;

import org.taxreport.dao.*;
import org.taxreport.dao.connection.ConnectionPool;
import org.taxreport.dao.connection.JdbcConnectionPool;

public final class DaoPoolImpl implements DaoPool {
    private final DaoPoolImpl INSTANCE;
    private final ConnectionPool connectionPool;
    private final ClientDao clientDao;
    private final PersonnelDao personnelDao;
    private final ReportStatusDao reportStatusDao;
    private final TaxReportDao taxReportDao;
    private final UserTypeDao userTypeDao;

    //TODO singleton

    public DaoPoolImpl () {
        this.connectionPool = new JdbcConnectionPool();
        this.clientDao = new ClientDaoImpl(this);
        this.personnelDao = new PersonnelDaoImpl(this);
        this.reportStatusDao = new ReportStatusDaoImpl(this);
        this.taxReportDao = new TaxReportDaoImpl(this);
        this.userTypeDao = new UserTypeDaoImpl(this);

        INSTANCE = this;
    }

    public DaoPoolImpl getInstance() {
        if (INSTANCE == null) {
            new DaoPoolImpl();
        }
        return INSTANCE;
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    public PersonnelDao getPersonnelDao() {
        return personnelDao;
    }

    public ReportStatusDao getReportStatusDao() {
        return reportStatusDao;
    }

    public TaxReportDao getTaxReportDao() {
        return taxReportDao;
    }

    public UserTypeDao getUserTypeDao() {
        return userTypeDao;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }
}
