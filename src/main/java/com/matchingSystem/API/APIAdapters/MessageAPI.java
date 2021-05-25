package com.matchingSystem.API.APIAdapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.ClientInterfaces.MessageAPIInterface;
import com.matchingSystem.BiddingSystem.Message;
import com.matchingSystem.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import static com.matchingSystem.API.APIService.*;

public class MessageAPI extends APIRouter implements MessageAPIInterface {
    public MessageAPI() {
        this.objMapper = new ObjectMapper();
        route = "/message";
        c = Message.class;
    }

    /**
     * Get all messages
     * @return list of messages
     */
    @Override
    public ArrayList<Message> getAll(String queryParam){
        ArrayList<Message> messages = new ArrayList<>();
        try {
            String response = GETRequest(route);
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
     * Parsing the param to create a new message
     * @param bidId the bid that the message is tied to
     * @param posterId id of the user who posted the message
     * @return the message created
     */
    public StringBuilder parseToJsonForCreate(String bidId, String posterId, String content) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"bidId\": \"%s\",", bidId));
        jsonParam.append(String.format("\"posterId\": \"%s\",", posterId));
        jsonParam.append(String.format("\"datePosted\": \"%s\",", Utility.sdf2.format(now)));
        jsonParam.append(String.format("\"content\": \"%s\",", content));
        jsonParam.append(String.format("\"additionalInfo\": { \"studentReply\": \"\" }"));
        jsonParam.append("}");

        return jsonParam;
    }

    /**
     * Parsing the param to update an existing message
     * @return the updated message
     */
    public StringBuilder parseToJsonForPartialUpdate(String content, JSONObject additionalInfo){
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"content\": \"%s\",", content));
        jsonParam.append("\"additionalInfo\": ");
        jsonParam.append(additionalInfo);
        jsonParam.append("}");
        return jsonParam;
    }

}