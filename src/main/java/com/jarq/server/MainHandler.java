package com.jarq.server;

import com.jarq.controller.IMessageController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

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
            switch (uri) {
                case "/":
                    renderPage(httpExchange);
                    break;
                case "/form":
                    renderForm(httpExchange);
                    break;
            }
        }

        if (method.equals("POST")) {
            switch (uri) {
                case "/form":
                    captureData(httpExchange);
                    break;
            }

        }

    }

    private void renderForm(HttpExchange httpExchange) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("/static/form.html");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        model.with("result", "");

        // render a template to a string
        String response = template.render(model);

        sendResponse(httpExchange, response);
    }

    private void captureData(HttpExchange httpExchange) throws IOException {

        Map<String,String> inputs = getInput(httpExchange);
        String guestName = inputs.get("guestName");
        String messageContent = inputs.get("messageContent");

        boolean isExportSuccess =  messageController.saveMessage(messageContent, guestName);

        String result;
        if(! isExportSuccess) {
            result = "problem occurred while adding Your message to repository, we will check it..";
        } else {
            result = "Your message has been successfully added to repository!";
        }

        JtwigTemplate template = JtwigTemplate.classpathTemplate("/static/form.html");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        model.with("result", result);

        // render a template to a string
        String response = template.render(model);

        sendResponse(httpExchange, response);
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

    private Map<String,String> getInput(HttpExchange httpExchange) throws IOException {

        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String data = br.readLine();
        Map<String,String> map = new HashMap<>();
        System.out.println("parser: " + data);
        String[] pairs = data.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = URLDecoder.decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

}
