package org.taxreport.dao.jdbc;

import org.apache.log4j.Logger;
import org.taxreport.dao.DaoPool;
import org.taxreport.dao.PersonnelDao;
import org.taxreport.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PersonnelDaoImpl implements PersonnelDao {

    private static final String SELECT_BY_ID = "SELECT * FROM personnel WHERE id = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM personnel WHERE email = ?";
    private static final String SELECT_ALL = "SELECT * FROM personnel";
    private static final String INSERT_ADMIN = "INSERT INTO personnel (email, password, user_type_id, name, admin_badge) VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_INSPECTOR = "INSERT INTO personnel (email, password, user_type_id, name, inspector_badge, description) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ADMIN = "UPDATE personnel SET email = ?, password = ?, user_type_id =?, name = ?, admin_badge = ? WHERE (id = ?)";
    private static final String UPDATE_INSPECTOR = "UPDATE personnel SET email = ?, password = ?, user_type_id =?, name = ?, inspector_badge = ?, description = ? WHERE (id = ?)";
    @SuppressWarnings("SqlNoDataSourceInspection")
    private static final String SELECT_ID_LIST = "SELECT * FROM personnel WHERE id IN ";
    private static final String UPDATE_REPORTS_ON_DELETING = "UPDATE reports SET inspector_id = ? WHERE inspector_id = ?";
    private static final String DELETE_FROM_REJECTED = "DELETE FROM rejected_inspectors WHERE inspector_id = ?";
    private static final String DELETE_FROM_PERSONNEL = "DELETE FROM personnel WHERE id = ?";
    private final Logger LOGGER = Logger.getLogger(getClass());

    private DaoPool daoPool;

    public PersonnelDaoImpl(DaoPool daoPool) {

        this.daoPool = daoPool;
    }

    @Override
    public Optional<Personnel> getByEmail(String email) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Personnel> result = getFromResultSet(resultSet);
            if (!result.isEmpty()) {
                return Optional.of(getFromResultSet(resultSet).get(0));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public void create(Personnel personnel) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            if (personnel instanceof Admin) {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADMIN, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, personnel.getEmail());
                preparedStatement.setString(2, personnel.getPassword());
                preparedStatement.setLong(3, daoPool.getUserTypeDao().getIdByType(Personnel.ADMIN).get());
                preparedStatement.setString(4, personnel.getName());
                preparedStatement.setString(5, personnel.getBadge());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating admin failed, no rows affected.");
                }
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    personnel.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating admin failed, no ID obtained.");
                }
            } else if (personnel instanceof Inspector) {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INSPECTOR, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, personnel.getEmail());
                preparedStatement.setString(2, personnel.getPassword());
                preparedStatement.setLong(3, daoPool.getUserTypeDao().getIdByType(Client.LEGAL_ENTITY).get());
                preparedStatement.setString(4, personnel.getName());
                preparedStatement.setString(5, personnel.getBadge());
                preparedStatement.setString(6, ((Inspector) personnel).getDescription());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating inspector failed, no rows affected.");
                }
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    personnel.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating inspector failed, no ID obtained.");
                }
            }
        } catch (NoSuchElementException | SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public Optional<Personnel> getById(Long id) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return Optional.of(getFromResultSet(resultSet).get(0));
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
        return Optional.empty();
    }


    @Override
    public List<Personnel> getAll() {
        Connection connection = daoPool.getConnectionPool().getConnection();
        List<Personnel> personnelList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            personnelList = getFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
        return personnelList;
    }

    @Override
    public void update(Personnel personnel) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            if (personnel instanceof Admin) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADMIN);
                preparedStatement.setString(1, personnel.getEmail());
                preparedStatement.setString(2, personnel.getPassword());
                preparedStatement.setLong(3, daoPool.getUserTypeDao().getIdByType(Personnel.ADMIN).get());
                preparedStatement.setString(4, personnel.getName());
                preparedStatement.setString(5, personnel.getBadge());
                preparedStatement.setLong(6, personnel.getId());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Updating admin failed, no rows affected.");
                }
            } else if (personnel instanceof Inspector) {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INSPECTOR);
                preparedStatement.setString(1, personnel.getEmail());
                preparedStatement.setString(2, personnel.getPassword());
                preparedStatement.setLong(3, daoPool.getUserTypeDao().getIdByType(Personnel.INSPECTOR).get());
                preparedStatement.setString(4, personnel.getName());
                preparedStatement.setString(5, personnel.getBadge());

                preparedStatement.setString(6, ((Inspector) personnel).getDescription());
                preparedStatement.setLong(7, personnel.getId());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Updating inspector failed, no rows affected.");
                }
            }
        } catch (NoSuchElementException | SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }
    }

    @Override
    public void delete(Personnel personnel) {
        Connection connection = daoPool.getConnectionPool().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement updateReports = connection.prepareStatement(UPDATE_REPORTS_ON_DELETING);
            updateReports.setLong(1, daoPool.getPersonnelDao().getByEmail("default@taxreport.org").get().getId());
            updateReports.setLong(2, personnel.getId());
            updateReports.executeUpdate();
            PreparedStatement deleteFromRejected = connection.prepareStatement(DELETE_FROM_REJECTED);
            deleteFromRejected.setLong(1, personnel.getId());
            deleteFromRejected.executeUpdate();
            PreparedStatement deleteFromPersonnel = connection.prepareStatement(DELETE_FROM_PERSONNEL);
            deleteFromPersonnel.setLong(1, personnel.getId());
            int affectedRows = deleteFromPersonnel.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting personnel failed, no rows affected.");
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            daoPool.getConnectionPool().releaseConnection(connection);
        }

    }

    @Override
    public List<Personnel> getByIdList(List<Long> ids) {
        List<Personnel> personnelList = new ArrayList<>();
        if (ids != null && !ids.isEmpty()) {
            Connection connection = daoPool.getConnectionPool().getConnection();
            StringBuilder inSql = new StringBuilder("(");
            ids.forEach(id -> inSql
                    .append(id)
                    .append(", "));
            inSql.deleteCharAt(inSql.lastIndexOf(","));
            inSql.append(")");
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_LIST + inSql.toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    Long typeId = resultSet.getLong("user_type_id");
                    String name = resultSet.getString("name");
                    UserType type = daoPool.getUserTypeDao().getById(typeId).get();
                    if (type.getUserType().equals("INSPECTOR")) {
                        String badge = resultSet.getString("inspector_badge");
                        String description = resultSet.getString("description");
                        personnelList.add(new Inspector(id, email, password, name, badge, description));
                    } else if (type.getUserType().equals("ADMIN")) {
                        String badge = resultSet.getString("admin_badge");
                        personnelList.add(new Admin(id, email, password, name, badge));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                daoPool.getConnectionPool().releaseConnection(connection);
            }
        }
        return personnelList;
    }

    private List<Personnel> getFromResultSet(ResultSet resultSet) {
        List<Personnel> personnelList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Long userTypeId = resultSet.getLong("user_type_id");
                UserType userType = daoPool.getUserTypeDao().getById(userTypeId).get();
                if (userType.getUserType().equals(Personnel.ADMIN)) {
                    String name = resultSet.getString("name");
                    String badge = resultSet.getString("admin_badge");
                    personnelList.add(new Admin(id, email, password, name, badge));
                } else if (userType.getUserType().equals(Personnel.INSPECTOR)) {
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String badge = resultSet.getString("inspector_badge");
                    personnelList.add(new Inspector(id, email, password, name, badge, description));
                }
            }
        } catch (NoSuchElementException | SQLException e) {
            LOGGER.error(e);
        }
        return personnelList;
    }
}
