package com.matchingSystem.API.APIAdapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIService;
import com.matchingSystem.API.ClientInterfaces.SubjectAPIInterface;
import com.matchingSystem.Model.Subject;
import org.json.JSONArray;
import static com.matchingSystem.API.APIService.*;
import org.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

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
    public ArrayList<Subject> getAll() {
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            String response = GETRequest(this.route);
            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonObj = arr.getJSONObject(i);
                String jsonStr =  jsonObj.toString();
                Subject subject = objMapper.readValue(jsonStr, Subject.class);
                subjects.add(subject);
            }
            return subjects;
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
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
