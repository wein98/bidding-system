package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.Poster;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
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
//    @JsonProperty("dateSigned")
//    private Timestamp dateSigned;
    @JsonProperty("expiryDate")
    private Timestamp expiryDate;
//    @JsonProperty("paymentInfo")
//    private JSONObject paymentInfo;
//    @JsonProperty("lessonInfo")
//    private JSONObject lessonInfo;
//    @JsonProperty("additionalInfo")
//    private JSONObject additionalInfo;


    public Poster getFirstParty() {
        return firstParty;
    }

    public Poster getSecondParty() {
        return secondParty;
    }

    public String getDateCreated() {
        return new SimpleDateFormat("dd/MM/yyyy").format(dateCreated);
    }

    public Subject getSubject() {
        return subject;
    }
}