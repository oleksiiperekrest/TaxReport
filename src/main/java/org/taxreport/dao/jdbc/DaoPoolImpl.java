package org.taxreport.dao.jdbc;

import org.taxreport.dao.*;
import org.taxreport.dao.connection.ConnectionPool;
import org.taxreport.dao.connection.JdbcConnectionPool;

public final class DaoPoolImpl implements DaoPool {
    private final ConnectionPool connectionPool;
    private final ClientDao clientDao;
    private final PersonnelDao personnelDao;
    private final ReportStatusDao reportStatusDao;
    private final TaxReportDao taxReportDao;
    private final UserTypeDao userTypeDao;

    private static DaoPoolImpl instance = null;

    private DaoPoolImpl () {
        this.connectionPool = JdbcConnectionPool.getInstance();
        this.clientDao = new ClientDaoImpl(this);
        this.personnelDao = new PersonnelDaoImpl(this);
        this.reportStatusDao = new ReportStatusDaoImpl(this);
        this.taxReportDao = new TaxReportDaoImpl(this);
        this.userTypeDao = new UserTypeDaoImpl(this);

    }

    public static DaoPoolImpl getInstance() {
        if (instance == null) {
            instance = new DaoPoolImpl();
        }
        return instance;
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
