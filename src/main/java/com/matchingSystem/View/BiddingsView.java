package com.matchingSystem.View;

import com.matchingSystem.Model.BiddingsModel;

import javax.swing.*;

public abstract class BiddingsView extends JFrame{
    protected JButton biddingRefresh;
    protected JLabel BidDurationText;
    protected JPanel panel;
    protected JScrollPane scrollPane;
    protected JPanel mainPanel;
    protected JLabel titleLabel;

    protected BiddingsModel model;

    public BiddingsView() {

    }

    protected abstract void initComponents();

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public JButton getBiddingRefresh() {
        return biddingRefresh;
    }

    public JLabel getBidDurationText() {
        return BidDurationText;
    }
}
