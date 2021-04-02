package com.matchingSystem.API;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.swing.*;
import java.io.*;
import java.net.*;

import static com.matchingSystem.API.APIKey.API_KEY;


public class APIManager {

    private static final String HOST = "https://fit3077.com/api/v1";
    public static final int PATCH = 1;
    public static final int PUT = 2;
    public static final int POST = 3;

    public static String GETRequest(String route) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpGet request = new HttpGet(HOST + route);

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

    /**
     * Function used for POST, PUT and PATCH request
     * @param url
     * @param params
     * @return
     */
    public static String UpdateRequest(String url, StringBuilder params, int requestType) {
        String result = null;

        HttpEntityEnclosingRequestBase request;
        if(requestType == PATCH){
            request = new HttpPatch(HOST + url);
        }else if(requestType == PUT) {
            request = new HttpPut(HOST + url);
        }else{
            request = new HttpPost(HOST + url);
        }
        System.out.println(request.getClass().getName());
        // add request headers
        request.addHeader("Authorization", API_KEY);
        request.addHeader("accept", "*/*");
        request.addHeader("Content-Type", "application/json");

//        StringBuilder json = new StringBuilder();
//        json.append("{");
//        json.append("\"name\":\"mkyong\",");
//        json.append("\"notes\":\"hello\"");
//        json.append("}");

        // send a JSON data
        try {
            request.setEntity(new StringEntity(params.toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            result = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int DELETERequest(String url){
        try {
            HttpDelete deleteRequest = new HttpDelete(HOST + url);

            // add request headers
            deleteRequest.addHeader("Authorization", API_KEY);
            deleteRequest.addHeader("Accept", "application/json");
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(deleteRequest);
            int statusCode = response.getStatusLine().getStatusCode();

            return statusCode;
        } catch (IOException e){
            e.printStackTrace();
        }
        return -1; // negative -1 indicates system error
    }

//    public static String PATCHRequest(String url, StringBuilder params){
//        String result = null;
//        HttpPatch patchRequest = new HttpPatch(HOST + url);
//
//        // add request headers
//        patchRequest.addHeader("Authorization", API_KEY);
//        patchRequest.addHeader("accept", "*/*");
//        patchRequest.addHeader("Content-Type", "application/json");
//
////        StringBuilder json = new StringBuilder();
////        json.append("{");
////        json.append("\"name\":\"mkyong\",");
////        json.append("\"notes\":\"hello\"");
////        json.append("}");
//
//        // send a JSON data
//        try {
//            patchRequest.setEntity(new StringEntity(params.toString()));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        try (CloseableHttpClient httpClient = HttpClients.createDefault();
//             CloseableHttpResponse response = httpClient.execute(patchRequest)) {
//
//            result = EntityUtils.toString(response.getEntity());
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

//    public static String PUTRequest(String url, StringBuilder params){
//        String result = null;
//        HttpPut putRequest = new HttpPut(HOST + url);
//
//        // add request headers
//        putRequest.addHeader("Authorization", API_KEY);
//        putRequest.addHeader("accept", "*/*");
//        putRequest.addHeader("Content-Type", "application/json");
//
////        StringBuilder json = new StringBuilder();
////        json.append("{");
////        json.append("\"name\":\"mkyong\",");
////        json.append("\"notes\":\"hello\"");
////        json.append("}");
//
//        // send a JSON data
//        try {
//            putRequest.setEntity(new StringEntity(params.toString()));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        try (CloseableHttpClient httpClient = HttpClients.createDefault();
//             CloseableHttpResponse response = httpClient.execute(putRequest)) {
//
//            result = EntityUtils.toString(response.getEntity());
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
}

