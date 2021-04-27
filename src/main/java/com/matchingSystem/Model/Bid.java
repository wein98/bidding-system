package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.Poster;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.List;

// holds the common fields of OpenBid and CloseBid
public abstract class Bid implements BidInterface{
    @JsonProperty("id")
    protected String id;
    @JsonProperty("type")
    protected String type;
    @JsonProperty("initiator")
    protected Poster initiator;
    @JsonProperty("dateCreated")
    protected Timestamp dateCreated;
    @JsonProperty("dateClosedDown")
    protected Timestamp dateClosedDown;
    @JsonProperty("subject")
    protected Subject subject;
    @JsonProperty("additionalInfo")
    protected JSONObject additionalInfo;

    @JsonProperty(value = "messages",required = false)
    protected List<Message> messages;
}
