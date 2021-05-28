package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.BiddingSystem.*;
import com.matchingSystem.LoginSystem.Tutor;
import com.matchingSystem.LoginSystem.UserCookie;
import org.json.JSONObject;

import java.util.Observable;

public class BidOfferModel extends Observable {
    private String bidId;

    private Bid bid;
    private String tutorName;
    private String offerTutorId;
    private int tutorCompLvl;
    private String msgId;
    private JSONObject bidOfferObj;
    private LessonInfo lessonInfo;

    public BidOfferModel(Bid b, JSONObject o){
        this.bid = b;
        this.bidId = b.getId();

        lessonInfo = new LessonInfo(
                o.getString("duration"),
                o.getString("numOfLesson"),
                o.getString("dayNight"),
                o.getString("rate"),
                o.getString("prefDay"),
                o.getString("time")
        );
        // unpack JSONObject o
        this.tutorCompLvl = o.getInt("tutorCompLvl");
        this.offerTutorId = o.getString("offerTutorId");
        this.tutorName = o.getString("tutorName");

        if (this.bid.getType().equals("close")) {
            this.msgId = o.getString("msgId");
        }

        bidOfferObj = o;
    }

    public BidOfferModel(Bid b) {
        this.bid = b;
        this.bidId = b.getId();
    }

    public String getOfferTutorId() {
        return offerTutorId;
    }

    public void updateDDL() {
        // notify observers
        setChanged();
        notifyObservers();
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
            jsonObj.remove("type");
            ((CloseBid) this.bid).tutorOfferBid(jsonObj);
        }
    }

    /**
     * Function to be called when Student picked this Bid Offer to reply message.
     * @param msgContent content of the student's reply
     */
    public void studentReplyMsg(String msgContent) {
        getMsg().studentReplyMsg(msgContent);
    }

    // Getters

    public String getTutorName() {
        return tutorName;
    }

    public int getTutorCompLvl() {
        return tutorCompLvl;
    }

    public String getMsgId() {
        return msgId;
    }

    public Message getMsg() {
        return APIFacade.getMessageById(msgId);
    }

    public JSONObject getAddInfoJson() {
        return bidOfferObj;
    }

    public LessonInfo getLessonInfo() {
        return lessonInfo;
    }
}
