package com.matchingSystem.Controller;

import com.matchingSystem.Model.BidOfferModel;
import com.matchingSystem.Model.BiddingsModel;
import com.matchingSystem.View.BidOfferView;
import com.matchingSystem.View.OpenCloseBidView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class OpenCloseBidController implements Observer, ActionListener {
    private OpenCloseBidView view;
    private BiddingsModel model;

    public OpenCloseBidController(OpenCloseBidView view, BiddingsModel model) {
        this.view = view;
        this.model = model;
        model.addObserver(this);    // subscribe to observable
        initController();
    }

    private void initController() {
        model.addObserver(this);    // subscribe to observable
        view.getBiddingRefreshBtn().addActionListener(this);
        if(view.getOfferButton().isVisible()) {
            view.getOfferButton().addActionListener(this);
        }
        updateBidOffers(view.getBidViewType());
    }

    /**
     * Get the latest bid offers
     * @param bidViewType type of bid
     */
    private void updateBidOffers(int bidViewType) {
        model.setBiddings(bidViewType);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(view.getBiddingRefreshBtn())) {
            updateBidOffers(view.getBidViewType());
        } else if (e.getSource().equals(view.getOfferButton())) {
            BidOfferModel offerModel = new BidOfferModel(model.getBid());
            BidOfferView offerView = new BidOfferView(offerModel, "open");
            new BidOfferController(offerView, offerModel);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof BiddingsModel) {
            view.setPanel(model.getBidOffersList());
            view.setBidDetailsPanel(model.getBid().getSubject().getName(),
                    String.valueOf(model.getBid().getCompetencyLevel()));
            view.getBidDurationText().setText("Bid duration left: " + model.getDuration());
        }
    }
}
