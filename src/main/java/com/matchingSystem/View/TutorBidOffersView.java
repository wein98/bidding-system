package com.matchingSystem.View;

import com.matchingSystem.Constant;
import com.matchingSystem.BiddingSystem.ActiveBidsIterator;
import com.matchingSystem.Model.*;

import javax.swing.*;
import java.awt.*;


public class TutorBidOffersView extends BiddingsView {
    private final int bidViewType;

    public TutorBidOffersView(BiddingsModel model, int bidViewType) {
        this.model = model;
        this.bidViewType = bidViewType;

        initComponents();
        this.setVisible(true);
    }

    @Override
    protected void initComponents() {
        if (bidViewType == Constant.TUTOR_OFFER_BIDS_VIEW) {
            titleLabel.setText("Offer Bids");
        } else if (bidViewType == Constant.TUTOR_SUBS_OPENBIDDINGS_VIEW) {
            titleLabel.setText("Open Bids Monitor");
        }
        BidDurationText.setVisible(false);
        OpenBidDetailsPanel.setVisible(false);
        setContentPane(mainPanel);
        pack();
    }

    public void setPanel(ActiveBidsIterator iterator) {
        panel.removeAll();
        scrollPane.getViewport().setView(panel);

        if (iterator.isEmpty()) {
            if (bidViewType == Constant.TUTOR_OFFER_BIDS_VIEW) {
                JLabel textLabel = new JLabel("There's no active bids at the moment. Press refresh button to update.");
                panel.add(textLabel);
            } else if (bidViewType == Constant.TUTOR_SUBS_OPENBIDDINGS_VIEW) {
                JLabel textLabel = new JLabel("You haven't subscribed to any active bids or your subscribed bids were expired.");
                panel.add(textLabel);
            }
        } else {
            while(iterator.hasNext()) {
                JPanel panel1 = (JPanel) iterator.next();

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(10, 0, 10, 0);
                panel.add(panel1, gbc, 0);
            }
        }
    }

    public int getBidViewType() {
        return bidViewType;
    }
}
