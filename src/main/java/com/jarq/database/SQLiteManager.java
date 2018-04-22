package com.jarq.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteManager implements DatabaseManager {

    private final String url;
    private Connection connection;

    public static DatabaseManager create(String driver, String url) throws ClassNotFoundException {
        return new SQLiteManager(driver, url);
    }


    private SQLiteManager(String driver, String url) throws ClassNotFoundException {
        Class.forName(driver);
        this.url = url;
    }

    @Override
    public Connection getConnection() {
        try {
            if(! isConnectionValid(connection) ) {
                connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("JDBC connection problem occurred!");
            System.exit(1);
        }
        return connection;
    }

    @Override
    public boolean closeConnection() {
        try {
            if( isConnectionValid(connection) ) {
                connection.close();

            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isConnectionValid(Connection connection) {
        try {
            return ( connection != null ) && (! connection.isClosed() );
        } catch (SQLException e) {
            return false;
        }
    }
}
