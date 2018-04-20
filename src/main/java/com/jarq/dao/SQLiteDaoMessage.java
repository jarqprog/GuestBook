package com.jarq.dao;

import com.jarq.database.DatabaseManager;
import com.jarq.model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDaoMessage implements IDaoMessage {

    private final DatabaseManager databaseManager;

    public static IDaoMessage create(DatabaseManager databaseManager) {
        return new SQLiteDaoMessage(databaseManager);
    }


    private SQLiteDaoMessage(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean write(List<Message> messages) throws SQLException {

        return false;
    }

    @Override
    public List<Message> importAll() throws SQLException {
        Connection con = databaseManager.getConnection();

        String query = "SELECT * FROM messages;";
        List<Message> messages = new ArrayList<>();

        try (
                PreparedStatement statement = con.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery() ) {

            int id;
            String guestName;
            String message;

            int ID_INDEX = 1;
            int GUEST_NAME_INDEX = 2;
            int MESSAGE_INDEX = 3;

            while (resultSet.next()) {

                id = resultSet.getInt(ID_INDEX);
                guestName = resultSet.getString(GUEST_NAME_INDEX);
                message = resultSet.getString(MESSAGE_INDEX);

                messages.add(new Message(id, guestName, message));
            }
        } catch (SQLException ex) {
            throw new SQLException("Problem with database occurred: " + ex.getMessage());
        }
        return messages;
    }
}
