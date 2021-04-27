package com.matchingSystem.View;

import com.matchingSystem.Model.Bid;
import com.matchingSystem.Model.BiddingsModel;
import com.matchingSystem.Model.CloseBid;
import com.matchingSystem.Model.OpenBid;

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

        scrollPane.getViewport().setView(panel);
        setContentPane(mainPanel);
        pack();
    }

    public void setPanel(ArrayList<Bid> bids) {
        panel.removeAll();
        for (Bid b: bids) {

            JPanel panel1 = new JPanel();
            panel1.getInsets().set(20, 20, 20, 20);
            JTable table = getTable(b);
            // resize table columns
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getColumnModel().getColumn(0).setPreferredWidth(150);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);

            panel1.add(table);

            // Set different buttons for different bidType
            if (b instanceof OpenBid) {
                JButton btn1 = new JButton("Buy out");
                JButton btn2 = new JButton("Offer");
                panel1.add(btn1);
                panel1.add(btn2);

                // buttons action listener
                btn1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO: buy out this b
                        System.out.println("BUY OUT: " + b.getInitiator().toString());
                    }
                });

                btn2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO: opens offer bid window
                        System.out.println("OFFER: " + b.getInitiator().toString());

                    }
                });

            } else if (b instanceof CloseBid) {
                JButton btn1 = new JButton("Message bid");
                panel1.add(btn1);

                btn1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO: opens Reply Bid window
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

    private JTable getTable(Bid b) {
        String[][] rec = {
            {"Bid Type", b.getType()},
            {"Student name", b.getInitiator().getGivenName()},
            {"Subject", b.getSubject().getName()},
            {"No. of sessions", b.getNoLessons()},
            {"Day & Time", b.getDayTime()},
            {"Rate (per hour)", b.getRate()},
        };
        String[] col = {"", ""};
        return new JTable(rec, col);
    }
}
