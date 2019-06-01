package org.taxreport.dao.jdbc;

import org.apache.log4j.Logger;
import org.taxreport.dao.DaoPool;
import org.taxreport.dao.UserTypeDao;
import org.taxreport.entity.UserType;
import org.taxreport.dao.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserTypeDaoImpl implements UserTypeDao {


    public static final String SELECT_ID = "SELECT id FROM user_type WHERE status = ?";
    private final Logger LOGGER = Logger.getLogger(getClass());

    private ConnectionPool connectionPool;
    private DaoPool daoPool;

    public UserTypeDaoImpl(ConnectionPool connectionPool, DaoPool daoPool) {
        this.connectionPool = connectionPool;
        this.daoPool = daoPool;
    }

    @Override
    public Optional<Long> getIdByType(String type) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID);
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        finally {
            connectionPool.releaseConnection(connection);
        }
        return Optional.empty();
    }


    @Override
    public void create(UserType userType) {

    }

    @Override
    public Optional<UserType> getById(Long id) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT type FROM user_type WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new UserType(id, resultSet.getString("type")));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return Optional.empty();
    }

    @Override
    public List<UserType> getAll() {
        return null;
    }

    @Override
    public void update(UserType userType) {

    }

    @Override
    public void delete(UserType userType) {

    }
}
