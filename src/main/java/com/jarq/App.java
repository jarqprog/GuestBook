package com.jarq;


import com.jarq.server.MyServer;

import java.io.IOException;

public class App {

    public static void main(String[] args) {

        final int PORT = 8080;

        try {
            MyServer.create(PORT).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}