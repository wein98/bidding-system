package com.matchingSystem.Model;

import com.matchingSystem.API.APIAdapters.UserAPI;
import org.json.JSONObject;

public class LoginModel {
    private UserAPI userAPI = UserAPI.getInstance();

    public JSONObject getUserLogin(String username, String password) {
        return userAPI.userLogin(username, password);
    }
}
