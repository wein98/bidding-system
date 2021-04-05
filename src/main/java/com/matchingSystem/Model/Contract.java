package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.Poster;
import org.json.JSONObject;

import java.sql.Timestamp;

public class Contract {
    @JsonProperty("id")
    private String id;
    @JsonProperty("firstParty")
    private Poster firstParty;
    @JsonProperty("secondParty")
    private Poster secondParty;
    @JsonProperty("subject")
    private Subject subject;
    @JsonProperty("dateCreated")
    private Timestamp dateCreated;
    @JsonProperty("dateSigned")
    private Timestamp dateSigned;
    @JsonProperty("expiryDate")
    private Timestamp expiryDate;
    @JsonProperty("paymentInfo")
    private JSONObject paymentInfo;
    @JsonProperty("lessonInfo")
    private JSONObject lessonInfo;
    @JsonProperty("additionalInfo")
    private JsonProperty additionalInfo;
}