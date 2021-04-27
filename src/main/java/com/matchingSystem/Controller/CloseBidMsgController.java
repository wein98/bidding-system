package com.matchingSystem.Controller;

import com.matchingSystem.Model.BidOfferModel;
import com.matchingSystem.View.CloseBidMsgView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseBidMsgController implements ActionListener {
    private CloseBidMsgView view;
    private BidOfferModel bidOffer;

    public CloseBidMsgController(CloseBidMsgView view, BidOfferModel bidOffer) {
        this.view = view;
        this.bidOffer = bidOffer;
        initController();
    }

    private void initController() {
        view.getSendBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: sendBtn onclick call MessageAPI

        String message = view.getMessageTextField().getText();
        view.dispose();
    }
}
