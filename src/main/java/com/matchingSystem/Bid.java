package com.matchingSystem;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

import java.sql.Timestamp;

public class Bid {
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("initiator")
    private Poster initiator;
    @JsonProperty("dateCreated")
    private Timestamp dateCreated;
    @JsonProperty("dateClosedDown")
    private Timestamp dateClosedDown;
    @JsonProperty("subject")
    private Subject subject;
    @JsonProperty("additionalInfo")
    private JSONObject additionalInfo;

    @Override
    public String toString() {
        return "Bid id: " + this.id + "\nBid type: " + this.type;
    }
}
