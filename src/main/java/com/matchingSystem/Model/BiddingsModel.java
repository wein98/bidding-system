package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.BiddingSystem.Bid;
import com.matchingSystem.BiddingSystem.Competency;
import com.matchingSystem.BiddingSystem.Subject;
import com.matchingSystem.Constant;
import com.matchingSystem.LoginSystem.Student;
import com.matchingSystem.LoginSystem.Tutor;
import com.matchingSystem.LoginSystem.UserCookie;
import java.util.ArrayList;
import java.util.Observable;

public class BiddingsModel extends Observable {

    private ArrayList<BidOfferModel> bidOffersList = new ArrayList<>();
    private ArrayList<Bid> bids = new ArrayList<>();
    private Bid bid;

    public BiddingsModel() {}

    public BiddingsModel(String bidId) {
        // get Bid object with bidId
        this.bid = APIFacade.getBidById(bidId);
    }

    public void setBiddings(int biddingsViewType) {
        // tutor view all active bids
        if (biddingsViewType == Constant.TUTOR_OFFER_BIDS_VIEW) {
            setBids();
        } else if (biddingsViewType == Constant.TUTOR_SUBS_OPENBIDDINGS_VIEW) { // tutor view subscribed open bids
            setSubBids();
        } else {    // tutor views an open bid of other bidders
            setBidOffersList();
        }
    }

    private void setSubBids() {
        bids = ((Tutor) UserCookie.getUser()).getSubscribedBids();
    }

    /**
     * Set the biddings view for TutorBidOffersView.
     */
    private void setBids() {
        bids = new ArrayList<>();
        ArrayList<Bid> bidsArr = APIFacade.getAllBids();

        ArrayList<Competency> tutorCompetencies = UserCookie.getUser().getCompetencies();

        // filter by competencies of tutor
        for (Bid b: bidsArr) {
            // get subject in bid
            if (!b.isExpired()) {
                Subject bidSubject = b.getSubject();
                int bidCompetency = b.getCompetencyLevel();
                for (Competency competency: tutorCompetencies){
                    if (competency.getSubject().getId().equals(bidSubject.getId())){
                        if (competency.getLevel() >= bidCompetency + 2){
                            bids.add(b);
                        }
                    }
                }
            }
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Set the bid offers view for OpenCloseBidView.
     */
    private void setBidOffersList() {
        if (bid.isExpired()) {
            bidOffersList.clear();
        } else {
            bidOffersList = bid.getBidOffers();
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Function is called when Student press Select Offer button.
     * @param b this bid offer selected by the Student
     */
    public void selectOffer(BidOfferModel b) {
        ((Student) UserCookie.getUser()).getInitiatedBid().selectBidder(b);
        // when an offer is selected, close the current panel
        setChanged();
        notifyObservers();
    }

    // Getters

    public Bid getBid() {
        return bid;
    }

    public ArrayList<BidOfferModel> getBidOffersList() {
        return bidOffersList;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public String getDuration() {
        return bid.getExpireDuration();
    }
}
