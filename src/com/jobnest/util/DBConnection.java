package com.jobnest.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database Connection Utility Class
 * Manages JDBC connections to MySQL database
 */
public class DBConnection {
    
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static String DRIVER;
    
    // Static block to load database properties
    static {
        try {
            Properties props = new Properties();
            InputStream input = DBConnection.class.getClassLoader()
                .getResourceAsStream("db.properties");
            
            if (input == null) {
                throw new IOException("Unable to find db.properties");
            }
            
            props.load(input);
            
            URL = props.getProperty("db.url");
            USERNAME = props.getProperty("db.username");
            PASSWORD = props.getProperty("db.password");
            DRIVER = props.getProperty("db.driver");
            
            // Load MySQL JDBC driver
            Class.forName(DRIVER);
            
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    /**
     * Close database connection
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
