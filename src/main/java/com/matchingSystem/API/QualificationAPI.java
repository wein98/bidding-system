package com.matchingSystem.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.Model.Qualification;

public class QualificationAPI extends APIRouter {
    /**
     * QualificationAPI constructor
     */
    public QualificationAPI() {
        this.objMapper = new ObjectMapper();
        route = "/qualification";
        c = Qualification.class;
    }

    @Override
    public Object getAll() {
        return null;
    }

    /**
     * Function that parses variables to json needed for the request body
     *
     * @param title title of the Qualification
     * @param desc description of the Qualification
     * @param verified verification of the Qualification
     * @param ownerId id of the owner
     * @return StringBuilder of the parsed json
     */
    public StringBuilder parseToJson(String title, String desc, String verified, String ownerId) {
        StringBuilder retVal = new StringBuilder();

        retVal.append("{");
        retVal.append(String.format("\"title\": \"%s\",", title));
        retVal.append(String.format("\"description\": \"%s\",", desc));
        retVal.append(String.format("\"verified\": %s,", verified));
        retVal.append(String.format("\"ownerId\": \"%s\"", ownerId));
        retVal.append("}");

        return retVal;
    }

}
