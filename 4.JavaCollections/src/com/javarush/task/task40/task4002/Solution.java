package com.javarush.task.task40.task4002;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;


/* 
Опять POST, а не GET
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
 //       solution.sendPost("https://requestbin.jumio.com/y3abvfy3", "name=zapp&mood=good&locale=&id=777");
        solution.sendPost("http://requestb.in/1h4qhvv1", "name=zapp&mood=good&locale=&id=777");
    }
//1. Метод sendPost должен создавать объект типа HttpPost с параметром url.
//2. Метод sendPost должен вызвать метод setEntity у созданного объекта типа HttpPost.
//3. В OutputStream соединения должны быть записаны переданные в метод sendPost параметры.
//4. Метод sendPost должен использовать метод getHttpClient для получения HttpClient.
public void sendPost(String url, String urlParameters) throws Exception {
    HttpClient client = getHttpClient();
    HttpPost request = new HttpPost(url);
    request.addHeader("User-Agent", "Mozilla/5.0");


//    String[] pairs = urlParameters.split("&");
//    List<NameValuePair> params = new ArrayList<NameValuePair>(pairs.length);
//    for(int i = 0; i < pairs.length; i++) {
//        String[] p = pairs[i].split("=");
//        String name = null;
//        String value = null;
//        if(p.length == 2) {
//            name = p[0];
//            value = p[1];
//        }
//        else{
//            name = pairs[i];
//            value = "";
//        }
//        params.add(new BasicNameValuePair(name, value));
//    }

    List<NameValuePair> params = URLEncodedUtils.parse(URI.create("?" + urlParameters), "UTF-8");

    request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
    HttpResponse response = client.execute(request);


    System.out.println("Response Code: " + response.getStatusLine().getStatusCode());

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

    StringBuffer result = new StringBuffer();
    String responseLine;
    while ((responseLine = bufferedReader.readLine()) != null) {
        result.append(responseLine);
    }

    System.out.println("Response: " + result.toString());
}

    protected HttpClient getHttpClient() {
        return HttpClientBuilder.create().build();
    }
}
/*
post.setEntity(new StringEntity(urlParameters))

Опять POST, а не GET
Исправь ошибки в методе sendPost, чтобы он отправлял POST-запрос с переданными параметрами.
Примечание: метод main в тестировании не участвует, но чтобы программа корректно работала локально,
можешь зайти на сайт http://requestb.in/, создать свой RequestBin и использовать его в main.
Требования:
1. Метод sendPost должен создавать объект типа HttpPost с параметром url.
2. Метод sendPost должен вызвать метод setEntity у созданного объекта типа HttpPost.
3. В OutputStream соединения должны быть записаны переданные в метод sendPost параметры.
4. Метод sendPost должен использовать метод getHttpClient для получения HttpClient.
 */

