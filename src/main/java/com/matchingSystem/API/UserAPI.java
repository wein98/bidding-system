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
        String jsonResponse = apiManager.GETRequest(route);

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
        String param = String.format(
                "{\n" + "\"userName\": \"%s\",\r\n" +
                        "\"password\": \"%s\"\r\n" + "}", username, password);

        String response = apiManager.POSTRequest(urlRoute, param);
        return new JSONObject(response);
    }
}
