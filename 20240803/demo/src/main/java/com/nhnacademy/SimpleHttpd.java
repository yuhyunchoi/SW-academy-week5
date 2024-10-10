package com.nhnacademy;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.io.File;

public class SimpleHttpd {
    
    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 80;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new RootHandler());

        server.setExecutor(null);
        System.out.println("Server is starting on port " + port);
        server.start();
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                File currentDir = new File("."); 
                String[] filesList = currentDir.list();
                StringBuilder response = new StringBuilder();

                response.append("<html><body>");
                response.append("<h1>Directory Listing</h1>");
                response.append("<ul>");
                
                for (String fileName : filesList) {
                    response.append("<li>").append(fileName).append("</li>");
                }

                response.append("</ul>");
                response.append("</body></html>");

                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.toString().getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); 
            }
        }
    }
}
