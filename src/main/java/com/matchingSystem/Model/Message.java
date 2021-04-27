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

    private BidOffer linkedOffer; // the Offer that this message is linked to

    Message(){}

    /**
     * Get the attached offer offered by tutor
     * @return
     */
    public BidOffer getLinkedOffer(){
        return this.linkedOffer;
    }
    /**
     * Attach the offer that this message is linked to OR update the offer
     * @param offer
     */
    public void attachOffer(BidOffer offer){
        this.linkedOffer = offer;
    }

    /**
     * Append new content in the conversation
     * @param content
     */
    public void updateMessageContent(String content){
        this.content = content;
        this.dateLastEdited = new Timestamp(System.currentTimeMillis());
    }
    @Override
    public String toString() {
        return "messageId: " + this.id + "\nbidId: " + this.bidId + "\nposter" + this.poster.toString()+ "\ncontent: " + this.content;
    }
}
