package com.jarq.server;

import com.jarq.controller.IMessageController;
import com.jarq.model.Message;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

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
        System.out.println("method: " + method);


        String uri = httpExchange.getRequestURI().toString();
        System.out.println("uri: " + uri);

        if (method.equals("GET")) {
            renderPage(httpExchange);
        }

        if (method.equals("POST")) {

            renderPage(httpExchange);
            captureData(httpExchange);

        }

    }

    private void captureData(HttpExchange httpExchange) throws IOException {

        System.out.println("jestem w capture data");

    }

    private void renderPage(HttpExchange httpExchange) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("/static/main.html");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        model.with("messages", messageController.getMessages());

        // render a template to a string
        String response = template.render(model);

        sendResponse(httpExchange, response);
    }


    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        // send the results to a the client
        byte[] bytes = response.getBytes();
        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

}
