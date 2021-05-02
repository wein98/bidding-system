package com.matchingSystem.API.ClientInterfaces;

import org.json.JSONObject;

import java.sql.Timestamp;

public interface BidAPIInterface extends BaseAPIInterface{
    StringBuilder parseToJsonForCreate(String type, String initiatorId, String subjectId, JSONObject additionalInfo);
    StringBuilder parseToJsonForPartialUpdate(JSONObject additionalInfo);
    boolean closeDownBidById(String id, Timestamp closeDownTime);
}
