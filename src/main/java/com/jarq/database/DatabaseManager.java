package com.jarq.database;

import java.sql.Connection;

public interface DatabaseManager {

    Connection getConnection();
    boolean closeConnection();
    boolean isConnectionValid(Connection connection);

}
