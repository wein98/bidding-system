package com.matchingSystem.BiddingSystem;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Constant;
import com.matchingSystem.Controller.BidOfferController;
import com.matchingSystem.Controller.ContractCreationController;
import com.matchingSystem.Controller.OpenCloseBidController;
import com.matchingSystem.LoginSystem.Tutor;
import com.matchingSystem.LoginSystem.UserCookie;
import com.matchingSystem.Model.BidOfferModel;
import com.matchingSystem.Model.BiddingsModel;
import com.matchingSystem.Model.ContractCreationModel;
import com.matchingSystem.View.BidOfferView;
import com.matchingSystem.View.ContractCreationView;
import com.matchingSystem.View.OpenCloseBidView;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActiveBidsIterator implements Iterator {
    private int currentPosition = 0;
    private List<Bid> bids = new ArrayList<>();
    private int bidViewType;

    public ActiveBidsIterator( int bidViewType) {
        this.bidViewType = bidViewType;
    }

    private void lazyload() {
        if (bids.size() == 0) {
            if (bidViewType == Constant.TUTOR_OFFER_BIDS_VIEW) {
                ArrayList<Bid> bidsArr = APIFacade.getAllBids();

                ArrayList<Competency> tutorCompetencies = UserCookie.getUser().getCompetencies();

                // filter by competencies of tutor
                for (Bid b: bidsArr) {
                    // get subject in bid
                    if (!b.isExpired()) {
                        Subject bidSubject = b.getSubject();
                        int bidCompetency = b.getCompetencyLevel();
                        for (Competency competency: tutorCompetencies){
                            if (competency.getSubject().getId().equals(bidSubject.getId())){
                                if (competency.getLevel() >= bidCompetency + 2){
                                    bids.add(b);
                                }
                            }
                        }
                    }
                }
            } else if (bidViewType == Constant.TUTOR_SUBS_OPENBIDDINGS_VIEW) {
                // populate subscribed bids from tutor api
                ((Tutor) UserCookie.getUser()).setSubscribedBids();
                bids = ((Tutor) UserCookie.getUser()).getSubscribedBids();
            }
        }
    }

    @Override
    public boolean hasNext() {
        lazyload();
        return currentPosition < bids.size();
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            return null;
        }

        Bid b = bids.get(currentPosition);

        JPanel panel1 = new JPanel();
        panel1.getInsets().set(20, 20, 20, 20);
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JTable table = getActiveBidsTable(b);

        // resize table columns
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        c.gridheight = 3;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1.0;
        panel1.add(table, c);

        currentPosition++;
        if (b instanceof OpenBid) {
            return getOpenBidLayout(b, c, panel1);
        } else if (b instanceof CloseBid) {
            return getCloseBidLayout(b, c, panel1);
        }

        return null;
    }

    public boolean isEmpty() {
        lazyload();
        return bids.isEmpty();
    }

    private JPanel getOpenBidLayout(Bid b, GridBagConstraints c, JPanel panel) {
        // Buy out button
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(getBuyOutBtn(b), c);

        // View all bidders button
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(getViewAllBiddersBtn(b), c);

        // Subscribe button
        if (bidViewType == Constant.TUTOR_OFFER_BIDS_VIEW) {
            c.gridheight = 1;
            c.gridx = 1;
            c.gridy = 2;
            panel.add(getSubscribeBidBtn(b), c);
        }

        return panel;
    }

    private JPanel getCloseBidLayout(Bid b, GridBagConstraints c, JPanel panel) {
        // Message button
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        c.weighty = 0.5;
        panel.add(getMessageBidBtn(b), c);

        return panel;
    }

    private JTable getActiveBidsTable(Bid b) {
        String[][] rec = null;
        if (b instanceof OpenBid) {
            rec = new String[][]{{"Bid Type", b.getType()},
                    {"Student name", b.getInitiator().getName()},
                    {"Subject", b.getSubject().getName()},
                    {"No. of sessions", b.getLessonInfo().getNumOfLesson()},
                    {"Day & Time", b.getLessonInfo().getDayTime()},
                    {"Rate (per hour)", b.getLessonInfo().getRate()}};
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
                        {"No. of sessions", b.getLessonInfo().getNumOfLesson()},
                        {"Day & Time", b.getLessonInfo().getDayTime()},
                        {"Rate (per hour)", b.getLessonInfo().getRate()},
                        {"Tutor message", ""},
                        {"Student's reply:", ""}};
            } else {
                rec = new String[][]{{"Bid Type", b.getType()},
                        {"Student name", b.getInitiator().getName()},
                        {"Subject", b.getSubject().getName()},
                        {"No. of sessions", bidoffer.getLessonInfo().getNumOfLesson()},
                        {"Day & Time", bidoffer.getLessonInfo().getDayTime()},
                        {"Rate (per hour)", bidoffer.getLessonInfo().getRate()},
                        {"Tutor message", bidoffer.getMsg().getContent()},
                        {"Student's reply:", bidoffer.getMsg().getStudentReply()}};
            }

        }
        String[] col = {"", ""};
        return new JTable(rec, col);

    }

    /**
     * Function that assigns an action listener to Buy Out button
     * @param b Bid that this button belongs to
     */
    private JButton getBuyOutBtn(Bid b) {
        JButton btn = new JButton("Buy out");

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed");

                ((OpenBid) b).buyOut(UserCookie.getUser().getId());

                // get Contract's info to json
                JSONObject details = new JSONObject();
                details.put("studentId",b.getInitiator());
                details.put("tutorId",UserCookie.getUser().getId());
                details.put("subjectId",b.getSubject().getId());
                details.put("lessInfo",b.getLessonInfo().getContractLessonInfo());

                JSONObject addInfo = new JSONObject();
                addInfo.put("rate",b.getLessonInfo().getRate());
                details.put("addInfo",addInfo);

                JSONObject payInfo = new JSONObject();
                details.put("payInfo",payInfo);

                ContractCreationModel contractModel = new ContractCreationModel();
                ContractCreationView contractView = new ContractCreationView(contractModel);
                new ContractCreationController(contractView,contractModel,details);

                System.out.println("BUY OUT: " + b.getInitiator().toString());
            }
        });

        return btn;
    }

    /**
     * Function that assigns an action listener to view open bidders button.
     * @param b
     */
    private JButton getViewAllBiddersBtn(Bid b) {
        JButton btn = new JButton("View all bidders");

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("OFFER: " + b.getInitiator().toString());
                // opens open bidding offers
                BiddingsModel biddingsModel = new BiddingsModel(b.getId());
                OpenCloseBidView biddingsView = new OpenCloseBidView(biddingsModel, Constant.TUTOR_OPEN_BIDDING_VIEW);
                new OpenCloseBidController(biddingsView, biddingsModel);
            }
        });

        return btn;
    }

    private JButton getSubscribeBidBtn(Bid b) {
        JButton btn = new JButton("Subscribe");

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Subsribed to: " + b.getInitiator().toString());
                // TODO: observe this bid
                b.addObserver((Tutor)UserCookie.getUser());
                ((OpenBid) b).tutorSubscribeBid(UserCookie.getUser().getId());

                btn.setVisible(false);
            }
        });

        return btn;
    }

    private JButton getMessageBidBtn(Bid b) {
        JButton btn = new JButton("Message bid");

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // opens Reply Bid window
                BidOfferModel bidOfferModel = new BidOfferModel(b);
                BidOfferView bidOfferView = new BidOfferView(bidOfferModel, "close");
                new BidOfferController(bidOfferView, bidOfferModel);

            }
        });

        return btn;
    }
}
