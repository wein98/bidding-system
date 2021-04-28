package com.matchingSystem.Model;

import com.matchingSystem.UserCookie;
import org.json.JSONObject;

import java.util.Observable;

public class BidOfferModel extends Observable {
    private String bidId;
    private String offerTutorId;
    private boolean freeLesson;

    private Bid bid;
    private String tutorName;
    private int tutorCompLvl;
    private String numberOfLesson;
    private String dayTime;
    private String duration;
    private String offerRate;
    private String subjectName;

    public BidOfferModel(String linkedBidId, String offerTutorId, String offerRate, String numOfLesson,
                        boolean freeLesson) {
        this.bidId = linkedBidId;
        this.offerTutorId = offerTutorId;
        this.offerRate = offerRate;
        this.numberOfLesson = numOfLesson;
        this.freeLesson = freeLesson;
    }

    public BidOfferModel(Bid b, JSONObject o){
        this.bid = b;
        this.bidId = b.getId();

        // unpack JSONObject o
        this.duration = o.getString("duration");
        this.tutorCompLvl = o.getInt("tutorCompLvl");
        this.tutorName = o.getString("tutorName");
        this.numberOfLesson = o.getString("numOfLesson");
        this.dayTime = o.getString("prefDay") + ","
                + o.getString("time")
                + o.getString("dayNight");
        this.offerRate = o.getString("rate");
    }

    public BidOfferModel(Bid b) {
        this.bid = b;
        this.bidId = b.getId();
    }

    public String getOfferTutorId() {
        return offerTutorId;
    }

    public void updateDDL(){
        // notify observers
        setChanged();
        notifyObservers();
    };

    public JSONObject toJSONObj() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("bidId", this.bidId);
        jsonObj.put("offerTutorId", this.offerTutorId);
        jsonObj.put("offerRate", this.offerRate);
        jsonObj.put("numOfLesson", this.numberOfLesson);
        jsonObj.put("freeLesson", this.freeLesson);
        return jsonObj;
    }

    public void sendOffer(JSONObject jsonObj){
        Tutor tutorObj = (Tutor) UserCookie.getUser();

        // add in tutor's info
        jsonObj.put("tutorId", tutorObj.getId());
        jsonObj.put("tutorName", tutorObj.getFullName());
        jsonObj.put("tutorCompLvl", tutorObj.getCompetencyLvlFromSubject(bid.getSubject()));

        if (jsonObj.getString("type").equals("open")) {
            jsonObj.remove("type");
            tutorObj.makeOfferToOpenBidding(bidId, jsonObj);

        }else if(jsonObj.getString("type").equals("close")) {
            jsonObj.remove("type");
            tutorObj.sendMessage(bidId, jsonObj);
        }
    }

    // Getters

    public String getTutorName() {
        return tutorName;
    }

    public int getTutorCompLvl() {
        return tutorCompLvl;
    }

    public String getNumberOfLesson() {
        return numberOfLesson;
    }

    public String getDayTime() {
        return dayTime;
    }

    public String getDuration() {
        return duration;
    }

    public String getOfferRate() {
        return offerRate;
    }

    public String getSubjectName() {
        return subjectName;
    }
}