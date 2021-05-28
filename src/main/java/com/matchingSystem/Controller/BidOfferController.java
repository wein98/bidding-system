package com.matchingSystem.Controller;

import com.matchingSystem.Model.BidOfferModel;
import com.matchingSystem.View.BidOfferView;
import org.json.JSONObject;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class BidOfferController implements Observer {
    private BidOfferView view;
    private BidOfferModel model;

    public BidOfferController(BidOfferView view, BidOfferModel model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {

        model.addObserver(this); // subscribe to observable
        model.updateDDL();;
        // Initialise action listeners in view to interpret user input.
        getView().getMakeOfferButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sendOffer();
                view.dispose();
            }
        });
    }

    /**
     * Update the bid with the offer send by a tutor
     */
    public void sendOffer(){
        JSONObject jsonObject = view.getFields();
        model.sendOffer(jsonObject);
    }
    /**
     * Get view linked to this controller
     * @return the view object
     */
    public BidOfferView getView() {
        return this.view;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
