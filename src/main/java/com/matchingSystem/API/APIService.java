package com.matchingSystem.API;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;

import static com.matchingSystem.API.APIKey.API_KEY;


public class APIService {

    private static final String HOST = "https://fit3077.com/api/v2";
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

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
//                System.out.println(res0ponse.getProtocolVersion());              // HTTP/1.1
//                System.out.println(response.getStatusLine().getStatusCode());   // 200
//                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
//                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
//                    System.out.println(result);
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
        // add request headers
        request.addHeader("Authorization", API_KEY);
        request.addHeader("accept", "*/*");
        request.addHeader("Content-Type", "application/json");

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
//        System.out.println(result);
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

}

