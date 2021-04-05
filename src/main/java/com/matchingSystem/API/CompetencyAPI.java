package com.matchingSystem.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Model.Competency;
import org.json.JSONArray;

public class CompetencyAPI extends APIRouter {
    /**
     * CompetencyAPI constructor
     */
    public CompetencyAPI() {
        this.objMapper = new ObjectMapper();
        route = "/competency";
        c = Competency.class;
    }

    /**
     * Get all competencies
     * @return JSONArray of all the competencies
     */
    @Override
    public Object getAll() {
        return new JSONArray(APIManager.GETRequest(route));
    }

    /**
     * Function that parses variables to json needed for the request body for create()
     *
     * @param ownerId ownerId of this Competency
     * @param subjectId subjectId of the subject
     * @param level level of the Competency
     * @return StringBuilder of the parsed json
     */
    public StringBuilder parseToJsonForCreate(String ownerId, String subjectId, int level) {
        StringBuilder retVal = new StringBuilder();

        retVal.append("{");
        retVal.append(String.format("\"ownerId\": \"%s\",", ownerId));
        retVal.append(String.format("\"subjectId\": \"%s\",", subjectId));
        retVal.append(String.format("\"level\": %d", level));
        retVal.append("}");

        return retVal;
    }

    /**
     * Function that parses variables to json needed for the request body for updatePartialById()
     *
     * @param level level of the Competency to be updated
     * @return StringBuilder of the parsed json
     */
    public StringBuilder parseToJsonForPartialUpdate(int level) {
        StringBuilder retVal = new StringBuilder();

        retVal.append("{");
        retVal.append(String.format("\"level\": %d", level));
        retVal.append("}");

        return retVal;
    }
}
