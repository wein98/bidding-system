package com.matchingSystem.BiddingSystem;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.LoginSystem.UserCookie;
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
    }

    /**
     * Tutor can perform the buy out of a Bid request
     * @param tutorId the ID of the tutor who buy out the request
     */
    public void buyOut(String tutorId){
        System.out.println("buy out is triggered!");
        int compLvl = 0;
        for (Competency competency: UserCookie.getUser().getCompetencies()){
            String subjectId = competency.getSubject().getId();
            if(this.subject.getId().equals(subjectId)){
                compLvl = competency.getLevel();
                break;
            }
        }

        // update the bid obj
        additionalInfo.put("successfulBidder", tutorId);
        this.bidders = getBidOffers();
        JSONArray offersArr = new JSONArray(this.bidders);
        offersArr.put(getLessonInfo().getBuyOutBidJSONObj(tutorId, compLvl));
        additionalInfo.remove("bidOffers");
        additionalInfo.put("bidOffers",offersArr);
        // call api to update details
        APIFacade.updateBidById(getId(), getAdditionalInfo());

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

    /**
     * Tutor subscribes to an active open bid.
     * @param tutorId tutor's id who wants to subscribe to this bid.
     */
    public void tutorSubscribeBid(String tutorId) {
        // check expiry of this active bid
        if (!isExpired()) {
            APIFacade.subscribeBid(tutorId, getId());

            // notify observers
            setChanged();
            notifyObservers();
        } else {
            System.out.println("Can't subscribe to an expired bid.");
        }
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
