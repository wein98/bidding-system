package com.matchingSystem.Controller;

import com.matchingSystem.Model.BiddingsModel;
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
        updateBidOffers(view.getBidViewType());
    }

    private void updateBidOffers(int bidViewType) {
        model.setBiddings(bidViewType);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(view.getBiddingRefreshBtn())) {
            updateBidOffers(view.getBidViewType());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof BiddingsModel) {
            System.out.println("POPOSFAF");
            view.setPanel(model.getBidOffersList());
            view.getBidDurationText().setText("Bid duration left: " + model.getDuration());
        }
    }
}
