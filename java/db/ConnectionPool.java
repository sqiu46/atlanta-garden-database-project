package main.java.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    private ComboPooledDataSource dataSource;
    private static ConnectionPool connectionPool;

    public ConnectionPool() {
        try {
            dataSource = new ComboPooledDataSource();

            dataSource.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
            dataSource.setJdbcUrl("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_team_58?zeroDateTimeBehavior=convertToNull");
            dataSource.setUser("cs4400_team_58");
            dataSource.setPassword("pqMWvchC");
        } catch(Exception e) {
            e.printStackTrace(System.err);
            throw new RuntimeException("Error connecting to the SQL server");
        }
    }

    public static ConnectionPool getInstance() {
        if (connectionPool != null) {
            return connectionPool;
        } else {
            connectionPool = new ConnectionPool();
            return connectionPool;
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
