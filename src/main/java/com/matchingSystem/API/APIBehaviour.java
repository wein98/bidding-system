package com.matchingSystem.API;

import org.json.JSONObject;

import java.lang.Object;

import static com.matchingSystem.API.APIManager.DELETERequest;

public abstract class APIBehaviour {
    abstract Object getAll();
//    abstract Object create();
    abstract Object getById(String id);
    boolean deleteById(String id, String apiPath){
        try {
            String route = apiPath + "/" + id;
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
//    Object updatePartialById(String id, String payload); // TODO: different obj need different parameters ... should we just give it as a String ? or StringBuilder
}
