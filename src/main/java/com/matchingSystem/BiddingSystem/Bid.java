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

    protected boolean closed = false;
    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackNested(Map<String,Object> addInfo) {
        if(addInfo.size() > 0){
            this.additionalInfo = new JSONObject(addInfo);
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
            // create Contract , set expiry to be 6 months
//            LocalDateTime sixMonthsFuture = LocalDate.now().plusMonths(6).atTime(0, 0);
//            Timestamp expiry = Timestamp.valueOf(sixMonthsFuture);
//            JSONObject lessonInfo = new JSONObject();
//            lessonInfo.put("time",offer.getTime());
//            lessonInfo.put("dayNight",offer.getDayNight());
//            lessonInfo.put("prefDay",offer.getDay());
//            lessonInfo.put("numOfLesson",offer.getNumOfLesson());
//            lessonInfo.put("duration",offer.getDuration());
//            JSONObject additionalInfo = new JSONObject();
//            additionalInfo.put("rate",this.getRate());
//            APIFacade.createContract(UserCookie.getUser().getId(), offer.getOfferTutorId(), this.getSubject().getId()
//                    ,expiry,new JSONObject(),lessonInfo,additionalInfo);
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
