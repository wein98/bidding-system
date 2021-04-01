package com.matchingSystem.API;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

import static com.matchingSystem.API.APIKey.API_KEY;


public class APIManager {

    private static String host = "https://fit3077.com/api/v1";

//    public static String GETRequest(String route) {
//        String readLine = null;
//        URL getUrl = null;
//
//        try {
//            getUrl = new URL(host + route);
//            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Accept", "application/json");
//            connection.setRequestProperty("Authorization", API_KEY);
//            int responseCode = connection.getResponseCode();
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream()));
//            StringBuffer response = new StringBuffer();
//            while ((readLine = in .readLine()) != null) {
//                response.append(readLine);
//            } in .close();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//
//                // print result
////                System.out.println("JSON String Result " + response.toString());
//                //GetAndPost.POSTRequest(response.toString());
//                return response.toString();
//            } else {
//                JSONObject parsedResponse = new JSONObject(response.toString());
//                JOptionPane.showMessageDialog(null, parsedResponse.getString("message"), "Login Error.", 2);
//                System.out.println(responseCode);
//                System.out.println("GET NOT WORKED");
//                return null;
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static String GETRequest(String route) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpGet request = new HttpGet(host + route);

            // add request headers
            request.addHeader("Authorization", API_KEY);
            request.addHeader("Accept", "application/json");

//            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                    return result;
                }

            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String POSTRequest(String route, String params) {
        URL obj = null;
        try {

            obj = new URL(host + route);
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("accept", "*/*");
            postConnection.setRequestProperty("Authorization", API_KEY);
            postConnection.setRequestProperty("Content-Type", "application/json");

            postConnection.setDoOutput(true);
            OutputStream os = postConnection.getOutputStream();
            os.write(params.getBytes());
            os.flush();
            os.close();

            int responseCode = postConnection.getResponseCode();
            System.out.println("POST Response Code :  " + responseCode);
            System.out.println("POST Response Message : " + postConnection.getResponseMessage());

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        postConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                } in .close();

                // print result
//                System.out.println(response.toString());
                return response.toString();
            } else {
                System.out.println("POST NOT WORKED");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

