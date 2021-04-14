package com.matchingSystem.Model;

import com.matchingSystem.API.UserAPI;
import org.json.JSONObject;

public class LoginModel {
    private UserAPI userAPI = new UserAPI();

    public JSONObject getUserLogin(String username, String password) {
        return userAPI.userLogin(username, password);
    }
}
