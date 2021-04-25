package com.matchingSystem.Controller;

import com.matchingSystem.Model.BiddingCreationModel;
import com.matchingSystem.Model.DashboardModel;
import com.matchingSystem.View.BiddingCreationView;
import com.matchingSystem.View.DashboardView;

import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardController implements Observer {
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

        // interpret button submission for creating new Bidding request
        getView().getBidActionBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Post/Create a new bidding");
                // Create Bidding Creation Model
                BiddingCreationModel bidCreateModel = new BiddingCreationModel();
                // Create Bidding Creation View
                BiddingCreationView bidCreateView = new BiddingCreationView(bidCreateModel);
                // Create Bidding Creation Controller
                BiddingCreationController bidCreatecontroller = new BiddingCreationController(bidCreateView,bidCreateModel);
            }
        });
    }

    public void updateProfile() {
        model.setProfile();
    }

    public void updateContracts() {

    }

    public DashboardView getView() { return this.view; }
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("jaja");
    }
}
