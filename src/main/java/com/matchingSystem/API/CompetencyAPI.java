package com.matchingSystem.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Competency;
import org.json.JSONArray;

public class CompetencyAPI implements APIBehaviour{
    ObjectMapper objMapper = new ObjectMapper();
    private static String ROUTE = "/competency";

    /**
     * Get all competencies
     * @return JSONArray of all the competencies
     */
    @Override
    public Object getAll() {
        return new JSONArray(APIManager.GETRequest(ROUTE));
    }

    /**
     * Get a competency by competency id
     * @param id the target competency id
     * @return a competency
     */
    @Override
    public Competency getById(String id) {
        try {
            String urlRoute = ROUTE + "/" + id;
            String jsonResponse = APIManager.GETRequest(urlRoute);

            return objMapper.readValue(jsonResponse, Competency.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Delete a Competency by competency id
     * @param id the target competency id
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
     * Create a new Competency
     * @param ownerId ownerId of this Competency
     * @param subjectId subjectId of the subject
     * @param level level of the Competency
     * @return the Competency created
     */
    public Competency create(String ownerId, String subjectId, int level) {
        try {
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"ownerId\": \"%s\",", ownerId));
            jsonParam.append(String.format("\"subjectId\": \"%s\",", subjectId));
            jsonParam.append(String.format("\"level\": \"%d\",", level));
            jsonParam.append("}");

            String jsonResponse = APIManager.UpdateRequest(ROUTE, jsonParam, APIManager.POST);

            return objMapper.readValue(jsonResponse, Competency.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Updates level of the competency by competencyId
     * @param competencyId id of the Competency
     * @param level level of the Competency to be updated
     * @return the Competency updated
     */
    public Competency updateLevel(String competencyId, int level) {
        try {
            StringBuilder jsonParam = new StringBuilder();
            jsonParam.append("{");
            jsonParam.append(String.format("\"level\": \"%d\",", level));
            jsonParam.append("}");

            String jsonResponse = APIManager.UpdateRequest(ROUTE + "/" + competencyId, jsonParam, APIManager.PATCH);

            return objMapper.readValue(jsonResponse, Competency.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
