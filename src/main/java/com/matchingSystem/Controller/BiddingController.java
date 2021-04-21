package com.matchingSystem.Controller;

import com.matchingSystem.Model.LoginModel;
import com.matchingSystem.View.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BiddingController {
    private LoginView view;
    private LoginModel model;

    public BiddingController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {
        // Initialise action listeners in view to interpret user input.
        getView().getBtnLoginSubmit().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

            }
        });
    }

    public LoginView getView() {
        return this.view;
    }
}
