package com.matchingSystem.API.APIAdapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIService;
import com.matchingSystem.API.ClientInterfaces.SubjectAPIInterface;
import com.matchingSystem.Model.Subject;
import org.json.JSONArray;

public class SubjectAPI extends APIRouter implements SubjectAPIInterface {
    /**
     * Singleton design pattern
     */
    private static SubjectAPI ourInstance;
    /**
     * SubjectAPI constructor (private)
     */
    private SubjectAPI() {
        this.objMapper = new ObjectMapper();
        route = "/subject";
        c = Subject.class;
    }
    /**
     * Global access point
     * @return the only instance of this class
     */
    public static SubjectAPI getInstance() {

        if (ourInstance == null) {
            ourInstance = new SubjectAPI();
        }
        return ourInstance;
    }

    /**
     * Get all subjects
     * @return JSONArray of all the subjects
     */
    @Override
    public Object getAll() {
        return new JSONArray(APIService.GETRequest(route));
    }

    /**
     * Function that parses variables to json needed for the request body
     * @param name name of the subject
     * @param desc description of the subject
     * @return
     */
    public StringBuilder parseToJson(String name, String desc) {
        StringBuilder retVal = new StringBuilder();

        retVal.append("{");
        retVal.append(String.format("\"name\": \"%s\",", name));
        retVal.append(String.format("\"description\": \"%s\"", desc));
        retVal.append("}");

        return retVal;
    }
}
