package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.Poster;
import org.json.JSONObject;
import java.sql.Timestamp;

public class Message{
    @JsonProperty("id")
    private String id;
    @JsonProperty("bidId")
    private String bidId;
    @JsonProperty("poster")
    private Poster poster;
    @JsonProperty("datePosted")
    private Timestamp datePosted;
    @JsonProperty("dateLastEdited")
    private Timestamp dateLastEdited;
    @JsonProperty("content")
    private String content;
    @JsonProperty("additionalInfo")
    private JSONObject additionalInfo;

    @Override
    public String toString() {
        return "messageId: " + this.id + "\nbidId: " + this.bidId + "\nposter" + this.poster.toString()+ "\ncontent: " + this.content;
    }
}
