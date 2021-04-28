package com.matchingSystem.Model;

import com.matchingSystem.Poster;
import com.matchingSystem.Utility;
import java.sql.Timestamp;

public class CloseBid extends Bid{

    protected boolean closed = false; // indicate if a Bid is closed
    private Message tutorMessage;
    private Message studentMessage;

    public CloseBid(){
        super();
    }

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

    @Override
    public void selectBidder(BidOfferModel offer){
        if(this.dateClosedDown != null ) {
            this.dateClosedDown = new Timestamp(System.currentTimeMillis());
            additionalInfo.put("successfulBidder",offer.getOfferTutorId());
        }else{
            System.out.println("Bid already closed!");
        }
    }

    @Override
    public void close() {
        this.closed = true;
        this.dateClosedDown = new Timestamp(System.currentTimeMillis());
    }

    public void updateTutorMsg(String content){
        this.tutorMessage.updateMessageContent(content);
    }

    public void updateStudentMsg(String content){
        this.studentMessage.updateMessageContent(content);
    }

    public void setTutorMessage(Message message){
        this.tutorMessage = message;
    }

    public void setStudentMessage(Message message) {
        this.studentMessage = message;
    }

    /**
     * Retrieve the offer attached in a conversation
     * @param message the message referred to
     * @return the offer attached to this message
     */
    public BidOfferModel getOfferFromMessage(Message message){
        return message.getLinkedOffer();
    }


    // Getters

    public Message getTutorMessage(){
        return this.tutorMessage;
    }

    public Message getStudentMessage() {
        return studentMessage;
    }

    @Override
    public String getDateCreated() {
        return Utility.sdf2.format(this.dateCreated);
    }

    public String getType() {
        return type;
    }

    @Override
    public String getDuration() {
        if (additionalInfo != null) {
            return additionalInfo.getString("duration");
        }
        return null;
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
        if (additionalInfo != null) {
            return additionalInfo.getString("numOfLesson");
        }
        return null;
    }

    @Override
    public String getRate() {
        if (additionalInfo != null) {
            return additionalInfo.getString("rate");
        }
        return null;
    }

    public String getId() { return this.id; }
    @Override
    public String getDayTime() {
        if (additionalInfo != null) {
            return additionalInfo.getString("prefDay") + " ,"
                    + this.additionalInfo.getString("time")
                    + this.additionalInfo.getString("dayNight");
        }
        return null;
    }
    @Override
    public int getCompetencyLevel() {
        if (additionalInfo != null) {
            return additionalInfo.getInt("competencyLevel");
        }
        return 0;
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
