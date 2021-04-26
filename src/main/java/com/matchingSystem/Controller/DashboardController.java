package com.matchingSystem.Controller;

import com.matchingSystem.Constant;
import com.matchingSystem.Model.BiddingCreationModel;
import com.matchingSystem.Model.DashboardModel;
import com.matchingSystem.View.BiddingCreationView;
import com.matchingSystem.View.DashboardView;

import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardController implements Observer, ActionListener {
    private DashboardView view;
    private DashboardModel model;

    public DashboardController(DashboardView view, DashboardModel model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {
        model.addObserver(this);    // subscribe to observable
        updateProfile();
        view.getBidActionBtn().addActionListener(this);
    }

    public void updateProfile() {
        model.setProfile();
    }

    public void updateContracts() {

    }

    public DashboardView getView() { return this.view; }
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof DashboardModel) {
            // Update bid action button to appropriate text according to initiated bid
            if (model.getUserType().equals("Student")) {
                if (model.getBidType() == Constant.OPENBID) {
                    view.getBidActionBtn().setText("View open bid offers");
                } else if (model.getBidType() == Constant.CLOSEBID) {
                    view.getBidActionBtn().setText("View close bid offers");
                } else {
                    view.getBidActionBtn().setText("Post a Bid");
                }
            } else if (model.getUserType().equals("Tutor")) {
                view.getBidActionBtn().setText("View current biddings");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (model.getUserType().equals("Student")) {
            if (model.getBidType() == Constant.OPENBID) {
                // TODO: open openbid view
            } else if (model.getBidType() == Constant.CLOSEBID) {
                // TODO: open closebid view
            } else {
                // Create Bidding Creation Model
                BiddingCreationModel bidCreateModel = new BiddingCreationModel();
                // Create Bidding Creation View
                BiddingCreationView bidCreateView = new BiddingCreationView(bidCreateModel);
                // Create Bidding Creation Controller
                BiddingCreationController bidCreatecontroller = new BiddingCreationController(bidCreateView,bidCreateModel);
            }
        } else if (model.getUserType().equals("Tutor")) {
            // TODO: open view biddings window
        }
    }
}
