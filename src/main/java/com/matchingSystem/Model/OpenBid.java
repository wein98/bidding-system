package com.matchingSystem.Model;

import com.matchingSystem.Poster;
import com.matchingSystem.Utility;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OpenBid extends Bid{

    protected ArrayList<BidOfferModel> bidders;
    protected boolean closed = false; // indicate if a Bid is closed

    public OpenBid(){
        super();
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

    @Override
    public String getExpireDuration() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp creation = this.dateCreated;
        Long interval = now.getTime() - creation.getTime();
        long minutes = 30 - ((interval / 1000) / 60);
        return minutes + " minutes";

        // TODO: do something if duration is negative
    }

    @Override
    public void close() {
        // TODO: should i call the close down API here ?
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

    // Getters

    @Override
    public String getDateCreated() {
        return Utility.sdf2.format(this.dateCreated);
    }

    @Override
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
        return initiator;
    }

    @Override
    public Subject getSubject() {
        return subject;
    }

    @Override
    public String getId() { return this.id; }

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

    @Override
    public String getDayTime() {
        if (additionalInfo != null) {
            String retVal = additionalInfo.getString("prefDay") + ",";
            retVal += additionalInfo.getString("time");
            retVal += additionalInfo.getString("dayNight");
            return retVal;
        }
        return null;

    }

    public JSONObject getAdditionalInfo() {
        return additionalInfo;
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
