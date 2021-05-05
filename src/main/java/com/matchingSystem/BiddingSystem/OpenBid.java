package com.matchingSystem.BiddingSystem;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.BiddingSystem.Bid;
import com.matchingSystem.Model.BidOfferModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class OpenBid extends Bid {

    protected ArrayList<BidOfferModel> bidders;
    protected boolean closed = false; // indicate if a Bid is closed

    public OpenBid(){
        super();
    }

//    @Override
//    public void selectBidder(BidOfferModel offer){
//        if(this.dateClosedDown != null ) {
//            this.dateClosedDown = new Timestamp(System.currentTimeMillis());
//            additionalInfo.put("successfulBidder",offer.getOfferTutorId());
//        }else{
//            System.out.println("Bid already closed!");
//        }
//    }

    @Override
    public boolean isExpired(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        System.out.println(now.toString());
        Timestamp creation = this.dateCreated;
        Long interval = now.getTime() - creation.getTime();
        long minutes = (interval / 1000) / 60;
        if (minutes >= 30) {
            // automatically selects the last bid offer
            if (this.dateClosedDown == null && getBidOffers().size() > 0) {
                selectBidder(getBidOffers().get(getBidOffers().size()-1));

            } else if (this.dateClosedDown == null) {
                // no bidders, close the bid
                additionalInfo.put("successfulBidder","undefined");
                // call update bid API
                APIFacade.updateBidById(getId(), getAdditionalInfo());

                close();
            }
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

//    @Override
//    public void close() {
//        this.closed = true;
//        this.dateClosedDown = new Timestamp(System.currentTimeMillis());
//        additionalInfo.put("successfulBidder","undefined");
//    }

    /**
     * Tutor can perform the buy out of a Bid request
     * @param tutorId the ID of the tutor who buy out the request
     */
    public void buyOut(String tutorId){
//        this.dateClosedDown = new Timestamp(System.currentTimeMillis()); // this is handled in close()

        JSONObject bidInfo = new JSONObject();
        bidInfo.put("offerTutorId", tutorId);
        bidInfo.put("duration", getDuration());
        bidInfo.put("numOfLesson", getNoLessons());
        bidInfo.put("prefDay", additionalInfo.getString("prefDay"));
        bidInfo.put("time", additionalInfo.getString("time"));
        bidInfo.put("dayNight", additionalInfo.getString("dayNight"));
        bidInfo.put("offerRate", getRate());
        // TODO: add LessonInfo?

        additionalInfo.put("successfulBidder", bidInfo);

        APIFacade.updateBidById(getId(), getAdditionalInfo());

        // TODO: create Contract API here

        close();
        System.out.println("Buy out successful.");
    }

    /**
     * Tutor offers an open bid.
     * @param bidOffer
     */
    public void tutorOfferBid(JSONObject bidOffer) {
        bidders = getBidOffers();
        String tutorId = bidOffer.getString("offerTutorId");
        int toRemoveIndex = -1;

        // check if there's any other bid offers to find previously offered by this tutor
        if (bidders != null) {
            // look for the bidoffers offered by the tutorId previously
            for (int i=0; i<bidders.size(); i++) {
                if (tutorId.equals(bidders.get(i).getOfferTutorId())) {
                    toRemoveIndex = i;
                    break;
                }
            }
        }

        // remove old bidoffer and add new updated one
        if (toRemoveIndex >= 0) {    // if there's a previous bid offers offered
            bidders.remove(toRemoveIndex);
        }
        JSONArray bidOffers = new JSONArray(bidders);
        bidOffers.put(bidOffer);
        // remove the whole list and insert again
        getAdditionalInfo().remove("bidOffers");
        getAdditionalInfo().put("bidOffers", bidOffers);

        APIFacade.updateBidById(getId(), getAdditionalInfo());
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
