package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Constant;
import com.matchingSystem.UserCookie;
import org.json.JSONObject;

import java.util.Observable;

public class BidOfferModel extends Observable {
    private String bidId;
    private boolean freeLesson;

    private Bid bid;
    private String tutorName;
    private String offerTutorId;
    private int tutorCompLvl;
    private String numOfLesson;
    private String dayTime;
    private String duration;
    private String offerRate;
    private String subjectName;
    private String msgId;

//    public BidOfferModel(String linkedBidId, String offerTutorId, String offerRate, String numOfLesson,
//                        boolean freeLesson) {
//        this.bidId = linkedBidId;
//        this.offerTutorId = offerTutorId;
//        this.offerRate = offerRate;
//        this.numberOfLesson = numOfLesson;
//        this.freeLesson = freeLesson;
//    }

    public BidOfferModel(Bid b, JSONObject o){
        this.bid = b;
        this.bidId = b.getId();

        // unpack JSONObject o
        this.duration = o.getString("duration");
        this.tutorCompLvl = o.getInt("tutorCompLvl");
        this.offerTutorId = o.getString("offerTutorId");
        this.tutorName = o.getString("tutorName");
        this.numOfLesson = o.getString("numOfLesson");
        this.dayTime = o.getString("prefDay") + ","
                + o.getString("time")
                + o.getString("dayNight");
        this.offerRate = o.getString("rate");

        if (this.bid.getType().equals("close")) {
            this.msgId = o.getString("msgId");
        }
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
        jsonObj.put("numOfLesson", this.numOfLesson);
        jsonObj.put("freeLesson", this.freeLesson);
        return jsonObj;
    }

    public void sendOffer(JSONObject jsonObj) {
        Tutor tutorObj = (Tutor) UserCookie.getUser();

        // add in tutor's info
        jsonObj.put("offerTutorId", tutorObj.getId());
        jsonObj.put("tutorName", tutorObj.getFullName());
        jsonObj.put("tutorCompLvl", tutorObj.getCompetencyLvlFromSubject(bid.getSubject()));

        if (jsonObj.getString("type").equals("open")) {
            jsonObj.remove("type");
            ((OpenBid) this.bid).tutorOfferBid(jsonObj);

        } else if(jsonObj.getString("type").equals("close")) {
            /*
               TODO: make sure jsonObj contains message property! Refer tutorObj.sendMessage to get the message property's key
             */
            jsonObj.remove("type");
            ((CloseBid) this.bid).tutorOfferBid(jsonObj);
        }
    }

    /**
     * Function to be called when Student picked this Bid Offer to reply message.
     * @param msgContent content of the student's reply
     */
    public void studentReplyMsg(String msgContent) {
        Message msgObj = (Message) APIFacade.getMessageAPI().getById(msgId, Constant.NONE);
        msgObj.studentReplyMsg(msgContent);
    }

    // Getters

    public String getTutorName() {
        return tutorName;
    }

    public int getTutorCompLvl() {
        return tutorCompLvl;
    }

    public String getNumOfLesson() {
        return numOfLesson;
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

    public String getMsgId() {
        return msgId;
    }
}
