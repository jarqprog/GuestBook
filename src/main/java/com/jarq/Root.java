package com.jarq;

import com.jarq.controller.IMessageController;
import com.jarq.controller.WebMessageController;
import com.jarq.dao.IDaoMessage;
import com.jarq.dao.SQLiteDaoMessage;
import com.jarq.database.DatabaseManager;
import com.jarq.database.SQLiteManager;
import com.jarq.server.IServer;
import com.jarq.server.MainHandler;
import com.jarq.server.MyServer;
import com.jarq.server.StaticHandler;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;


public class Root implements Client {


    public static Client create() {
        return new Root();
    }

    private Root() {}


    @Override
    public void run() {

        int port = 8002;
        String databaseDriver = "org.sqlite.JDBC";
        String databaseUrl = "jdbc:sqlite:src/main/resources/db/msg.db";

        try {

            // initialize objects

            DatabaseManager dbManager = SQLiteManager.create(databaseDriver, databaseUrl);
            IDaoMessage daoMessage = SQLiteDaoMessage.create(dbManager);
            IMessageController messageController = WebMessageController.create(daoMessage);

            HttpHandler staticHandler = StaticHandler.create();
            HttpHandler mainHandler = MainHandler.create(messageController);

            IServer server = MyServer.create(port, staticHandler, mainHandler);

            try {
                server.run();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Database problem occurred, shutting down app..");
            System.exit(-1);
        }
    }
}
