package com.matchingSystem.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import static com.matchingSystem.API.APIManager.*;

public class MessageAPI implements APIBehaviour {
    ObjectMapper objMapper = new ObjectMapper();
    private static final String APIPATH = "/message";
    /**
     * Get all messages
     * @return list of messages
     */
    public ArrayList<Message> getAll(){
        ArrayList<Message> messages = new ArrayList<>();
        try {
            String response = GETRequest(APIPATH);
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
    public Message create(String bidId, String posterId, String content){
        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"bidId\": \"%s\",", bidId));
            jsonParam.append(String.format("\"posterId\": \"%s\"", posterId));
            jsonParam.append(String.format("\"datePosted\": \"%s\"", sdf2.format(now)));
            jsonParam.append(String.format("\"content\": \"%s\"", content));
            jsonParam.append(String.format("\"additionalInfo\": \"%s\"", "{}"));
            jsonParam.append("}");
            String response = UpdateRequest(APIPATH, jsonParam, APIManager.POST);
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
            String route = APIPATH + "/" + id;
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
            String route = APIPATH + "/" + id;
            int responseCode = DELETERequest(route);
            // TODO: think on how to display message of different status code of a failed request
            if (responseCode == 204){
                return true;
            }else{
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update message content
     * @param id the target message id
     * @return the updated message
     */
    public Message updatePartialById(String id, String content){
        try {
            String route = APIPATH + "/" + id;

            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"content\": \"%s\",", content));
            jsonParam.append(String.format("\"additionalInfo\": \"%s\"", "{}"));
            jsonParam.append("}");

            String response = UpdateRequest(route, jsonParam, APIManager.PATCH);
            Message message = objMapper.readValue(response, Message.class);

            return message;
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

}