package com.javarush.task.task40.task4006;

import java.io.*;
import java.net.*;

/*
Отправка GET-запроса через сокет
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        getSite(new URL("http://javarush.ru/social.html"));
    }

    public static void getSite(URL url) {
        try {

            Socket socket = new Socket(url.getHost(), url.getDefaultPort());

            OutputStream outputStream = socket.getOutputStream();

            String request = "GET "+url.getPath();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bw.write(request);
            bw.flush();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseLine;

            while ((responseLine = bufferedReader.readLine()) != null) {
                System.out.println(responseLine);
            }
            bufferedReader.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
