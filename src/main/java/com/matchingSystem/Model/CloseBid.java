package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.Model.BidInterface;
import com.matchingSystem.Model.Message;
import com.matchingSystem.Poster;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CloseBid extends Bid{
//    @JsonProperty("id")
//    protected String id;
//    @JsonProperty("type")
//    protected String type;
//    @JsonProperty("initiator")
//    protected Poster initiator;
//    @JsonProperty("dateCreated")
//    protected Timestamp dateCreated;
//    @JsonProperty("dateClosedDown")
//    protected Timestamp dateClosedDown;
//    @JsonProperty("subject")
//    protected Subject subject;
//    @JsonProperty("additionalInfo")
//    protected JSONObject additionalInfo;

    protected boolean closed = false; // indicate if a Bid is closed
    private Message tutorMessage;
    private Message studentMessage;


    public CloseBid(){
        super();
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackNested(Map<String,Object> addInfo) {
        if(addInfo.size() > 0){
            this.additionalInfo = new JSONObject(addInfo);
        }else{
            this.additionalInfo = new JSONObject();
        }
    }

    public void updateTutorMsg(String content){
        this.tutorMessage.updateMessageContent(content);
    }

    public void updateStudentMsg(String content){
        this.studentMessage.updateMessageContent(content);
    }

    public Message getTutorMessage(){
        return this.tutorMessage;
    }

    public Message getStudentMessage() {
        return studentMessage;
    }

    public void setTutorMessage(Message message){
        this.tutorMessage = message;
    }

    public void setStudentMessage(Message message) {
        this.studentMessage = message;
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

    @Override
    public String getExpireDuration() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp creation = this.dateCreated;
        Long interval = now.getTime() - creation.getTime();
        long day = (((interval / 1000) / 60)/ 60)/ 24;

        if (day > 0) {
            return day + " days";
        } else {
            long minutes = (interval / 1000) / 60;
            return minutes + " minutes";
        }
    }

    /**
     * Retrieve the offer attached in a conversation
     * @param message the message referred to
     * @return the offer attached to this message
     */
    public BidOfferModel getOfferFromMessage(Message message){
        return message.getLinkedOffer();
    }

    /**
     * Select successful bidder
     * @param offer the offer that the student choose to accept
     */
    @Override
    public void selectBidder(BidOfferModel offer){
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

    @Override
    public String getDateCreated() {
        return null;
    }

    public String getType() {
        return type;
    }

    @Override
    public Poster getInitiator() {
        return this.initiator;
    }

    @Override
    public Subject getSubject() {
        return this.subject;
    }

    @Override
    public String getNoLessons() {
        return this.additionalInfo.getString("numOfLesson");
    }

    @Override
    public String getRate() {
        return this.additionalInfo.getString("rate");
    }

    public String getId() { return this.id; }
    @Override
    public String getDayTime() {
        return this.additionalInfo.getString("prefDay") + " ," + this.additionalInfo.getString("time") + this.additionalInfo.getString("dayNight");
    }

    @Override
    public String toString() {
        return "CloseBid{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", initiator=" + initiator +
                ", dateCreated=" + dateCreated +
                ", dateClosedDown=" + dateClosedDown +
                ", subject=" + subject +
                ", additionalInfo=" + additionalInfo +
                ", closed=" + closed +
                '}';
    }
}
