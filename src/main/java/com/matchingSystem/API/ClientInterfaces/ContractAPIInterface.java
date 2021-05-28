package com.matchingSystem.API.ClientInterfaces;

import org.json.JSONObject;
import java.sql.Timestamp;

public interface ContractAPIInterface extends BaseAPIInterface {
    StringBuilder parseToJson(String firstPartyId, String secondPartyId, String subjectId, Timestamp expiryDate, JSONObject paymentInfo, JSONObject lessonInfo, JSONObject additionalInfo);
    boolean sign(String id);
}
