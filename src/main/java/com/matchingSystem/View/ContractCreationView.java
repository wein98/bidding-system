package com.matchingSystem.View;

import com.matchingSystem.Model.ContractCreationModel;
import com.matchingSystem.Utility;

import javax.swing.*;

public class ContractCreationView extends javax.swing.JFrame {
    private JPanel panel;
    private JLabel titleLabel;
    private JComboBox durationDDL;
    private JLabel durationLabel;
    private JButton submitButton;

    private ContractCreationModel model;
    public ContractCreationView(ContractCreationModel model){
        this.model = model;
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        for (String item : Utility.contractDuration) {
            this.durationDDL.addItem(item);
        }
        // default is 6 months
        this.durationDDL.setSelectedIndex(1);
        setContentPane(panel);
        pack();
    }

    public JButton getSubmitButton(){
        return this.submitButton;
    }
    public int getContractDurationIndex() {
        return this.durationDDL.getSelectedIndex();
    }
}
