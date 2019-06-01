package org.taxreport.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public final class ConnectionUtil {

    private ConnectionUtil() {}

    public static Connection getConnection() throws SQLException {

        Properties props = new Properties();
        try {
            props.load(ConnectionUtil.class.getResourceAsStream("/jdbc.properties"));
        } catch (IOException e) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.ERROR,
                    "Could not read properties file: ", e);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }
}
