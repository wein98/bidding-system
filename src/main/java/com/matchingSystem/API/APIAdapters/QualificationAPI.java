package com.matchingSystem.API.APIAdapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.ClientInterfaces.QualificationAPIInterface;
import com.matchingSystem.Model.Qualification;

public class QualificationAPI extends APIRouter implements QualificationAPIInterface {
    /**
     * Singleton design pattern
     */
    private static QualificationAPI ourInstance;
    /**
     * QualificationAPI constructor (private)
     */
    private QualificationAPI() {
        this.objMapper = new ObjectMapper();
        route = "/qualification";
        c = Qualification.class;
    }

    /**
     * Global access point
     * @return the only instance of this class
     */
    public static QualificationAPI getInstance() {

        if (ourInstance == null) {
            ourInstance = new QualificationAPI();
        }
        return ourInstance;
    }

    /**
     * Get all qualifications
     * @return null
     */
    @Override
    public Object getAll() {
        return null;
    }

    /**
     * Function that parses variables to json needed for the request body
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
