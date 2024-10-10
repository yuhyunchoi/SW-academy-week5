package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class SimpleNC {
    static class Sender extends Thread{
        OutputStream output;
        public Sender(OutputStream output){
            this.output = output;
        }

        @Override
        public void run(){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while(!Thread.currentThread().isInterrupted()){
                try{
                    String line = reader.readLine();
                    output.write((line + "\n").getBytes());
                    output.flush();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    static class Receiver extends Thread{
        InputStream input;
        public Receiver(InputStream input){
            this.input = input;
        }

        @Override
        public void run(){
            try{
                int ch;
                while((ch = input.read()) >= 0){
                    System.out.print((char)ch);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
    
        if(args.length < 1){
            System.out.println("Usage: java SimpleNC <options> [hostname] [port]");
            return;
        }

        List<String> options = List.of(args);
        if(options.contains("-l")){
            int port = Integer.parseInt(options.get(options.indexOf("-l") + 1));
            runServer(port);
        } else if(args.length == 2){
            String hostname = args[0];
            int port = Integer.parseInt(args[1]);
            runClient(hostname, port);
        }else{
            System.out.println("Invalid arguements");
        }

    }

    public static void runServer(int port) {
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Listening on port " + port);

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            Receiver receiver = new Receiver(clientSocket.getInputStream());
            Sender sender = new Sender(clientSocket.getOutputStream());

            receiver.start();
            sender.start();

            receiver.join();
            sender.join();
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void runClient(String hostname, int port) {
        try(Socket socket = new Socket(hostname, port)){
            System.out.println("Conncted to server");
            
            Receiver receiver = new Receiver(socket.getInputStream());
            Sender sender = new Sender(socket.getOutputStream());

            receiver.start();
            sender.start();

            receiver.join();
            sender.join();
        
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
