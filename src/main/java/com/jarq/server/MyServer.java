package com.jarq.server;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class MyServer implements IServer {

    public static IServer create(int port) {
        return new MyServer(port);
    }

    private final int PORT;

    private MyServer(int port) {
        PORT = port;
    }

    @Override
    public void run() throws IOException {

        System.out.println("Starting server using port: " + PORT);

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // set routes
        server.createContext("/static", new StaticHandler());
        server.createContext("/", new MainHandler());
        server.setExecutor(null);

        // start listening
        server.start();
    }
}
