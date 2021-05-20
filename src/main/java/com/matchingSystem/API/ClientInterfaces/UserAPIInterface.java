package com.matchingSystem.API.ClientInterfaces;

import org.json.JSONObject;

public interface UserAPIInterface extends BaseAPIInterface {
    JSONObject userLogin(String username, String password);
    JSONObject userVerifyToken(String jwt);
    StringBuilder parseToJsonForPartialUpdate(JSONObject additionalInfo);
}
