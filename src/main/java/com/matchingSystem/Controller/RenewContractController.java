package com.matchingSystem.Controller;

import com.matchingSystem.Model.RenewContractModel;
import com.matchingSystem.View.RenewContractView;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class RenewContractController implements Observer, ActionListener {
    private final RenewContractView view;
    private final RenewContractModel model;

    public RenewContractController(RenewContractView view, RenewContractModel model) {
        this.view = view;
        this.model = model;
        model.addObserver(this);    // subscribe to observable
        initController();
    }

    private void initController() {
        model.addObserver(this);
        view.getRenewBtn().addActionListener(this);
        if(model.isNewTutor()) {
            populateMatchingTutors();
        } else {
            populatePreviousContracts();
        }
    }

    private void populatePreviousContracts() {
        model.loadPreviousContracts();
    }

    private void populateMatchingTutors() {
        model.loadMatchingTutors();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RenewContractModel) {
            view.getSubjectNameLabel().setText("Subject name: " + model.getSubject().getName());
            view.getSubjectDescLabel().setText("Subject description: " + model.getSubject().getDescription());

            if (model.isNewTutor()) {
                System.out.println(model.getTutors());

                view.setMatchingTutors(model.getTutors());
            } else {
                view.setReuseContractPanel(model.getContracts());

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(view.getRenewBtn())) {
            JSONObject jsonObj = this.view.getField();
            model.renewContract(jsonObj);
            view.dispose();
        }
    }
}
