package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Poster;
import com.matchingSystem.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// holds the common fields of OpenBid and CloseBid
public abstract class Bid implements BidInterface{
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

    @JsonProperty(value = "messages",required = false)
    protected List<Message> messages;

    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackNested(Map<String,Object> addInfo) {
        if(addInfo.size() > 0){
            this.additionalInfo = new JSONObject(addInfo);
        }
    }

    @Override
    public void selectBidder(BidOfferModel offer){
        if (this.dateClosedDown != null) {
            this.dateClosedDown = new Timestamp(System.currentTimeMillis());
            additionalInfo.put("successfulBidder",offer.getAddInfoJson());

            APIFacade.updateBidById(getId(), getAdditionalInfo());

            // TODO: create Contract API here

            close();

        } else {
            System.out.println("Bid already closed!");
        }
    }

    @Override
    public void close() {
        this.dateClosedDown = new Timestamp(System.currentTimeMillis());
        APIFacade.closeDownBidById(getId());

        System.out.println("Close bid successful.");
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Poster getInitiator() {
        return this.initiator;
    }

    @Override
    public String getDateCreated() {
        return Utility.sdf2.format(this.dateCreated);
    }

    @Override
    public Subject getSubject() {
        return subject;
    }

    @Override
    public JSONObject getAdditionalInfo() {
        return additionalInfo;
    }

    @Override
    public String getDuration() {
        if (additionalInfo != null) {
            return additionalInfo.getString("duration");
        }
        return null;
    }

    @Override
    public String getNoLessons() {
        if (additionalInfo != null) {
            return additionalInfo.getString("numOfLesson");
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

    @Override
    public String getRate() {
        if (additionalInfo != null) {
            return additionalInfo.getString("rate");
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
    public ArrayList<BidOfferModel> getBidOffers() {
        ArrayList<BidOfferModel> retVal = new ArrayList<>();
        if (additionalInfo != null) {
            JSONArray arr = additionalInfo.getJSONArray("bidOffers");

            if (arr.length() != 0) {
                for (int i=0; i<arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    if (!isExpired()) {
                        retVal.add(new BidOfferModel(this, o));
                    }
                }
            }
            return retVal;
        }
        return null;
    }
}
