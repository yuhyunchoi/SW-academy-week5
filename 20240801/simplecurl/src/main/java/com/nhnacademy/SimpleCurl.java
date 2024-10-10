package com.nhnacademy;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.OutputStreamWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class SimpleCurl {


    private static void sendRequest(Socket socket, String requestLine, Map<String, String> headers, String data) throws IOException{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
     
        writer.write(requestLine + "\r\n");

        for(Map.Entry<String, String> entry : headers.entrySet()){
            writer.write(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }
        writer.write("\r\n");

        if(data != null && !data.isEmpty()){
            writer.write(data);
            writer.write("\r\n");
        }

        writer.flush();
    }

    private static void readResponse(Socket socket, boolean verbose) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        StringBuilder responseHeaders = new StringBuilder();
        StringBuilder responseBody = new StringBuilder();
        boolean inHeaders = true;

        while((line = reader.readLine()) != null){
            if(line.isEmpty()){
                inHeaders = false;
            }

            if(inHeaders){
                responseHeaders.append(line).append("\n");
            }else{
                responseBody.append(line).append("\n");
            }
        }
        if(verbose){
            System.out.println("Response Headers: \n" + responseHeaders.toString());
        }

        System.out.println("Response Body : \n" + responseBody.toString());
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("v", "verbose", false, "Print request and response headers");
        options.addOption("X", "request", true, "Specify request command to use (GET, POST, etc.)");
        options.addOption("H", "header", true, "Pass custom header(s) to server");
        options.addOption("d", "data", true, "Send data with request");
        options.addOption("h", "help", false, "Display help");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("scurl", options);
            return;
        }

        if (cmd.hasOption("h")) {
            formatter.printHelp("scurl", options);
            return;
        }

        boolean verbose = cmd.hasOption("v");
        String method = cmd.getOptionValue("X", "GET").toUpperCase();
        String data = cmd.getOptionValue("d", "data");
        String[] customHeaders = cmd.getOptionValues("H");
        String urlString = cmd.getArgs().length > 0 ? cmd.getArgs()[0] : null;

        if (urlString == null) {
            System.out.println("Error: URL is required");
            return;
        }

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);

            if (customHeaders != null) {
                for (String header : customHeaders) {
                    String[] headerParts = header.split(":", 2);
                    if (headerParts.length == 2) {
                        connection.setRequestProperty(headerParts[0].trim(), headerParts[1].trim());
                    }
                }
            }

            if (data != null && (method.equals("POST") || method.equals("PUT"))) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(data.getBytes());
                    os.flush();
                }
            }

            // Print request headers if verbose
            if (verbose) {
                System.out.println("Request Headers:");
                connection.getRequestProperties().forEach((key, value) -> System.out.println(key + ": " + String.join(", ", value)));
            }

            // Get the response
            int responseCode = connection.getResponseCode();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }

            // Print response headers if verbose
            if (verbose) {
                System.out.println("Response Headers:");
                connection.getHeaderFields().forEach((key, value) -> {
                    if (key != null) {
                        System.out.println(key + ": " + String.join(", ", value));
                    }
                });
            }

            // Print response body
            System.out.println(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
