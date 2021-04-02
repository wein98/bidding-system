package com.matchingSystem.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Subject;
import org.json.JSONArray;

public class SubjectAPI implements APIBehaviour {
    ObjectMapper objMapper = new ObjectMapper();
    private static String ROUTE = "/subject";

    @Override
    public Object getAll() {
        return new JSONArray(APIManager.GETRequest(ROUTE));
    }

    /**
     * Get a subject by subject id
     * @param id the target subject id
     * @return a subject
     */
    @Override
    public Object getById(String id) {
        try {
            String urlRoute = ROUTE + "/" + id;
            String jsonResponse = APIManager.GETRequest(urlRoute);

            return objMapper.readValue(jsonResponse, Subject.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Delete a subject by subject id
     * @param id the target subject id
     * @return true if delete success, else false
     */
    @Override
    public boolean deleteById(String id) {
        try {
            int responseCode = APIManager.DELETERequest(ROUTE + "/" + id);
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
     * Create a new Subject
     * @param name name of the subject
     * @param desc description of the subject
     * @return the subject created
     */
    public Subject create(String name, String desc) {
        try {
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"name\": \"%s\",", name));
            jsonParam.append(String.format("\"description\": \"%s\",", desc));
            jsonParam.append("}");

            String jsonResponse = APIManager.UpdateRequest(ROUTE, jsonParam, APIManager.POST);

            return objMapper.readValue(jsonResponse, Subject.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Updates an existing Subject
     * @param name name of the subject
     * @param desc description of the subject
     * @return the subject updated
     */
    public Subject updateSubject(String name, String desc) {
        try {
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"name\": \"%s\",", name));
            jsonParam.append(String.format("\"description\": \"%s\",", desc));
            jsonParam.append("}");

            String jsonResponse = APIManager.UpdateRequest(ROUTE, jsonParam, APIManager.PUT);

            return objMapper.readValue(jsonResponse, Subject.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
