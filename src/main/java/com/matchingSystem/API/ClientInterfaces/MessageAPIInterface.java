package com.matchingSystem.API.ClientInterfaces;

import org.json.JSONObject;

public interface MessageAPIInterface extends BaseAPIInterface {
    StringBuilder parseToJsonForCreate(String bidId, String posterId, String content);
    StringBuilder parseToJsonForPartialUpdate(String content, JSONObject additionalInfo);
}
