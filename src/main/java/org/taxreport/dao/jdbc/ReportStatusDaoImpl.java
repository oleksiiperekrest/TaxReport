package org.taxreport.dao.jdbc;

import org.apache.log4j.Logger;
import org.taxreport.dao.DaoPool;
import org.taxreport.dao.ReportStatusDao;
import org.taxreport.entity.ReportStatus;
import org.taxreport.dao.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportStatusDaoImpl implements ReportStatusDao {

    private static final String SELECT_ID_BY_STATUS = "SELECT id FROM report_status WHERE status = ?";
    private static final String SELECT_ALL = "SELECT * FROM report_status";
    private static final String SELECT_BY_ID = "SELECT status FROM report_status WHERE id = ?";
    private static final String CREATE = "INSERT INTO report_status (status) VALUES (?)";
    private static final String UPDATE = "UPDATE report_status SET status = ? WHERE (id = ?)";
    private static final String DELETE = "DELETE FROM report_status WHERE id = ?";
    private final Logger LOGGER = Logger.getLogger(getClass());

    private ConnectionPool connectionPool;
    private DaoPool daoPool;

    public ReportStatusDaoImpl(ConnectionPool connectionPool, DaoPool daoPool) {
        this.connectionPool = connectionPool;
        this.daoPool = daoPool;
    }

    @Override
    public Optional<Long> getIdByStatus(String status) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_BY_STATUS);
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return Optional.empty();
    }

    @Override
    public void create(ReportStatus reportStatus) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, reportStatus.getStatus());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating individual failed, no rows affected.");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                reportStatus.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Optional<ReportStatus> getById(Long id) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new ReportStatus(id, resultSet.getString("status")));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return Optional.empty();
    }

    @Override
    public List<ReportStatus> getAll() {
        Connection connection = connectionPool.getConnection();
        List<ReportStatus> reportStatusList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String status = resultSet.getString("status");
                reportStatusList.add(new ReportStatus(id, status));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return reportStatusList;
    }

    @Override
    public void update(ReportStatus reportStatus) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, reportStatus.getStatus());
            preparedStatement.setLong(2, reportStatus.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating report status failed, no rows affected.");
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void delete(ReportStatus reportStatus) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, reportStatus.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating report status failed, no rows affected.");
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
