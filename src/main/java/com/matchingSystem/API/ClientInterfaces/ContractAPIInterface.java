package com.matchingSystem.API.ClientInterfaces;

import java.sql.Timestamp;

public interface ContractAPIInterface extends BaseAPIInterface {
    StringBuilder parseToJsonForCreate(String firstPartyId, String secondPartyId, String subjectId, Timestamp expiryDate);
    StringBuilder parseToJsonForPartialUpdate(String firstPartyId, String secondPartyId, String subjectId, Timestamp expiryDate);
    boolean sign(String id);
}
