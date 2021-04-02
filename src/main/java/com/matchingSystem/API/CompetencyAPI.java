package com.matchingSystem.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Competency;
import org.json.JSONArray;
import org.json.JSONObject;

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

    @Override
    public boolean deleteById(String id) {
        return false;
    }
}
