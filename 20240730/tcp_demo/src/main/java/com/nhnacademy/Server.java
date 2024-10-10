package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static class Sender extends Thread{
        OutputStream output;

        public Sender(OutputStream output){
            this.output = output;
        }

        @Override
        public void run(){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while(!Thread.currentThread().isInterrupted()){
                try {
                    String line = reader.readLine();
                    output.write(line.getBytes());
                    output.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class EchoClient extends Thread{
        Socket socket;
        
        public EchoClient(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run(){
            int ch;
            try {
                while((ch = socket.getInputStream().read()) >= 0){
                    socket.getOutputStream().write(ch);
                    System.out.println(Thread.currentThread().getId() + " : " + ch);
                    socket.getOutputStream().flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
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
            
            try {
                int ch;
                while((ch = input.read()) >= 0){
                    System.out.print((char)ch);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    static class Client extends Thread{
        Socket socket;

        public Client(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run(){
            Sender sender;
            try {
                sender = new Sender(socket.getOutputStream());
                sender.start();

                Receiver receiver = new Receiver(socket.getInputStream());
                receiver.start();

                Thread.currentThread().interrupt();
                Thread.sleep(1000);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(1234)){
            while(true){
                Client client = new Client(serverSocket.accept());
                client.start();
            }
        }
        catch(IOException e){
            System.err.println(e);
        }
    }
}
