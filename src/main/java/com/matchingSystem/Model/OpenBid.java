package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.matchingSystem.Poster;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
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
    protected ObjectNode additionalInfo;

    protected ArrayList<BidOffer> bidders;
    protected boolean closed = false; // indicate if a Bid is closed

    public OpenBid(){}

    // mapping nested object
    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackNested(Map<String,Object> addInfo) {
        if (addInfo.size() > 0) {
            ObjectMapper objMapper = new ObjectMapper();
            this.additionalInfo = objMapper.createObjectNode();
            additionalInfo.put("rate", (String) addInfo.get("rate"));
            additionalInfo.put("dayNight", (String) addInfo.get("dayNight"));
            additionalInfo.put("numOfLesson", (String) addInfo.get("numOfLesson"));
            additionalInfo.put("prefDay", (String) addInfo.get("prefDay"));
            additionalInfo.put("time", (String) addInfo.get("time"));
        }
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

    @Override
    public Poster getInitiator() {
        return initiator;
    }

    @Override
    public Subject getSubject() {
        return subject;
    }

    @Override
    public String getNoLessons() {
        if (additionalInfo != null) {
            return additionalInfo.get("numOfLesson").asText();
        }
        return null;
    }

    @Override
    public String getRate() {
        if (additionalInfo != null) {
            return additionalInfo.get("rate").asText();
        }
        return null;
    }

    @Override
    public String getDayTime() {
        if (additionalInfo != null) {
            String retVal = additionalInfo.get("prefDay").asText() + ",";
            retVal += additionalInfo.get("time").asText();
            retVal += additionalInfo.get("dayNight").asText();
            return retVal;
        }
        return null;

    }

    @Override
    public String toString() {
        return "OpenBid{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", initiator=" + initiator +
                ", dateCreated=" + dateCreated +
                ", dateClosedDown=" + dateClosedDown +
                ", subject=" + subject +
                ", additionalInfo=" + additionalInfo +
                ", bidders=" + bidders +
                ", closed=" + closed +
                '}';
    }
}
