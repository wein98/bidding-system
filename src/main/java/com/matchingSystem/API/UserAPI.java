package com.matchingSystem.API;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserAPI implements APIBehaviour {
    private static String route = "/user";

    /**
     * Get all users
     * @return JSONArray of all the users
     */
    @Override
    public JSONArray getAll() {
        return new JSONArray(APIManager.GETRequest(route));
    }

    public JSONObject create() {
        return null;
    }

    /**
     * Get a user by userId
     * @param id the target user id
     * @return a user
     */
    @Override
    public JSONObject getById(String id) {
        String urlRoute = route + "/" + id;
        String jsonResponse = APIManager.GETRequest(urlRoute);

        return new JSONObject(jsonResponse);
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    public JSONObject updatePartialById(String id) {

        return null;
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

        // call request
        String response = APIManager.UpdateRequest(urlRoute, jsonParam, APIManager.POST);
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
        String response = APIManager.UpdateRequest(urlRoute, jsonParam, APIManager.POST);
        return new JSONObject(response);
    }
}
