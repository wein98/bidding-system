package com.matchingSystem.View;

import com.matchingSystem.Model.DashboardModel;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class DashboardView extends javax.swing.JFrame implements Observer {
    // Profile section UI components
    private JLabel userTypeLabel;
    private JLabel usernameLabel;
    private JLabel familyNameLabel;
    private JLabel givenNameLabel;
    private JPanel profileSection;
    private JPanel window2;
    private JPanel contractsPanel;
    private JButton ContractRefreshBtn;
    private JButton BidActionBtn;
    private JScrollPane ContractListScroll;

    private DashboardModel model;

    public DashboardView(DashboardModel model) {
        this.model = model;
        model.addObserver(this);    // subscribe to observable
        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
//        UserCookie user = UserCookie.getInstance();

//        System.out.println(user.getUser().toString());
//        // display profile section
//        if (user.getUser() instanceof Student) {
//            userTypeLabel.setText("Student");
//        } else if (user.getUser() instanceof Tutor) {
//            userTypeLabel.setText("Tutor");
//        } else {
//            userTypeLabel.setText("unknown");
//        }
//
//        usernameLabel.setText(user.getUser().getUserName());
//        familyNameLabel.setText(user.getUser().getFamilyName());
//        givenNameLabel.setText(user.getUser().getGivenName());

        setContentPane(window2);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof DashboardModel) {
            System.out.println("HAHAFHDFHDFH");
            userTypeLabel.setText(model.getUserType());
            usernameLabel.setText(model.getUsername());
            familyNameLabel.setText(model.getFamilyName());
            givenNameLabel.setText(model.getGivenName());
        }

    }
}
