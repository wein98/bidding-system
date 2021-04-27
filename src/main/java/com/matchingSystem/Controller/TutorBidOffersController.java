package com.matchingSystem.Controller;

import com.matchingSystem.Constant;
import com.matchingSystem.Model.BiddingsModel;
import com.matchingSystem.View.TutorBidOffersView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class TutorBidOffersController implements Observer, ActionListener {
    private TutorBidOffersView view;
    private BiddingsModel model;

    public TutorBidOffersController(TutorBidOffersView view, BiddingsModel model) {
        this.view = view;
        this.model = model;
        model.addObserver(this);    // subscribe to observable
        initController();
    }

    private void initController() {
        model.addObserver(this);    // subscribe to observable
        view.getBiddingRefresh().addActionListener(this);
        updateBids();
    }

    private void updateBids() {
        model.setBiddings(Constant.OFFER_BIDS_VIEW);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(view.getBiddingRefresh())) {
            System.out.println("Refresh button pressed");
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
