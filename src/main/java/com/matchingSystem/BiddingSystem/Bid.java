package com.matchingSystem.BiddingSystem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Model.BidOfferModel;
import com.matchingSystem.LoginSystem.UserCookie;
import com.matchingSystem.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// holds the common fields of OpenBid and CloseBid
public abstract class Bid implements BidInterface {
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

//    @Override
//    public void selectBidder(BidOfferModel offer){
//        if (this.dateClosedDown != null) {
//            this.dateClosedDown = new Timestamp(System.currentTimeMillis());
//            additionalInfo.put("successfulBidder",offer.getAddInfoJson());
//
//            APIFacade.updateBidById(getId(), getAdditionalInfo());
//
//            // TODO: create Contract API here
//
//            close();
//
//        } else {
//            System.out.println("Bid already closed!");
//        }
//    }
//
//    @Override
//    public void close() {
//        this.dateClosedDown = new Timestamp(System.currentTimeMillis());
//        APIFacade.closeDownBidById(getId());
//
//        System.out.println("Close bid successful.");
//    }


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
                    if (!isExpired()) {
                        retVal.add(new BidOfferModel(this, o));
                    }
                }
            }
            return retVal;
        }
        return null;
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
            Timestamp now = new Timestamp(System.currentTimeMillis());
            LocalDateTime sixMonthsFuture = LocalDate.now().plusMonths(6).atTime(0, 0);
            Timestamp expiry = Timestamp.valueOf(sixMonthsFuture);
            JSONObject lessonInfo = new JSONObject();
            lessonInfo.put("time",offer.getTime());
            lessonInfo.put("dayNight",offer.getDayNight());
            lessonInfo.put("prefDay",offer.getDay());
            lessonInfo.put("numOfLesson",offer.getNumOfLesson());
            lessonInfo.put("duration",offer.getDuration());
            JSONObject additionalInfo = new JSONObject();
            additionalInfo.put("rate",this.getRate());
            APIFacade.createContract(UserCookie.getUser().getId(), offer.getOfferTutorId(), this.getSubject().getId()
                    ,expiry,new JSONObject(),lessonInfo,additionalInfo);
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
        Timestamp closeDownTime = new Timestamp(System.currentTimeMillis());
        this.dateClosedDown = closeDownTime;
        // close down the bid
        APIFacade.closeDownBidById(this.id, closeDownTime);
    }
}
