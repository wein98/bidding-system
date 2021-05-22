package com.matchingSystem.Controller;

import com.matchingSystem.Model.ContractCreationModel;
import com.matchingSystem.Utility;
import com.matchingSystem.View.ContractCreationView;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContractCreationController implements ActionListener {
    private ContractCreationView view;
    private ContractCreationModel model;
    private JSONObject details;

    public ContractCreationController(ContractCreationView view, ContractCreationModel model, JSONObject details) {
        this.view = view;
        this.model = model;
        this.details = details;
        initController();
    }

    private void initController() {
        // Initialise action listeners in view to interpret user input.
        getView().getSubmitButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int index = this.view.getContractDurationIndex();
        String month = Utility.contractDuration[index];
        //create contract
        model.setContractDuration(month);
        model.initiateContract(details.getString("studentId"), details.getString("tutorId"), details.getString(
                "subjectId"), details.getJSONObject("payInfo"), details.getJSONObject("lessInfo"),
                details.getJSONObject("addInfo"));
        view.dispose();
    }

    public ContractCreationView getView() {
        return this.view;
    }
}
