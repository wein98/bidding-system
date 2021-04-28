package com.matchingSystem.View;

import com.matchingSystem.Constant;
import com.matchingSystem.Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OpenCloseBidView extends BiddingsView {
    private final int bidViewType;

    public OpenCloseBidView(BiddingsModel model, int bidViewType) {
        this.bidViewType = bidViewType;
        this.model = model;
        initComponents();
        this.setVisible(true);
    }

    @Override
    protected void initComponents() {
        if (bidViewType == Constant.OPEN_BIDDING_VIEW) {
            titleLabel.setText("Open Biddings");
        } else if (bidViewType == Constant.CLOSE_BIDDING_VIEW){
            titleLabel.setText("Close Biddings");
        }

        setContentPane(mainPanel);
        pack();
    }

    public void setPanel(ArrayList<BidOfferModel> bidOffers) {
        panel.removeAll();
        scrollPane.getViewport().setView(panel);

        if (bidOffers.size() != 0) {
            for (BidOfferModel b: bidOffers) {
//        for (int i=0; i<4; i++) {
                JPanel panel1 = new JPanel();
                panel1.getInsets().set(20, 20, 20, 20);
                JTable table = getTable(b);
                // resize table columns
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.getColumnModel().getColumn(0).setPreferredWidth(150);
                table.getColumnModel().getColumn(1).setPreferredWidth(150);

                panel1.add(table);

                JButton btn1 = new JButton("Select offer");
                panel1.add(btn1);

                // buttons action listener
                btn1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO: select this bid offer
                        model.selectOffer(b);
                    }
                });

                if (bidViewType == Constant.CLOSE_BIDDING_VIEW) {
                    JButton btn2 = new JButton("Reply message");
                    panel1.add(btn2);

                    // buttons action listener
                    btn2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // TODO: reply message
//                            model.selectOffer(b);
                        }
                    });
                }

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(10, 0, 10, 0);
                panel.add(panel1, gbc, 0);
            }
        } else {
            JLabel textLabel = new JLabel("No bid offers yet. Press refresh button to update.");
            panel.add(textLabel);
        }

    }

    private JTable getTable(BidOfferModel b) {
        String [][] rec = null;
        if (bidViewType == Constant.OPEN_BIDDING_VIEW) {
            rec = new String[][] {
                {"Tutor name", b.getTutorName()},
                {"Tutor competency level", String.valueOf(b.getTutorCompLvl())},
                {"Subject", b.getSubjectName()},
                {"No. of sessions", String.valueOf(b.getNumberOfLesson())},
                {"Day & Time", b.getDayTime()},
                {"Duration (hours per session)", b.getDuration()},
                {"Rate (per hour)", b.getOfferRate()}
            };
        }

        String[] col = {"", ""};
        return new JTable(rec, col);
    }

    public int getBidViewType() {
        return bidViewType;
    }
}