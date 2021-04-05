package com.matchingSystem.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.Object;

import static com.matchingSystem.API.APIManager.DELETERequest;

public abstract class APIRouter {
    protected String route;
    protected ObjectMapper objMapper;
    protected Class c;

    abstract Object getAll();

    /**
     * Create a new c.object
     * @param params params for the request body
     * @return the c.object created
     */
    public Object create(StringBuilder params) {
        String jsonResponse = APIManager.UpdateRequest(route, params, APIManager.POST);

        try {
            return objMapper.readValue(jsonResponse, c);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    };

    /**
     * Get an object by id
     * @param id the target object id
     * @return a c.object
     */
    public Object getById(String id) {
        try {
            String urlRoute = route + "/" + id;
            String jsonResponse = APIManager.GETRequest(urlRoute);
            return objMapper.readValue(jsonResponse, c);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    };

    /**
     * Delete an object by id
     * @param id the target id
     * @return true if delete success, else false
     */
    public boolean deleteById(String id){
        try {
            int responseCode = DELETERequest(route + "/" + id);
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
     * Partially updates data for an existing c.object
     * Uses PATCH method
     *
     * @param id the target object id
     * @param params params for the request body
     * @return the subject updated
     */
    public Object updatePartialById(String id, StringBuilder params) {
        try {
            String jsonResponse = APIManager.UpdateRequest(route + "/" + id, params, APIManager.PATCH);

            return objMapper.readValue(jsonResponse, c);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    };
}
