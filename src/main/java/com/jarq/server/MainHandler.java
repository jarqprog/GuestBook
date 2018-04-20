package com.jarq.server;

import com.jarq.controller.IMessageController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class MainHandler implements HttpHandler {

    private final IMessageController messageController;

    public static HttpHandler create(IMessageController messageController) {
        return new MainHandler(messageController);
    }

    private MainHandler(IMessageController messageController) {
        this.messageController = messageController;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        System.out.println(method);

        System.out.println(messageController.getMessages());

    }
}
