package com.matchingSystem.Controller;

import com.matchingSystem.Model.BiddingCreationModel;
import com.matchingSystem.Model.LoginModel;
import com.matchingSystem.View.BiddingCreationView;
import com.matchingSystem.View.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BiddingCreationController {
    private BiddingCreationView view;
    private BiddingCreationModel model;

    public BiddingCreationController(BiddingCreationView view, BiddingCreationModel model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {
        // Initialise action listeners in view to interpret user input.
        getView().getBtnInitiateBiddingSubmit().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

            }
        });
    }

    private void postBid(){

    }

    private void getParamsForCreation(){

    }
    public BiddingCreationView getView() {
        return this.view;
    }
}
