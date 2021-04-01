package com.matchingSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import com.matchingSystem.Message;

import java.util.ArrayList;

import static com.matchingSystem.APIManager.POSTRequest;
import static com.matchingSystem.APIManager.GETRequest;
public class MessageAPI implements APIBehaviour{
    public ArrayList<Message> getAll(){
        String route = "/message";
        ArrayList<Message> messages = new ArrayList<Message>();
        try {
            //String response = GETRequest(route);
            // split returned string into array of strings (multiple object)
            // for testing purposes only
            String response = "[{\"id\":\"f9aab64d-d0e5-486d-bb5c-30d3014dasds\",\"bidId\":\"bc06e9ad-5d20-4dce-a176-a6ac73bdsasd\",\"poster\":{\"id\":\"4ad8f1ed-4883-4c44-a9ab-a50bdee96ff9\",\"givenName\":\"Dmitri\",\"familyName\":\"Antonovich\",\"userName\":\"danto87\",\"isStudent\":false,\"isTutor\":true},\"datePosted\":\"2021-03-30T20:04:07.244Z\",\"dateLastEdited\":null,\"content\":\"Sample content\",\"additionalInfo\":{}},{\"id\":\"f9aab64d-d0e5-486d-bb5c-30d3014ddf1e\",\"bidId\":\"bc06e9ad-5d20-4dce-a176-a6ac73b26b35\",\"poster\":{\"id\":\"4ad8f1ed-4883-4c44-a9ab-a50bdee96ff9\",\"givenName\":\"something\",\"familyName\":\"something\",\"userName\":\"danto87\",\"isStudent\":false,\"isTutor\":true},\"datePosted\":\"2021-03-30T20:04:07.244Z\",\"dateLastEdited\":null,\"content\":\"Sample content\",\"additionalInfo\":{}}]";
            response = response.replace("[","");
            response = response.replace("]","");
            response = response.replace("},{","}  {");
            String[] resArr = response.split("  ",0);
            for(int i=0; i<resArr.length; i++) {
                ObjectMapper objMapper = new ObjectMapper();
                Message message = objMapper.readValue(response, Message.class);
                messages.add(message);
                //System.out.println(message);
            }
            return messages;
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject create(){
        return new JSONObject();
    }

    public JSONObject getById(String id){
        return new JSONObject();
    }

    public JSONObject deleteById(String id){
        return new JSONObject();
    }

    public JSONObject updatePartialById(String id){
        return new JSONObject();
    }

}