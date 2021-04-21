package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.Model.Bid;
import com.matchingSystem.Model.Message;
import com.matchingSystem.Poster;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class CloseBid implements Bid {
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

    protected boolean closed = false; // indicate if a Bid is closed
    private ArrayList<Message> messages; // each tutor sends one message, all content is stored within one message obj

    public CloseBid(){

    }

    public void addMessage(Message message){
        this.messages.add(message);
    }

    public ArrayList<Message> getMessages(){
        return this.messages;
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
        long day = (((interval / 1000) / 60)/ 60)/ 24;
        if (day >= 7){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Retrieve the offer attached in a conversation
     * @param message the message referred to
     * @return the offer attached to this message
     */
    public BidOffer getOfferFromMessage(Message message){
        return message.getLinkedOffer();
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
     * Close out the Bid Request when no action is carried out by the student before expiry
     */
    @Override
    public void close() {
        this.closed = true;
        this.dateClosedDown = new Timestamp(System.currentTimeMillis());
    }
}
