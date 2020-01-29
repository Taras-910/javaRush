package com.javarush.task.task40.task4001;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/* 
POST, а не GET
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        solution.sendPost(new URL("https://requestbin.jumio.com/y3abvfy3"),
                /*.solution.sendPost(new URL("http://requestb.in/"),*/             // для валика
                "name=zapp&mood=good&locale=&id=777");
    }

    public void sendPost(URL url, String urlParameters) throws Exception {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();

        outputStream.write(urlParameters.getBytes("UTF-8"));
        outputStream.flush();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        writer.flush();
        writer.close();
        outputStream.close();

        connection.connect();




        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String responseLine;
        StringBuilder response = new StringBuilder();

        while ((responseLine = bufferedReader.readLine()) != null) {
            response.append(responseLine);
        }
        bufferedReader.close();

        System.out.println("Response: " + response.toString());
    }
}
/*
POST, а не GET
Исправь ошибки в методе sendPost, чтобы он отправлял POST-запрос с переданными параметрами.
Примечание: метод main в тестировании не участвует, но чтобы программа корректно работала локально,
можешь зайти на сайт http://requestb.in/, создать свой RequestBin и использовать его в main.
Требования:
1. Метод sendPost должен вызвать метод setRequestMethod с параметром "POST".
2. Метод sendPost должен устанавливать флаг doOutput у соединения в true.
3. В OutputStream соединения должны быть записаны переданные в метод sendPost параметры.
4. Метод sendPost должен выводить результат своей работы на экран.


    public void sendPost(URL url, String urlParameters) throws Exception {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String responseLine;
        StringBuilder response = new StringBuilder();

        while ((responseLine = bufferedReader.readLine()) != null) {
            response.append(responseLine);
        }
        bufferedReader.close();

        System.out.println("Response: " + response.toString());
    }
 */