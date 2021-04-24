package com.matchingSystem.Controller;

import com.matchingSystem.API.APIAdapters.BidAPI;
import com.matchingSystem.Model.BiddingCreationModel;
import com.matchingSystem.Model.LoginModel;
import com.matchingSystem.UserCookie;
import com.matchingSystem.View.BiddingCreationView;
import com.matchingSystem.View.LoginView;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class BiddingCreationController implements Observer {
    private BiddingCreationView view;
    private BiddingCreationModel model;

    public BiddingCreationController(BiddingCreationView view, BiddingCreationModel model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {

        model.addObserver(this); // subscribe to observable
        getLatestDataForDDL();

        // Initialise action listeners in view to interpret user input.
        getView().getBtnInitiateBiddingSubmit().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("HAHA button is hit!");
                postBid();
            }
        });
    }

    /**
     * Retrieve the latest subject and qualification from DB
     */
    private void getLatestDataForDDL(){
        model.updateDDL();
    }

    /**
     * Tell the model to create the Bid object and post it to the DB
     */
    private void postBid(){
        JSONObject jsonObj = view.getFields();
        model.initiateBid(jsonObj);
    }

    /**
     * Get view linked to this controller
     * @return
     */
    public BiddingCreationView getView() {
        return this.view;
    }

    /**
     * Function called when Model has changes
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("smtg");
    }
}
