package com.matchingSystem.Controller;

import com.matchingSystem.Model.DashboardModel;
import com.matchingSystem.View.DashboardView;

import java.util.Observable;
import java.util.Observer;

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
    }

    public void updateProfile() {
        model.setProfile();
    }

    public void updateContracts() {

    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("jaja");
    }
}
