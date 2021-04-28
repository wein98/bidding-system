package com.matchingSystem.Model;

import com.matchingSystem.UserCookie;
import org.json.JSONObject;

import java.util.Observable;

public class BidOfferModel extends Observable {
    private String bidId;
    private String offerTutorId;
    private double offerRate;
    private int numberOfLesson;
    private boolean freeLesson;

    public BidOfferModel(String linkedBidId, String offerTutorId, double offerRate, int numOfLesson,
                        boolean freeLesson) {
        this.bidId = linkedBidId;
        this.offerTutorId = offerTutorId;
        this.offerRate = offerRate;
        this.numberOfLesson = numOfLesson;
        this.freeLesson = freeLesson;
    }

    public BidOfferModel(Bid b){
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
        if (jsonObj.getString("type").equals("open")) {
            jsonObj.remove("type");
            tutorObj.makeOfferToOpenBidding(bidId, jsonObj);
        }else if(jsonObj.getString("type").equals("close")) {
            jsonObj.remove("type");
            tutorObj.sendMessage(bidId, jsonObj);
        }
    }
}
