package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Constant;

import java.util.ArrayList;
import java.util.Observable;

public class BiddingsModel extends Observable {

    private ArrayList<BidOffer> bidOffersList = new ArrayList<>();
    private ArrayList<Bid> bids = new ArrayList<>();
    private String duration;
    private int biddingsViewType;

    public void setBiddings(int biddingsViewType) {
        // TODO:
        //      - call bidAPI by id from user.getInitiateBid().getById()
        //      - get the bid and addtionalInfos
        //      - populate additionalInfos to List<BidOffer>

        // biddingsViewType = Constant.OPEN_BIDDING_VIEW
        // biddingsViewType = Constant.CLOSE_BIDDING_VIEW
        // biddingsViewType = Constant.OFFER_BIDS_VIEW
        if (biddingsViewType == Constant.OFFER_BIDS_VIEW) {
            setBids();
        }

    }

    private void setBids() {
        bids = (ArrayList<Bid>) APIFacade.getBidAPI().getAll();

        setChanged();
        notifyObservers();
    }

    private void setBidOffersList() {

    }

    public ArrayList<BidOffer> getBidOffersList() {
        return bidOffersList;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public String getDuration() {
        return duration;
    }
}
