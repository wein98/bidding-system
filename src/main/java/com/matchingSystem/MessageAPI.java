package com.matchingSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import static com.matchingSystem.APIManager.POSTRequest;
import static com.matchingSystem.APIManager.GETRequest;

public class MessageAPI implements APIBehaviour{
    ObjectMapper objMapper = new ObjectMapper();

    /**
     * Get all messages
     * @return list of messages
     */
    public ArrayList<Message> getAll(){
        String route = "/message";
        ArrayList<Message> messages = new ArrayList<Message>();
        try {
            String response = GETRequest(route);
            // split returned string into array of strings (multiple object)
            // for testing purposes only
            //String response = "[{\"id\":\"f9aab64d-d0e5-486d-bb5c-30d3014dasds\",\"bidId\":\"bc06e9ad-5d20-4dce-a176-a6ac73bdsasd\",\"poster\":{\"id\":\"4ad8f1ed-4883-4c44-a9ab-a50bdee96ff9\",\"givenName\":\"Dmitri\",\"familyName\":\"Antonovich\",\"userName\":\"danto87\",\"isStudent\":false,\"isTutor\":true},\"datePosted\":\"2021-03-30T20:04:07.244Z\",\"dateLastEdited\":null,\"content\":\"Sample content\",\"additionalInfo\":{}},{\"id\":\"f9aab64d-d0e5-486d-bb5c-30d3014ddf1e\",\"bidId\":\"bc06e9ad-5d20-4dce-a176-a6ac73b26b35\",\"poster\":{\"id\":\"4ad8f1ed-4883-4c44-a9ab-a50bdee96ff9\",\"givenName\":\"something\",\"familyName\":\"something\",\"userName\":\"danto87\",\"isStudent\":false,\"isTutor\":true},\"datePosted\":\"2021-03-30T20:04:07.244Z\",\"dateLastEdited\":null,\"content\":\"Sample content\",\"additionalInfo\":{}}]";
            response = response.replace("[","");
            response = response.replace("]","");
            response = response.replace("},{","}  {");
            String[] resArr = response.split("  ",0);
            for(int i=0; i<resArr.length; i++) {
                //ObjectMapper objMapper = new ObjectMapper();
                Message message = objMapper.readValue(resArr[i], Message.class);
                messages.add(message);
            }
            return messages;
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Format of Timestamp used (ex. 2021-03-24T16:44:39.083+08:00)
     */
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    /**
     * Create a new message
     * @param bidId the bid that the message is tied to
     * @param posterId id of the user who posted the message
     * @return the message created
     */
    public Message create(String bidId, String posterId){
        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            String route = "/message";
            String param = String.format(
                    "{\n" + "\"bidId\": \"%s\",\r\n" +
                            "\"posterId\": \"%s\"\r\n" +
                            "\"datePosted\": \"%s\"\r\n" +
                            "\"content\": \"%s\"\r\n" +
                            "\"additionalInfo\": {}\r\n" + "}", bidId, posterId, sdf2.format(now), "SomeContent");
            String response = POSTRequest(route, param);
            Message message = objMapper.readValue(response, Message.class);
            return message;
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a message by its message id
     * @param id the target message id
     * @return the message
     */
    public Message getById(String id){
        try {
            String route = "/message/" + id;
            String response = GETRequest(route);
            Message message = objMapper.readValue(response, Message.class);
            return message;
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delete a message by its message id
     * @param id the target message id
     */
    public boolean deleteById(String id){
        try {
            String route = "/message/"+id;
            Boolean response = DELETERequest(route);
        }
    }

    /**
     * Update message content
     * @param id the target message id
     * @return the updated message
     */
    public JSONObject updatePartialById(String id){
        return new JSONObject();
    }

}