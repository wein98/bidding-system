package com.matchingSystem.API.APIAdapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIService;

import java.lang.Object;
import java.util.ArrayList;

import static com.matchingSystem.API.APIService.DELETERequest;

public abstract class APIRouter {
    protected String route;
    protected ObjectMapper objMapper;
    protected Class c;

    public Object getAll() {
        return null;
    }

    /**
     * Create a new c.object
     * @param params params for the request body
     * @return the c.object created
     */
    public Object create(StringBuilder params) {
        String jsonResponse = APIService.UpdateRequest(route, params, APIService.POST);

        try {
            return objMapper.readValue(jsonResponse, c);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get an object by id
     * @param id the target object id
     * @return a c.object
     */
    public Object getById(String id) {
        try {
            String urlRoute = route + "/" + id;
            String jsonResponse = APIService.GETRequest(urlRoute);
            return objMapper.readValue(jsonResponse, c);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Delete an object by id
     * @param id the target id
     * @return true if delete success, else false
     */
    public boolean deleteById(String id){
        try {
            int responseCode = DELETERequest(route + "/" + id);
            // TODO: think on how to display message of different status code of a failed request
            return responseCode == 204;
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
            String jsonResponse = APIService.UpdateRequest(route + "/" + id, params, APIService.PATCH);

            return objMapper.readValue(jsonResponse, c);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
