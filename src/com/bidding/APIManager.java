package com.bidding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

import static com.bidding.APIKey.API_KEY;


public class APIManager {

    private static String host = "https://fit3077.com/api/v1";

    public static void GETRequest(String route) {
        String readLine = null;
        URL getUrl = null;

        try {
            getUrl = new URL(host + route);
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", API_KEY);
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in .readLine()) != null) {
                    response.append(readLine);
                } in .close();
                // print result
                System.out.println("JSON String Result " + response.toString());
                //GetAndPost.POSTRequest(response.toString());
            } else {
                System.out.println("GET NOT WORKED");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

