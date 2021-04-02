package com.matchingSystem.API;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserAPI implements APIBehaviour {
    private static String route = "/user";
    private static APIManager apiManager = new APIManager();

    @Override
    public JSONArray getAll() {
        return new JSONArray(apiManager.GETRequest(route));
    }

    @Override
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
    public JSONObject deleteById(String id) {
        return null;
    }

    @Override
    public JSONObject updatePartialById(String id) {

        return null;
    }

    public JSONObject userLogin(String username, String password) {
        String urlRoute = route + "/login?jwt=true";
        StringBuilder jsonParam = new StringBuilder();
        jsonParam.append("{");
        jsonParam.append(String.format("\"userName\": \"%s\",", username));
        jsonParam.append(String.format("\"password\": \"%s\"", password));
        jsonParam.append("}");

        // call request
        String response = apiManager.POSTRequest(urlRoute, jsonParam);
        return new JSONObject(response);
    }
}
