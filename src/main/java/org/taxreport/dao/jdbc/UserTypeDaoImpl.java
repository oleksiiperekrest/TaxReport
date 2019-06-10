package org.taxreport.dao.jdbc;

import org.apache.log4j.Logger;
import org.taxreport.dao.DaoPool;
import org.taxreport.dao.UserTypeDao;
import org.taxreport.entity.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserTypeDaoImpl implements UserTypeDao {

    private static final String SELECT_ID_BY_STATUS = "SELECT id FROM user_type WHERE status = ?";
    private static final String SELECT_ALL = "SELECT * FROM user_type";
    private static final String SELECT_BY_ID = "SELECT type FROM user_type WHERE id = ?";
    private static final String CREATE = "INSERT INTO user_type (status) VALUES (?)";
    private static final String UPDATE = "UPDATE user_type SET type = ? WHERE (id = ?)";
    private static final String DELETE = "DELETE FROM user_type WHERE id = ?";
    private final Logger LOGGER = Logger.getLogger(getClass());

    private DaoPool daoPool;

    public UserTypeDaoImpl(DaoPool daoPool) {

        this.daoPool = daoPool;
    }

    @Override
    public Optional<Long> getIdByType(String type) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_BY_STATUS);
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
        return Optional.empty();
    }


    @Override
    public void create(UserType userType) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, userType.getUserType());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user type failed, no rows affected.");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userType.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public Optional<UserType> getById(Long id) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new UserType(id, resultSet.getString("type")));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
        return Optional.empty();
    }

    @Override
    public List<UserType> getAll() {
        Connection connection = daoPool.getConnectionPool().getConnection();
        List<UserType> userTypes = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String status = resultSet.getString("type");
                userTypes.add(new UserType(id, status));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
        return userTypes;
    }

    @Override
    public void update(UserType userType) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, userType.getUserType());
            preparedStatement.setLong(2, userType.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user type failed, no rows affected.");
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public void delete(UserType userType) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, userType.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user type failed, no rows affected.");
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
    }
}
