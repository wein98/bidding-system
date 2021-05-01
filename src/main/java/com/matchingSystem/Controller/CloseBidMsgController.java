package com.matchingSystem.Controller;

import com.matchingSystem.Model.BidOfferModel;
import com.matchingSystem.View.CloseBidMsgView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseBidMsgController implements ActionListener {
    private CloseBidMsgView view;
    private BidOfferModel model;

    public CloseBidMsgController(CloseBidMsgView view, BidOfferModel model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {
        view.getSendBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (view.getMessageTextField().getText().length() != 0) {
            model.studentReplyMsg(view.getMessageTextField().getText());
        }
        view.dispose();
    }
}
