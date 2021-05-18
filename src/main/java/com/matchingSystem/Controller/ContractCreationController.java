package com.matchingSystem.Controller;

import com.matchingSystem.Utility;
import com.matchingSystem.View.ContractCreationView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContractCreationController {
    private ContractCreationView view;


    public ContractCreationController(ContractCreationView view) {
        this.view = view;
        initController();
    }

    private void initController() {

        ;
        // Initialise action listeners in view to interpret user input.
        getView().getSubmitButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passContractDurationSelected();
                view.dispose();
            }
        });
    }

    public String passContractDurationSelected(){
        int index = this.view.getContractDurationIndex();
        return Utility.contractDuration[index];
    }

    public ContractCreationView getView(){
        return this.view;
    }
}
