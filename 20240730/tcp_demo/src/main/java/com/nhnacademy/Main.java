package com.nhnacademy;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8888)) {
            int ch;
            while((ch = socket.getInputStream().read()) >= 0){
                System.out.println(ch);
            }

        } catch (IOException e) {
            //e.printStackTrace();
        }    
    }   
}