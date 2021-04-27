package com.matchingSystem.Controller;

import com.matchingSystem.Constant;
import com.matchingSystem.Model.BiddingsModel;
import com.matchingSystem.View.OpenBidView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class OpenBidController implements Observer, ActionListener {
    private OpenBidView view;
    private BiddingsModel model;

    public OpenBidController(OpenBidView view, BiddingsModel model) {
        this.view = view;
        this.model = model;
        model.addObserver(this);    // subscribe to observable
        initController();
    }

    private void initController() {
        model.addObserver(this);    // subscribe to observable
        view.getBiddingRefreshBtn().addActionListener(this);
        updateBidOffers();
    }

    private void updateBidOffers() {
        model.setBiddings(Constant.OPEN_BIDDING_VIEW);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(view.getBiddingRefreshBtn())) {
            System.out.println("Refresh open bid offers button pressed");
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
