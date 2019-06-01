package org.taxreport.dao.jdbc;

import org.apache.log4j.Logger;
import org.taxreport.dao.ClientDao;
import org.taxreport.dao.DaoPool;
import org.taxreport.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

    private static final String SELECT_BY_EMAIL = "SELECT * FROM clients WHERE email = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM clients WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM clients";
    private static final String INSERT_INDIVIDUAL = "INSERT INTO clients (email, password, user_type_id, first_name, last_name, individual_number) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_LEGAL = "INSERT INTO clients (email, password, user_type_id, legal_name, enterprise_code) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_INDIVIDUAL = "UPDATE clients SET email = ?, password = ?, user_type_id =?, first_name = ?, last_name = ?, individual_number = ? WHERE (id = ?)";
    private static final String UPDATE_LEGAL = "UPDATE clients SET email = ?, password = ?, user_type_id =?, legal_name = ?, enterprise_code = ? WHERE (id = ?)";
    private static final String DELETE_FROM_REPORTS = "DELETE FROM reports WHERE client_id = ?";
    private static final String DELETE_FROM_CLIENTS = "DELETE FROM clients WHERE id = ?";
    private final Logger LOGGER = Logger.getLogger(getClass());

    private DaoPool daoPool;

    public ClientDaoImpl(DaoPool daoPool) {
        this.daoPool = daoPool;
    }

    @Override
    public Optional<Client> getByEmail(String email) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return Optional.of(getFromResultSet(resultSet).get(0));

        } catch (SQLException e) {
            LOGGER.error(e);
        }
        finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public void create(Client client) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            if (client instanceof Individual) {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INDIVIDUAL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, client.getEmail());
                preparedStatement.setString(2, client.getPassword());
                preparedStatement.setLong(3, daoPool.getUserTypeDao().getIdByType(Client.INDIVIDUAL).get());
                preparedStatement.setString(4, ((Individual) client).getFirstName());
                preparedStatement.setString(5, ((Individual) client).getLastName());
                preparedStatement.setString(6, ((Individual) client).getTaxpayerNumber());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating individual failed, no rows affected.");
                }
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating individual failed, no ID obtained.");
                }
            } else if (client instanceof LegalEntity) {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LEGAL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, client.getEmail());
                preparedStatement.setString(2, client.getPassword());
                preparedStatement.setLong(3, daoPool.getUserTypeDao().getIdByType(Client.LEGAL_ENTITY).get());
                preparedStatement.setString(4, ((LegalEntity) client).getLegalName());
                preparedStatement.setString(5, ((LegalEntity) client).getEnterpriseCode());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating legal entity failed, no rows affected.");
                }
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating legal entity failed, no ID obtained.");
                }
            }
        } catch (NoSuchElementException | SQLException e) {
            LOGGER.error(e);
        }
        finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public Optional<Client> getById(Long id) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return Optional.of(getFromResultSet(resultSet).get(0));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
        return Optional.empty();
    }

    @Override
    public List<Client> getAll() {
        Connection connection = daoPool.getConnectionPool().getConnection();
        List<Client> clientList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            clientList = getFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
        return clientList;
    }

    @Override
    public void update(Client client) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            if (client instanceof Individual) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INDIVIDUAL);
                preparedStatement.setString(1, client.getEmail());
                preparedStatement.setString(2, client.getPassword());
                preparedStatement.setLong(3, daoPool.getUserTypeDao().getIdByType(Client.INDIVIDUAL).get());
                preparedStatement.setString(4, ((Individual) client).getFirstName());
                preparedStatement.setString(5, ((Individual) client).getLastName());
                preparedStatement.setString(6, ((Individual) client).getTaxpayerNumber());
                preparedStatement.setLong(7, client.getId());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Updating individual failed, no rows affected.");
                }
            } else if (client instanceof LegalEntity) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LEGAL);
                preparedStatement.setString(1, client.getEmail());
                preparedStatement.setString(2, client.getPassword());
                preparedStatement.setLong(3, daoPool.getUserTypeDao().getIdByType(Client.LEGAL_ENTITY).get());
                preparedStatement.setString(4,  ((LegalEntity) client).getLegalName());
                preparedStatement.setString(5, ((LegalEntity) client).getEnterpriseCode());
                preparedStatement.setLong(6, client.getId());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Updating legal entity failed, no rows affected.");
                }
            }
        } catch (NoSuchElementException | SQLException e) {
            LOGGER.error(e);
        }
        finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public void delete(Client client) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement deleteFromReportsPS = connection.prepareStatement(DELETE_FROM_REPORTS);
            deleteFromReportsPS.setLong(1, client.getId());
            deleteFromReportsPS.executeUpdate();
            PreparedStatement deleteFromClientsPS = connection.prepareStatement(DELETE_FROM_CLIENTS);
            deleteFromClientsPS.setLong(1, client.getId());
            int affectedRows = deleteFromClientsPS.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting client failed, no rows affected.");
            }
            connection.commit();

        } catch (SQLException e) {
            LOGGER.error(e);
        }
        finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
    }

    private List<Client> getFromResultSet(ResultSet resultSet) {
        List<Client> clients = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Long userTypeId = resultSet.getLong("user_type_id");
                UserType userType = daoPool.getUserTypeDao().getById(userTypeId).get();
                Client author = null;
                List<TaxReport> taxReports = daoPool.getTaxReportDao().getByClientIdWithDummyAuthor(id, author);
                if (userType.getUserType().equals(Client.INDIVIDUAL)) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String number = resultSet.getString("individual_number");
                    author = new Individual(id, email, password, taxReports, firstName, lastName, number);
                    for (TaxReport report : taxReports) {
                        report.setAuthor(author);
                    }
                    clients.add(author);
                } else if (userType.getUserType().equals(Client.LEGAL_ENTITY)) {
                    String name = resultSet.getString("legal_name");
                    String number = resultSet.getString("enterprise_code");
                    author = new LegalEntity(id, email, password, taxReports, name, number);
                    for (TaxReport report : taxReports) {
                        report.setAuthor(author);
                    }
                    clients.add(author);
                }
            }
        } catch (NoSuchElementException | SQLException e) {
            LOGGER.error(e);
        }
        return clients;
    }
}
