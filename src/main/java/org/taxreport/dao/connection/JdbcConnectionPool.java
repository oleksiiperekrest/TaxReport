package org.taxreport.dao.connection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.taxreport.utils.ConnectionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class JdbcConnectionPool implements ConnectionPool {

    private static final int INITIAL_POOL_SIZE = 10;
    private static final int INCREMENT_SIZE = 5;
    private static final Logger LOGGER = Logger.getLogger(ConnectionUtil.class.getName());

    private static JdbcConnectionPool instance = null;
    //TODO constants. IS IT POSSIBLE?
    private String url;
    private String username;
    private String password;

    private List<Connection> connectionPool;
    private List<Connection> usedConnections;

    private JdbcConnectionPool() {
        Properties props = new Properties();
        try {
            props.load(ConnectionUtil.class.getResourceAsStream("/jdbc.properties"));

            url = props.getProperty("url");
            username = props.getProperty("username");
            password = props.getProperty("password");

            connectionPool = new ArrayList<>();
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                connectionPool.add(DriverManager.getConnection(url, username, password));
            }
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Could not read properties file: ", e);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Could not create connection: ", e);
        }
        usedConnections = new ArrayList<>();
    }

    public static JdbcConnectionPool getInstance() {
        if (instance == null) {
            instance = new JdbcConnectionPool();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        if (connectionPool.size() == 0) {
            increasePool();
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    private void increasePool() {
        try {
            LOGGER.info("Attempting to increase connection pool size.");
            for (int i = 0; i < INCREMENT_SIZE; i++) {
                connectionPool.add(DriverManager.getConnection(url, username, password));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Could not create connection: ", e);
        }
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }


}
