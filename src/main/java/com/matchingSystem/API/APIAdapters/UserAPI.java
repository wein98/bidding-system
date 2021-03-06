package com.matchingSystem.API.APIAdapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIService;
import com.matchingSystem.API.ClientInterfaces.UserAPIInterface;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserAPI extends APIRouter implements UserAPIInterface {
    public UserAPI() {
        this.objMapper = new ObjectMapper();
        route = "/user";
    }

    /**
     * Get all users
     * @return JSONArray of all the users
     */
    @Override
    public JSONArray getAll(String queryParam) {
        String urlRoute = route + queryParam;

        return new JSONArray(APIService.GETRequest(urlRoute));
    }

    /**
     * Get a user by userId
     * @param id the target user id
     * @return a user
     */
    @Override
    public JSONObject getById(String id, String queryParam) {
        String urlRoute = route + "/" + id + queryParam;
        String jsonResponse = APIService.GETRequest(urlRoute);

        return new JSONObject(jsonResponse);
    }

    /**
     * User login
     * @param username credential
     * @param password login credential
     * @return jwt token if success, else error msg in json
     */
    // call user login request with username and password
    public JSONObject userLogin(String username, String password) {
        String urlRoute = route + "/login?jwt=true";
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"userName\": \"%s\",", username));
        jsonParam.append(String.format("\"password\": \"%s\"", password));
        jsonParam.append("}");
        System.out.println(jsonParam.toString());
        // call request
        String response = APIService.UpdateRequest(urlRoute, jsonParam, APIService.POST);

        return new JSONObject(response);
    }

    /**
     * User verify with token
     * @param jwt jwt token of the user
     * @return JSONObject the user that holds this jwt token, else a error msg json
     */
    public JSONObject userVerifyToken(String jwt) {
        String urlRoute = route + "/verify-token";
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"jwt\": \"%s\",", jwt));
        jsonParam.append("}");

        // call request
        String response = APIService.UpdateRequest(urlRoute, jsonParam, APIService.POST);
        return new JSONObject(response);
    }

    /**
     * Parsing the param to update an existing user
     * @return the parsed json
     */
    @Override
    public StringBuilder parseToJsonForPartialUpdate(JSONObject additionalInfo) {
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append("\"additionalInfo\": ");
        jsonParam.append(additionalInfo);
        jsonParam.append("}");

        return jsonParam;
    }
}
