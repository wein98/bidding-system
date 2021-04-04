package com.matchingSystem.API;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserAPI implements APIBehaviour {
    private static String route = "/user";
    private static APIManager apiManager = new APIManager();

    // call get all users request
    @Override
    public JSONArray getAll() {
        return new JSONArray(apiManager.GETRequest(route));
    }

    public JSONObject create() {
        return null;
    }

    // get user response by userId
    @Override
    public JSONObject getById(String id) {
        String urlRoute = route + "/" + id;
        String jsonResponse = apiManager.GETRequest(urlRoute);

        return new JSONObject(jsonResponse);
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    public JSONObject updatePartialById(String id) {

        return null;
    }

    // call user login request with username and password
    public JSONObject userLogin(String username, String password) {
        String urlRoute = route + "/login?jwt=true";
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"userName\": \"%s\",", username));
        jsonParam.append(String.format("\"password\": \"%s\"", password));
        jsonParam.append("}");

        // call request
        String response = apiManager.UpdateRequest(urlRoute, jsonParam, APIManager.POST );
        return new JSONObject(response);
    }

    public JSONObject userVerifyToken(String jwt) {
        String urlRoute = route + "/verify-token";
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"jwt\": \"%s\",", jwt));
        jsonParam.append("}");

        // call request
        String response = apiManager.UpdateRequest(urlRoute, jsonParam, APIManager.POST );
        return new JSONObject(response);
    }
}
