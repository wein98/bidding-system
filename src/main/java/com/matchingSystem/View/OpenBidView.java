package com.matchingSystem.View;

import com.matchingSystem.Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class OpenBidView extends BiddingsView implements Observer {

    public OpenBidView(BiddingsModel model) {
        this.model = model;
        model.addObserver(this);
        initComponents();
        this.setVisible(true);
    }

    @Override
    protected void initComponents() {
        titleLabel.setText("Open Biddings");

        scrollPane.getViewport().setView(panel);
        setContentPane(mainPanel);
        pack();
    }

    public void setPanel(ArrayList<BidOffer> bidOffers) {
        if (bidOffers.size() != 0) {
            for (BidOffer b: bidOffers) {
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

    private JTable getTable(BidOffer b) {
        String[][] rec = {
//                {"Tutor name", },
//                {"Tutor qualification", b.getInitiator().getGivenName()},
//                {"Competency level", b.getSubject().getName()},
//                {"Subject", b.getSubject().getName()},
//                {"No. of sessions", b.getNoLessons()},
//                {"Day & Time", b.getDayTime()},
//                {"Duration", b.getDayTime()},
//                {"Rate (per hour)", b.getRate()},
        };
        String[] col = {"", ""};
        return new JTable(rec, col);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof BiddingsModel) {
            setPanel(model.getBidOffersList());
            BidDurationText.setText("Bid duration left: " + model.getDuration());
        }
    }
}
