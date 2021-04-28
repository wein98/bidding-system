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

    public BiddingsModel() {}

    public BiddingsModel(String bidId) {
        this.bidId = bidId;
    }

    public void setBiddings(int biddingsViewType) {
        // TODO:
        //      - call bidAPI by id from user.getInitiateBid().getById()
        //      - get the bid and addtionalInfos
        //      - populate additionalInfos to List<BidOffer>

        // biddingsViewType = Constant.OPEN_BIDDING_VIEW
        if (biddingsViewType == Constant.OPEN_BIDDING_VIEW) {
            setBidOffersList(bidId);
        }
        // biddingsViewType = Constant.CLOSE_BIDDING_VIEW

        // biddingsViewType = Constant.OFFER_BIDS_VIEW
        if (biddingsViewType == Constant.OFFER_BIDS_VIEW) {
            setBids();
        }

    }

    /**
     * Set the biddings view for TutorBidOffersView.
     */
    private void setBids() {
        ArrayList<Bid> bidsArr = (ArrayList<Bid>) APIFacade.getBidAPI().getAll();

        ArrayList<Competency> tutorCompetencies = UserCookie.getUser().getCompetencies();

        // filter by competencies of tutor
        for (Bid b: bidsArr) {
            // TODO: needs to filter out the competency subjects based on competency level

        }

        bids = bidsArr;

        setChanged();
        notifyObservers();
    }

    /**
     * Set the bid offers view for OpenCloseBidView.
     */
    private void setBidOffersList(String bidId) {
        // TODO: parse bid.addtionalInfo.bidOffers here
        Bid b = (Bid) APIFacade.getBidAPI().getById(bidId, Constant.NONE);
        bidOffersList = b.getBidOffers();

        setChanged();
        notifyObservers();
    }

    public void selectOffer(BidOfferModel b) {

    }

    public ArrayList<BidOfferModel> getBidOffersList() {
        return bidOffersList;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public String getDuration() {
        Student studentObj = (Student) UserCookie.getUser();
        return studentObj.getInitiatedBid().getExpireDuration();
    }
}