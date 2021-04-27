package com.matchingSystem.Controller;

import com.matchingSystem.Model.BidOffer;
import com.matchingSystem.View.CloseBidMsgView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseBidMsgController implements ActionListener {
    private CloseBidMsgView view;
    private BidOffer bidOffer;

    public CloseBidMsgController(CloseBidMsgView view, BidOffer bidOffer) {
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
