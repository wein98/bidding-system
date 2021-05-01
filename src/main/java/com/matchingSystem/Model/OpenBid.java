package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class OpenBid extends Bid {

    protected ArrayList<BidOfferModel> bidders;

    public OpenBid(){
        super();
    }

    @Override
    public boolean isExpired(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp creation = this.dateCreated;
        Long interval = now.getTime() - creation.getTime();
        long minutes = (interval / 1000) / 60;
        if (minutes >= 30) {
            // automatically selects the last bid offer
            if (getBidOffers().size() > 0) {
                selectBidder(getBidOffers().get(getBidOffers().size()-1));

            } else {
                // no bidders, close the bid
                additionalInfo.put("successfulBidder","undefined");
                // call update bid API
                StringBuilder params = APIFacade.getBidAPI().parseToJsonForPartialUpdate(getAdditionalInfo());
                APIFacade.getBidAPI().updatePartialById(getId(), params);

                close();
            }
            return true;

        } else {
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
    }

    /**
     * Tutor can perform the buy out of a Bid request
     * @param tutorId the ID of the tutor who buy out the request
     */
    public void buyOut(String tutorId){
        this.dateClosedDown = new Timestamp(System.currentTimeMillis());

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

        StringBuilder params = APIFacade.getBidAPI().parseToJsonForPartialUpdate(getAdditionalInfo());
        APIFacade.getBidAPI().updatePartialById(getId(), params);

        // TODO: create Contract API here

        close();
        System.out.println("Buy out successful.");
    }

    /**
     * Tutor offers an open bid.
     * @param bidOffer
     */
    public void tutorOfferBid(JSONObject bidOffer) {
        String tutorId = bidOffer.getString("offerTutorId");
        int toRemoveIndex = -1;

        // check if there's any other bid offers to find previously offered by this tutor
        if (getBidOffers() != null) {
            // look for the bidoffers offered by the tutorId previously
            for (int i=0; i<getBidOffers().size(); i++) {
                if (tutorId.equals(getBidOffers().get(i).getOfferTutorId())) {
                    toRemoveIndex = i;
                    break;
                }
            }
        }

        // remove old bidoffer and add new updated one
        if (toRemoveIndex >= 0) {    // if there's a previous bid offers offered
            getBidOffers().remove(toRemoveIndex);
        }

        JSONArray bidOffers = new JSONArray(getBidOffers());
        bidOffers.put(bidOffer);

        // remove the whole list and insert again
        getAdditionalInfo().remove("bidOffers");
        getAdditionalInfo().put("bidOffers", bidOffers);

        StringBuilder params = APIFacade.getBidAPI().parseToJsonForPartialUpdate(getAdditionalInfo());
        APIFacade.getBidAPI().updatePartialById(getId(), params);
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
                '}';
    }
}
