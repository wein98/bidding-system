package com.matchingSystem.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Model.Subject;
import org.json.JSONArray;

public class SubjectAPI extends APIRouter {
    /**
     * SubjectAPI constructor
     */
    public SubjectAPI() {
        this.objMapper = new ObjectMapper();
        route = "/subject";
        c = Subject.class;
    }

    @Override
    public Object getAll() {
        return new JSONArray(APIManager.GETRequest(route));
    }

    public StringBuilder parseToJson(String name, String desc) {
        StringBuilder retVal = new StringBuilder();

        retVal.append("{");
        retVal.append(String.format("\"name\": \"%s\",", name));
        retVal.append(String.format("\"description\": \"%s\"", desc));
        retVal.append("}");

        return retVal;
    }
}
