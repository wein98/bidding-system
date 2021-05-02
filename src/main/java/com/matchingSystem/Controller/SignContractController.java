package com.matchingSystem.Controller;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.View.SignContractView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignContractController implements ActionListener {
    private SignContractView view;
    private String contractId;

    public SignContractController(SignContractView view, String cId) {
        this.view = view;
        this.contractId = cId;
        initController();
    }

    private void initController() {
        view.getSignBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean signed =  APIFacade.signContractById(contractId);
        System.out.println("Contract sign : " + signed);
        view.dispose();
    }
}
