package com.matchingSystem.Controller;

import com.matchingSystem.Constant;
import com.matchingSystem.ContractDev.ContractExpiryIterator;
import com.matchingSystem.Model.BiddingCreationModel;
import com.matchingSystem.Model.BiddingsModel;
import com.matchingSystem.Model.DashboardModel;
import com.matchingSystem.LoginSystem.Student;
import com.matchingSystem.LoginSystem.UserCookie;
import com.matchingSystem.View.*;

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
        view.getContractRefreshBtn().addActionListener(this);
        updateProfile();
        updateContracts();
        view.getBidActionBtn().addActionListener(this);
        view.getSubscribeBidsBtn().addActionListener(this);

        // show the contract expiry notification
        ContractExpiryIterator iterator = new ContractExpiryIterator();
        ContractExpiryNotificationView view =  new ContractExpiryNotificationView(iterator);
    }

    public void updateProfile() {
        model.setProfile();
    }

    public void updateContracts() {
        model.updateContractList();
    }

    public DashboardView getView() { return this.view; }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(view.getContractRefreshBtn())) {
            System.out.println("Refreshing dashboard ... ");
            updateContracts();

        } else if (e.getSource().equals(view.getSubscribeBidsBtn())) {
            // open view subscribed open biddings monitor window
            System.out.println("Subscribe bid monitor");
            BiddingsModel biddingsModel = new BiddingsModel();
            TutorBidOffersView bidOffersView = new TutorBidOffersView(biddingsModel, Constant.TUTOR_SUBS_OPENBIDDINGS_VIEW);
            new TutorBidOffersController(bidOffersView, biddingsModel);

        } else {
            if (model.getUserType().equals("Student")) {
                Student studentObj = (Student) UserCookie.getUser();

                if (model.getBidType() == Constant.OPENBID) {
                    // open openbid view
                    BiddingsModel biddingsModel = new BiddingsModel(studentObj.getInitiatedBid().getId());
                    OpenCloseBidView openBidView = new OpenCloseBidView(biddingsModel, Constant.OPEN_BIDDING_VIEW,
                            this.model);
                    new OpenCloseBidController(openBidView, biddingsModel);

                } else if (model.getBidType() == Constant.CLOSEBID) {
                    // open closebid view
                    BiddingsModel biddingsModel = new BiddingsModel(studentObj.getInitiatedBid().getId());
                    OpenCloseBidView openBidView = new OpenCloseBidView(biddingsModel, Constant.CLOSE_BIDDING_VIEW,
                            this.model);
                    new OpenCloseBidController(openBidView, biddingsModel);

                } else {
                    // Open student create bid view
                    BiddingCreationModel bidCreateModel = new BiddingCreationModel();
                    BiddingCreationView bidCreateView = new BiddingCreationView(bidCreateModel);
                    new BiddingCreationController(bidCreateView,bidCreateModel, model);
                }
            } else if (model.getUserType().equals("Tutor")) {
                // open view biddings window
                BiddingsModel biddingsModel = new BiddingsModel();
                TutorBidOffersView bidOffersView = new TutorBidOffersView(biddingsModel, Constant.TUTOR_OFFER_BIDS_VIEW);
                new TutorBidOffersController(bidOffersView, biddingsModel);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof DashboardModel) {
            // Profile section
            view.getUserTypeLabel().setText(model.getUserType());
            view.getUsernameLabel().setText(model.getUsername());
            view.getFullNameLabel().setText(model.getFullName());
            view.setContractsPanel(model.getIterator());

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
                view.getSubscribeBidsBtn().setVisible(true);    // show view subscribed bids button
                view.getBidActionBtn().setText("View current biddings");
            }
        }
    }

}
