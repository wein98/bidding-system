package com.matchingSystem.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Qualification;

public class QualificationAPI implements APIBehaviour {
    ObjectMapper objMapper = new ObjectMapper();
    private static String ROUTE = "/qualification";

    @Override
    public Object getAll() {
        return null;
    }

    /**
     * Get a Qualification by qualification id
     * @param id the target qualification id
     * @return a Qualification
     */
    @Override
    public Qualification getById(String id) {
        try {
            String urlRoute = ROUTE + "/" + id;
            String jsonResponse = APIManager.GETRequest(urlRoute);
            Qualification qualification = objMapper.readValue(jsonResponse, Qualification.class);
            return qualification;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Delete a qualification by qualification id
     * @param id the target qualification id
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
     * Create a new Qualification
     * @param title title of the Qualification
     * @param desc description of the Qualification
     * @param verified verification of the Qualification
     * @param ownerId id of the owner
     * @return the Qualification created
     */
    public Qualification create(String title, String desc, String verified, String ownerId) {
        try {
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"title\": \"%s\",", title));
            jsonParam.append(String.format("\"description\": \"%s\",", desc));
            jsonParam.append(String.format("\"verified\": %s,", verified));
            jsonParam.append(String.format("\"ownerId\": \"%s\"", ownerId));
            jsonParam.append("}");

            String jsonResponse = APIManager.UpdateRequest(ROUTE, jsonParam, APIManager.POST);

            return objMapper.readValue(jsonResponse, Qualification.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Updates level of the Qualification by id
     * @param title title of the Qualification
     * @param desc description of the Qualification
     * @param verified verification of the Qualification
     * @param ownerId id of the owner
     * @return the Qualification updated
     */
    public Qualification updateQualification(String id, String title, String desc, String verified, String ownerId) {
        try {
            String urlRoute = ROUTE + "/" + id;

            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"title\": \"%s\",", title));
            jsonParam.append(String.format("\"description\": \"%s\",", desc));
            jsonParam.append(String.format("\"verified\": %s,", verified));
            jsonParam.append(String.format("\"ownerId\": \"%s\"", ownerId));
            jsonParam.append("}");

            String jsonResponse = APIManager.UpdateRequest(urlRoute, jsonParam, APIManager.PUT);

            return objMapper.readValue(jsonResponse, Qualification.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
