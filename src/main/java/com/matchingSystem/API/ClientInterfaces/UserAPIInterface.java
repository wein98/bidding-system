package com.matchingSystem.API.ClientInterfaces;

import com.matchingSystem.API.ClientInterfaces.BaseAPIInterface;
import org.json.JSONObject;

public interface UserAPIInterface extends BaseAPIInterface {
    JSONObject userLogin(String username, String password);
    JSONObject userVerifyToken(String jwt);
}
