package com.jarq.server;

import com.jarq.controller.IMessageController;
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
        System.out.println(method);

        if (method.equals("GET")) {
            System.out.println(method);
            String uri = httpExchange.getRequestURI().toString();
            System.out.println(uri);
            renderPage(httpExchange);

        }

        if (method.equals("POST")) {

            String uri = httpExchange.getRequestURI().toString();
            System.out.println("URI: " + uri);
            captureData(httpExchange);




        }

    }

    private void captureData(HttpExchange he) throws IOException {

        System.out.println("jestem w capture data");

    }

    private void renderPage(HttpExchange he) throws IOException {
        System.out.println("jestem w render page1");
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate(
                "static/templates/index.html");
//                "/static/index2.html");

        System.out.println("ustwaiłem model");
        JtwigModel model = JtwigModel.newModel();
//        model.with("user", "Jarek");

        System.out.println("zaczynam renderować");
        response = template.render(model);

        System.out.println("jestem w render page2!!!");

        executeResponse(he,response);

        System.out.println("wychodzę z render page");
    }

    private void executeResponse(HttpExchange he, String response) throws IOException {
        byte[] bytes = response.getBytes();
        he.sendResponseHeaders(200, bytes.length);

        System.out.println("jestem w executeResponse");

        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
