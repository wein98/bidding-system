package com.matchingSystem.LoginSystem;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.BiddingSystem.Bid;
import com.matchingSystem.BiddingSystem.Competency;
import com.matchingSystem.BiddingSystem.OpenBid;
import com.matchingSystem.BiddingSystem.Subject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Tutor extends User implements Observer {
    protected ArrayList<Bid> subscribedBids = new ArrayList<>();

    public Tutor(String id, String givenName, String familyName, String userName) {
        super(id, givenName, familyName, userName);
    }

    public Tutor() {
        super();
    }

    /**
     * Function to send an offer for Open Bidding
     * @param bidId
     * @param additionalObject
     */
    public void makeOfferToOpenBidding(String bidId, JSONObject additionalObject) {
        OpenBid bid = (OpenBid) APIFacade.getBidById(bidId);
        JSONObject additionalInfo = bid.getAdditionalInfo();
        JSONArray bidOffersArr = additionalInfo.getJSONArray("bidOffers");
        int toRemoveIndex = -1;

        // look for the bidoffers offered by the tutorId previously
        for (int i=0; i<bidOffersArr.length(); i++) {
            JSONObject o = bidOffersArr.getJSONObject(i);
            if (this.id.equals(o.getString("tutorId"))) {
                toRemoveIndex = i;
                break;
            }
        }

        // remove old bidoffer and add new updated one
        if (toRemoveIndex >= 0) {    // if there's a previous bid offers offered
            bidOffersArr.remove(toRemoveIndex);
        }
        bidOffersArr.put(additionalObject);

        // remove the whole list and insert again
        additionalInfo.remove("bidOffers");
        additionalInfo.put("bidOffers", bidOffersArr);

        APIFacade.updateBidById(bidId, additionalInfo);
    }

    public void setSubscribedBids() {
        this.subscribedBids = APIFacade.getSubscribedBids(getId());
    }

    public ArrayList<Bid> getSubscribedBids() {
        return subscribedBids;
    }

    public int getCompetencyLvlFromSubject(Subject s) {
        for (Competency c: competencies) {
            if (c.getSubject().getId().equals(s.getId())) {
                return c.getLevel();
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "id='" + id + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", userName='" + userName + '\'' +
                ", competencies=" + competencies +
                ", qualifications=" + qualifications +
                ", contracts=" + contracts +
                ", objectMapper=" + objectMapper +
                '}';
    }

    @Override
    public void update(Observable o, Object arg) {
        // set tutor's subscribed bids when tutor pressed subscribe to open bid button
        if (o instanceof OpenBid) {
            this.setSubscribedBids();
        }
    }
}
