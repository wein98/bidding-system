package com.matchingSystem.BiddingSystem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Model.BidOfferModel;
import com.matchingSystem.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

// holds the common fields of OpenBid and CloseBid
public abstract class Bid extends Observable implements BidInterface {
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

    protected LessonInfo lessonInfo;

    protected boolean closed = false;
    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackNested(Map<String,Object> addInfo) {
        if(addInfo.size() > 0){
            this.additionalInfo = new JSONObject(addInfo);

            lessonInfo = new LessonInfo(
                    additionalInfo.getString("duration"),
                    additionalInfo.getString("numOfLesson"),
                    additionalInfo.getString("dayNight"),
                    additionalInfo.getString("rate"),
                    additionalInfo.getString("prefDay"),
                    additionalInfo.getString("time")
            );
        }
    }

    /**
     * Select the successful bidder and close down the bid
     * @param offer the offer that the student choose to accept
     */
    @Override
    public void selectBidder(BidOfferModel offer) {
        if (this.dateClosedDown == null) {
            this.additionalInfo.put("successfulBidder", offer.getOfferTutorId());
            // update Bid additionalInfo with successfulBidder property
            APIFacade.updateBidById(this.id, this.additionalInfo);
            close();
        } else {
            System.out.println("Bid already closed!");
        }
    }

    /**
     * Close down the bid
     */
    public void close() {
        this.closed = true;
        this.dateClosedDown = new Timestamp(System.currentTimeMillis());
        // close down the bid
        APIFacade.closeDownBidById(this.id, this.dateClosedDown);
    }

    public Timestamp getDateClosedDown() {
        return dateClosedDown;
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

    public LessonInfo getLessonInfo() {
        return lessonInfo;
    }

    @Override
    public int getCompetencyLevel() {
        if (additionalInfo != null) {
            return additionalInfo.getInt("competencyLevel");
        }
        return 0;
    }

    /**
     * Get offers attached to this bid
     * @return the list of bid offers object
     */
    @Override
    public ArrayList<BidOfferModel> getBidOffers() {
        ArrayList<BidOfferModel> retVal = new ArrayList<>();
        if (additionalInfo != null) {
            JSONArray arr = additionalInfo.getJSONArray("bidOffers");

            if (arr.length() != 0) {
                for (int i=0; i<arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    retVal.add(new BidOfferModel(this, o));
                }
            }
            return retVal;
        }
        return null;
    }
}
