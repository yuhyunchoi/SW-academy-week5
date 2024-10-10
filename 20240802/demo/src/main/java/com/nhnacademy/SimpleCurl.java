package com.nhnacademy;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleCurl {
    public static void main(String[] args) {
        try {
            String urlString = "http://httpbin.org/get";  // 요청할 URL
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 요청 메서드 설정 (GET, POST 등)
            connection.setRequestMethod("GET");

            // 요청 헤더 설정
            connection.setRequestProperty("User-Agent", "SimpleCurl/1.0");

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 응답 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // 출력
            System.out.println(content.toString());

            // 자원 해제
            in.close();
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

