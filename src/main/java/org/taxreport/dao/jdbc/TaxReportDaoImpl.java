package org.taxreport.dao.jdbc;

import org.apache.log4j.Logger;
import org.taxreport.dao.*;
import org.taxreport.entity.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TaxReportDaoImpl implements TaxReportDao {
    private static final String SELECT_BY_ID = "SELECT * FROM reports WHERE id = ?";
    private static final String SELECT_REJECTED_INSPECTORS = "SELECT inspector_id FROM reports_rejected_inspectors WHERE report_id = ?";
    private static final String INSERT_INTO_REJECTED_INSPECTORS = "INSERT INTO reports_rejected_inspectors (report_id, inspector_id) VALUES ";
    private static final String SELECT_ALL = "SELECT * FROM reports";
    private static final String UPDATE = "UPDATE tax_reports SET content = ?, author_id = ?, status_id = ?, creation_datetime = ?, update_datetime = ?, inspector_id = ? WHERE (id = ?)";
    private static final String DELETE = "DELETE FROM tax_reports WHERE id = ?";
    private final Logger LOGGER = Logger.getLogger(getClass());
    private static final String INSERT = "INSERT INTO reports (content, author_id, status_id," +
            " creation_datetime, update_datetime, inspector_id) VALUES (?, ?, ?, ?, ?, ?);";

    private DaoPool daoPool;

    public TaxReportDaoImpl(DaoPool daoPool) {
        this.daoPool = daoPool;
    }

    @Override
    public void create(TaxReport taxReport) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, taxReport.getContent());
            preparedStatement.setLong(2, taxReport.getAuthor().getId());
            Optional<Long> idByStatus = daoPool.getReportStatusDao().getIdByStatus(taxReport.getReportStatus().getStatus());
            if (!idByStatus.isPresent()) {
                throw new SQLException("Report id not found in database");
            } else {
                preparedStatement.setLong(3, idByStatus.get());
            }
            preparedStatement.setTimestamp(4, Timestamp.valueOf(taxReport.getCreationTime()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(taxReport.getLastUpdatedTime()));
            preparedStatement.setLong(6, taxReport.getInspector().getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating tax report failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    taxReport.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating tax report failed, no ID obtained.");
                }
            }
            preparedStatement.close();
            if (!taxReport.getRejectedInspectors().isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder(INSERT_INTO_REJECTED_INSPECTORS);
                for (int i = 0; i < taxReport.getRejectedInspectors().size(); i++) {
                    stringBuilder
                            .append("('")
                            .append(taxReport.getId())
                            .append("', '")
                            .append(taxReport.getRejectedInspectors().get(i).getId())
                            .append("')");
                    if (i < taxReport.getRejectedInspectors().size() - 1) {
                        stringBuilder
                                .append(", ");
                    }
                }
                stringBuilder.append(";");
                Statement statement = connection.createStatement();
                int rowsAffected = statement.executeUpdate(stringBuilder.toString());
                if (rowsAffected == 0) {
                    throw new SQLException("Updating rejected_inspectors table failed, no rows affected.");
                }
                if (rowsAffected != taxReport.getRejectedInspectors().size()) {
                    throw new SQLException("Updating rejected_inspectors table failed, rows count doesn't match");
                }
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public Optional<TaxReport> getById(Long id) {

        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_BY_ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getFromResultSet(resultSet).get(0));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
        return Optional.empty();
    }

    private List<Personnel> getRejectedInspectors(Long id) {
        List<Personnel> rejectedInspectors = new ArrayList<>();
        Connection connection = daoPool.getConnectionPool().getConnection();
        List<Long> rejectedInspectorsIds = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REJECTED_INSPECTORS);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rejectedInspectorsIds.add(resultSet.getLong("inspector_id"));
            }
            rejectedInspectors = daoPool.getPersonnelDao().getByIdList(rejectedInspectorsIds);

        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }

        return rejectedInspectors;
    }

    @Override
    public List<TaxReport> getAll() {
        Connection connection = daoPool.getConnectionPool().getConnection();
        List<TaxReport> taxReports = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            taxReports = getFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
        return taxReports;
    }

    @Override
    public void update(TaxReport taxReport) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, taxReport.getContent());
            preparedStatement.setLong(2, taxReport.getAuthor().getId());
            preparedStatement.setLong(3, taxReport.getReportStatus().getId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(taxReport.getCreationTime()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(taxReport.getLastUpdatedTime()));
            preparedStatement.setLong(6, taxReport.getInspector().getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating tax report failed, no rows affected.");
            }

        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public void delete(TaxReport taxReport) {
        Connection connection = daoPool.getConnectionPool().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, taxReport.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting tax report failed, no rows affected.");
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public List<TaxReport> getByClientId(Long id) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        List<TaxReport> taxReports = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reports WHERE author_id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            taxReports = getFromResultSet(resultSet);

        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return taxReports;
    }

    @Override
    public List<TaxReport> getByClientIdWithDummyAuthor(Long id, Client author) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        List<TaxReport> taxReports = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reports WHERE author_id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            taxReports = getFromResultSetWithDummyAuthor(resultSet, author);

        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
        return taxReports;
    }

    private List<TaxReport> getFromResultSet(ResultSet resultSet) {
        List<TaxReport> taxReports = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Client author = daoPool.getClientDao().getById(resultSet.getLong("author_id")).get();
                String content = resultSet.getString("content");
                LocalDateTime creationTime = resultSet.getTimestamp("creation_datetime").toLocalDateTime();
                ReportStatus reportStatus = daoPool.getReportStatusDao().getById(resultSet.getLong("status_id")).get();
                Personnel inspector = daoPool.getPersonnelDao().getById(resultSet.getLong("inspector_id")).get();
                LocalDateTime lastUpdatedTime = resultSet.getTimestamp("update_datetime").toLocalDateTime();
                List<Personnel> rejectedInspectors = getRejectedInspectors(id);
                taxReports.add(new TaxReport(id, author, content, creationTime, reportStatus, inspector, rejectedInspectors, lastUpdatedTime));
            }
        } catch (NoSuchElementException | SQLException e) {
            LOGGER.error(e);

        }
        return taxReports;
    }

    private List<TaxReport> getFromResultSetWithDummyAuthor(ResultSet resultSet, Client author) {
        List<TaxReport> taxReports = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String content = resultSet.getString("content");
                LocalDateTime creationTime = resultSet.getTimestamp("creation_datetime").toLocalDateTime();
                ReportStatus reportStatus = daoPool.getReportStatusDao().getById(resultSet.getLong("status_id")).get();
                Personnel inspector = daoPool.getPersonnelDao().getById(resultSet.getLong("inspector_id")).get();
                LocalDateTime lastUpdatedTime = resultSet.getTimestamp("update_datetime").toLocalDateTime();
                List<Personnel> rejectedInspectors = getRejectedInspectors(id);
                taxReports.add(new TaxReport(id, author, content, creationTime, reportStatus, inspector, rejectedInspectors, lastUpdatedTime));
            }
        } catch (NoSuchElementException | SQLException e) {
            LOGGER.error(e);

        }
        return taxReports;
    }

}
