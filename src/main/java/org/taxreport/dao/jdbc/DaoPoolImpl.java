package org.taxreport.dao.jdbc;

import org.taxreport.dao.*;
import org.taxreport.dao.connection.ConnectionPool;
import org.taxreport.dao.connection.JdbcConnectionPool;

public final class DaoPoolImpl implements DaoPool {
    private final DaoPoolImpl INSTANCE;
    private final ClientDao clientDao;
    private final PersonnelDao personnelDao;
    private final ReportStatusDao reportStatusDao;
    private final TaxReportDao taxReportDao;
    private final UserTypeDao userTypeDao;

    //TODO singleton
    //todo connection pool goes to dao pool

    public DaoPoolImpl () {
        ConnectionPool connectionPool = new JdbcConnectionPool();
        this.clientDao = new ClientDaoImpl(connectionPool, this);
        this.personnelDao = new PersonnelDaoImpl(connectionPool, this);
        this.reportStatusDao = new ReportStatusDaoImpl(connectionPool, this);
        this.taxReportDao = new TaxReportDaoImpl(connectionPool, this);
        this.userTypeDao = new UserTypeDaoImpl(connectionPool, this);

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
}
