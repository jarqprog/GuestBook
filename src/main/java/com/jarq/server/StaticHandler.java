package com.jarq.server;

import com.jarq.server.helpers.MimeTypeResolver;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.net.URL;

public class StaticHandler implements HttpHandler {

    public static HttpHandler create() {
        return new StaticHandler();
    }

    private StaticHandler() {}

    public void handle(HttpExchange httpExchange) throws IOException {

        // get file path from url
        URI uri = httpExchange.getRequestURI();

        // get file path from url
        String path = uri.getPath().substring(1);  // remove '/' for executable jar file purposes

        System.out.println("looking for: " + path);

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        URL fileURL = classLoader.getResource(path);

        if (inputStream == null) {

            System.out.println("Not found static");

            // Object does not exist or is not a file: reject with 404 error.
            send404(httpExchange);
        } else {

            System.out.println("Found it! static");


            // Object exists and is a file: accept with response code 200.
            sendFile(httpExchange, fileURL, inputStream);
        }
    }

    private void send404(HttpExchange httpExchange) throws IOException {
        String response = "404 (Not Found)\n";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
    }

    private void sendFile(HttpExchange httpExchange, URL fileURL, InputStream inputStream) throws IOException {
        // get the file
        File file = new File(fileURL.getFile());


        try {
            // we need to find out the mime type of the file, see: https://en.wikipedia.org/wiki/Media_type
            MimeTypeResolver resolver = new MimeTypeResolver(file);
            String mime = resolver.getMimeType();



            httpExchange.getResponseHeaders().set("Content-Type", mime);
            httpExchange.sendResponseHeaders(200, 0);

            OutputStream os = httpExchange.getResponseBody();

            System.out.println("jestem przy OS!");
            // send the file
            System.out.println("jestem przy FIS!");
            final byte[] buffer = new byte[0x10000];
            int count;
            while ((count = inputStream.read(buffer)) >= 0) {
                os.write(buffer, 0, count);
            }

            System.out.println("os: " + os.toString());
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException();
        }

    }
}