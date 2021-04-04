package com.matchingSystem.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import static com.matchingSystem.API.APIManager.*;

public class MessageAPI extends APIBehaviour {

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
//            response = response.replace("[","");
//            response = response.replace("]","");
//            response = response.replace("},{","}  {");
//            if(response.length() > 0) {
//                String[] resArr = response.split("  ", 0);
//                for (int i = 0; i < resArr.length; i++) {
//                    Message message = objMapper.readValue(resArr[i], Message.class);
//                    messages.add(message);
//                }
//            }
            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                Message message = objMapper.readValue(jsonObj.toString(), Message.class);
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
    public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

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
            jsonParam.append(String.format("\"posterId\": \"%s\",", posterId));
            jsonParam.append(String.format("\"datePosted\": \"%s\",", sdf2.format(now)));
            jsonParam.append(String.format("\"content\": \"%s\",", content));
            jsonParam.append(String.format("\"additionalInfo\": {}"));
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
//    public boolean deleteById(String id){
//        try {
//            String route = APIPATH + "/" + id;
//            int responseCode = DELETERequest(route);
//            if (responseCode == 204){
//                return true;
//            }else{
//                return false;
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return false;
//    }

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
            jsonParam.append(String.format("\"additionalInfo\": {}"));
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