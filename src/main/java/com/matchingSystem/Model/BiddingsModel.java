package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Constant;
import com.matchingSystem.UserCookie;
import java.util.ArrayList;
import java.util.Observable;

public class BiddingsModel extends Observable {

    private ArrayList<BidOfferModel> bidOffersList = new ArrayList<>();
    private ArrayList<Bid> bids = new ArrayList<>();
    private String bidId;
    private Bid bid;

    public BiddingsModel() {}

    public BiddingsModel(String bidId) {
        this.bidId = bidId;
        // get Bid object with bidId
        this.bid = APIFacade.getBidById(this.bidId);
    }

    public void setBiddings(int biddingsViewType) {
        // biddingsViewType = Constant.OFFER_BIDS_VIEW
        if (biddingsViewType == Constant.OFFER_BIDS_VIEW) {
            setBids();
        } else {
            setBidOffersList();
        }

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
            Subject bidSubject = b.getSubject();
            int bidCompetency = b.getCompetencyLevel();
            // TODO: needs to filter out the competency subjects based on competency level
            for (Competency competency: tutorCompetencies){
                if (competency.getSubject().getId().equals(bidSubject.getId())){
                    if (competency.getLevel() >= bidCompetency + 2){
                        bids.add(b);
                    }
                }
            }
        }

//        bids = bidsArr;

        setChanged();
        notifyObservers();
    }

    /**
     * Set the bid offers view for OpenCloseBidView.
     */
    private void setBidOffersList() {
        bidOffersList = bid.getBidOffers();

        setChanged();
        notifyObservers();
    }

    /**
     * Function is called when Student press Select Offer button.
     * @param b this bid offer selected by the Student
     */
    public void selectOffer(BidOfferModel b) {
        ((Student) UserCookie.getUser()).getInitiatedBid().selectBidder(b);
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
