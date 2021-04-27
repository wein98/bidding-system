package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.Model.Bid;
import com.matchingSystem.Poster;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public class OpenBid implements Bid {
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

    protected ArrayList<BidOffer> bidders;
    protected boolean closed = false; // indicate if a Bid is closed

    public OpenBid(){}

    // mapping nested object
    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackNested(Map<String,Object> addInfo) {
        this.additionalInfo = new JSONObject(
                (String) addInfo.get("rate"), // double
                (String) addInfo.get("duration"), // int
                (String) addInfo.get("numberOfSession"), // int
                (String) addInfo.get("freeLesson")); // boolean
    }

    /**
     * Select successful bidder
     * @param offer the offer that the student choose to accept
     */
    @Override
    public void selectBidder(BidOffer offer){
        if(this.dateClosedDown != null ) {
            this.dateClosedDown = new Timestamp(System.currentTimeMillis());
            additionalInfo.put("successfulBidder",offer.getOfferTutorId());
        }else{
            System.out.println("Bid already closed!");
        }
    }

    /**
     * Check if a Bid request is still valid/active
     * @return true if the request already expired, otherwise false
     */
    @Override
    public boolean isExpired(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp creation = this.dateCreated;
        Long interval = now.getTime() - creation.getTime();
        long minutes = (interval / 1000) / 60;
        if (minutes >= 30){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Close out the Bid Request when no action is carried out by the student before expiry
     */
    @Override
    public void close() {
        this.closed = true;
        this.dateClosedDown = new Timestamp(System.currentTimeMillis());
        additionalInfo.put("successfulBidder","undefined");
    }

    /**
     * Tutor can perform the buy out of a Bid request
     * @param tutorId the ID of the tutor who buy out the request
     */
    public void buyOut(String tutorId){
        this.closed = true;
        this.dateClosedDown = new Timestamp(System.currentTimeMillis());
        additionalInfo.put("successfulBidder", tutorId);
    }

    @Override
    public String getType() {
        return type;
    }
}
