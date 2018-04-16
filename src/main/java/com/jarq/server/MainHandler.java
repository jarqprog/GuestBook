package com.jarq.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class MainHandler implements HttpHandler {

    private int visitsCounter = 0;


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        // get a template file to create Jtwig model:
        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/book.twig");

        JtwigModel model = getBasicJtwigModel(template, httpExchange);


        model.with("formData", getFormContent());
        model.with("messagesData", getMessages());


        // render a template to a string
        String response = template.render(model);

        // send the results to a the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getHeaderData() {
        return "<a class=\"link\" href=\"./\" title=\"main\">\n" +
                "<div id=\"logo\">JQ's restaurant</div></a> - It's nice to feed You!\n" +
                "<p style=\"clear: both\"></p>";
    }

    private String getHeadData() {
        return "<link rel=\"stylesheet\" type=\"text/css\" href=\"static/css/style.css\">\n" +
                "<script src=\"static/js/script.js\"></script>";
    }

    private JtwigModel getBasicJtwigModel(JtwigTemplate template, HttpExchange he) {

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // fill the model with basic values
        model.with("headData", getHeadData());
        model.with("headerData", getHeaderData());
        model.with("visitsInfo", getVisitsInfo());
        return model;
    }

    private String getVisitsInfo() {
        visitsCounter ++;
        return "Page was visited: " + visitsCounter + " times!";
    }

    private String getFormContent() {
        return "        <div id=\"form-content\">\n" +
                "            <form>\n" +
                "                <div class=\"basic\"><label>Your name</label></div>\n" +
                "                <div class=\"basic\"><input type=\"text\" name=\"name\" maxlength=\"20\" required/></div>\n" +
                "                <div class=\"basic\"><label>message</label></div>\n" +
                "                <div class=\"basic\"><textarea name=\"message\" required></textarea></div>\n" +
                "                <div class=\"basic\"><input name=\"submit\" type=\"submit\" value=\"send\"></div>\n" +
                "            </form>\n" +
                "        </div>";
    }

    private String getMessages() {
        return "        <div id=\"messages\">\n" +
                "            <h2> our guests about us:</h2>\n" +
                "\n" +
                "        \n" +
                "        </div>";
    }
}
