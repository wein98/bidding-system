package com.matchingSystem.API.APIAdapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIService;
import com.matchingSystem.API.ClientInterfaces.SubjectAPIInterface;
import com.matchingSystem.BiddingSystem.Subject;
import org.json.JSONArray;
import org.json.JSONObject;
import static com.matchingSystem.API.APIService.*;

import java.util.ArrayList;

public class SubjectAPI extends APIRouter implements SubjectAPIInterface {
    /**
     * SubjectAPI constructor (private)
     */
    public SubjectAPI() {
        this.objMapper = new ObjectMapper();
        route = "/subject";
        c = Subject.class;
    }

    /**
     * Get all subjects
     * @return JSONArray of all the subjects
     */
    @Override
    public ArrayList<Subject> getAll(String queryParam) {
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
     * Retreive a subject object by its id
     * @param id the target object id
     * @param queryParam the query parameter constant
     *                   pass in Constant.NONE_S if it's the default param
     *                   refer to //field type string section in Constant.class
     * @return the subject object
     */
    @Override
    public Object getById(String id, String queryParam) {
        String urlRoute = route + "/" + id + queryParam;
        return new JSONObject(APIService.GETRequest(urlRoute));
    }

    /**
     * Function that parses variables to json needed for the request body
     * @param name name of the subject
     * @param desc description of the subject
     * @return the parsed string
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
