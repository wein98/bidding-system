package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.Poster;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

public abstract class Bid {
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

    // mapping nested User object
    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackNested(Map<String,Object> addInfo) {
        this.additionalInfo = new JSONObject(
                (String) addInfo.get("rate"), // double
                (String) addInfo.get("duration"), // int
                (String) addInfo.get("numberOfSession"), // int
                (String) addInfo.get("freeLesson")); // boolean
    }

    public void selectBidder(BidOffer offer){
//        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(this.dateClosedDown != null ) {
            this.dateClosedDown = new Timestamp(System.currentTimeMillis());
            additionalInfo.put("successfulBidder",offer.getOfferTutorId());
        }else{
            System.out.println("Bid already closed!");
        }
    }

    public abstract boolean isExpired();

    @Override
    public String toString() {
        return "Bid id: " + this.id + "\nBid type: " + this.type;
    }
}
