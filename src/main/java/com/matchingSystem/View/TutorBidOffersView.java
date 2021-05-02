package com.matchingSystem.View;

import com.matchingSystem.BiddingSystem.Bid;
import com.matchingSystem.BiddingSystem.CloseBid;
import com.matchingSystem.BiddingSystem.OpenBid;
import com.matchingSystem.Constant;
import com.matchingSystem.Controller.BidOfferController;
import com.matchingSystem.Controller.OpenCloseBidController;
import com.matchingSystem.Model.*;
import com.matchingSystem.LoginSystem.UserCookie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TutorBidOffersView extends BiddingsView {

    public TutorBidOffersView(BiddingsModel model) {
        this.model = model;
        initComponents();
        this.setVisible(true);
    }

    @Override
    protected void initComponents() {
        titleLabel.setText("Offer Bids");
        BidDurationText.setVisible(false);
        OpenBidDetailsPanel.setVisible(false);
        setContentPane(mainPanel);
        pack();
    }

    public void setPanel(ArrayList<Bid> bids) {
        panel.removeAll();
        scrollPane.getViewport().setView(panel);

        for (Bid b : bids) {
            if(b.getDateClosedDown() == null) {
                JPanel panel1 = new JPanel();
                panel1.getInsets().set(20, 20, 20, 20);
                panel1.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();


                JTable table = getTable(b);

                // resize table columns
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.getColumnModel().getColumn(0).setPreferredWidth(150);
                table.getColumnModel().getColumn(1).setPreferredWidth(150);
                c.gridheight = 2;
                c.gridx = 0;
                c.gridy = 0;
                c.weighty = 1.0;
                panel1.add(table, c);

                // Set different buttons for different bidType
                if (b instanceof OpenBid && b.getDateClosedDown() == null) {
                    JButton btn1 = new JButton("Buy out");
                    JButton btn2 = new JButton("View all bidders");
                    c.gridheight = 1;
                    c.gridx = 1;
                    c.gridy = 0;
                    c.weighty = 0.5;
                    panel1.add(btn1, c);

                    c.gridheight = 1;
                    c.gridx = 1;
                    c.gridy = 1;
                    c.weighty = 0.5;
                    panel1.add(btn2, c);

                    // buttons action listener
                    btn1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // TODO: buy out this bid
                            ((OpenBid) b).buyOut(UserCookie.getUser().getId());
                            System.out.println("BUY OUT: " + b.getInitiator().toString());
                        }
                    });

                    btn2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("OFFER: " + b.getInitiator().toString());
                            // opens open bidding offers
                            BiddingsModel biddingsModel = new BiddingsModel(b.getId());
                            OpenCloseBidView biddingsView = new OpenCloseBidView(biddingsModel, Constant.TUTOR_OPEN_BIDDING_VIEW);
                            new OpenCloseBidController(biddingsView, biddingsModel);
                        }
                    });

                } else if (b instanceof CloseBid && b.getDateClosedDown() == null) {
                    JButton btn1 = new JButton("Message bid");
                    c.gridheight = 1;
                    c.gridx = 1;
                    c.gridy = 0;
                    c.weighty = 0.5;
                    panel1.add(btn1, c);

                    btn1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // TODO: opens Reply Bid window
                            BidOfferModel bidOfferModel = new BidOfferModel(b);
                            BidOfferView bidOfferView = new BidOfferView(bidOfferModel, "close");
                            BidOfferController bidOfferController = new BidOfferController(bidOfferView, bidOfferModel);

                        }
                    });
                }

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(10, 0, 10, 0);
                panel.add(panel1, gbc, 0);
            }
        }
    }

    private JTable getTable(Bid b) {
        String[][] rec = null;
        if (b instanceof OpenBid) {
            rec = new String[][]{{"Bid Type", b.getType()},
                    {"Student name", b.getInitiator().getName()},
                    {"Subject", b.getSubject().getName()},
                    {"No. of sessions", b.getNoLessons()},
                    {"Day & Time", b.getDayTime()},
                    {"Rate (per hour)", b.getRate()},};
        } else {
            BidOfferModel bidoffer = null;
            for (BidOfferModel offer : b.getBidOffers()) {
                if (offer.getOfferTutorId().equals(UserCookie.getUser().getId())) {
                    bidoffer = offer;
                }
            }
            if (bidoffer == null) {
                rec = new String[][]{{"Bid Type", b.getType()},
                        {"Student name", b.getInitiator().getName()},
                        {"Subject", b.getSubject().getName()},
                        {"No. of sessions", b.getNoLessons()},
                        {"Day & Time", b.getDayTime()},
                        {"Rate (per hour)", b.getRate()},
                        {"Tutor message", ""},
                        {"Student's reply:", ""}};
            } else {
                rec = new String[][]{{"Bid Type", b.getType()},
                        {"Student name", b.getInitiator().getName()},
                        {"Subject", b.getSubject().getName()},
                        {"No. of sessions", bidoffer.getNumOfLesson()},
                        {"Day & Time", bidoffer.getDayTime()},
                        {"Rate (per hour)", bidoffer.getOfferRate()},
                        {"Tutor message", bidoffer.getMsg().getContent()},
                        {"Student's reply:", bidoffer.getMsg().getStudentReply()}};
            }

        }
        String[] col = {"", ""};
        return new JTable(rec, col);
    }
}
