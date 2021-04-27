package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.Poster;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.util.Map;

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

    private BidOfferModel linkedOffer; // the Offer that this message is linked to

    Message(){}

    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackNested(Map<String,Object> addInfo) {
        if(addInfo.size() > 0){
            this.additionalInfo = new JSONObject(addInfo);
        }else{
            this.additionalInfo = new JSONObject();
        }
    }
    /**
     * Get the attached offer offered by tutor
     * @return
     */
    public BidOfferModel getLinkedOffer(){
        return this.linkedOffer;
    }
    /**
     * Attach the offer that this message is linked to OR update the offer
     * @param offer
     */
    public void attachOffer(BidOfferModel offer){
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
