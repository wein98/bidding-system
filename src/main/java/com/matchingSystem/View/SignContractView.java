package com.matchingSystem.View;

import com.matchingSystem.Constant;
import com.matchingSystem.Model.Contract;
import com.matchingSystem.Utility;

import javax.swing.*;

public class SignContractView extends JFrame {
    private JButton signBtn;
    private JPanel panel;
    private JLabel studentNameLabel;
    private JLabel tutorNameLabel;
    private JLabel subjectLabel;
    private JLabel lessonDayTimeLabel;
    private JLabel rateLabel;
    private JLabel expiryLabel;
    private JLabel studentNameField;
    private JLabel tutorNameField;
    private JLabel subjectNameField;
    private JLabel lessonDayTimeField;
    private JLabel rateField;
    private JLabel expiryField;

    public SignContractView(Contract contract) {
        setContent(contract);
        initComponents();
        this.setVisible(true);
    }

    private void setContent(Contract contract){
        this.studentNameField.setText(contract.getFirstParty().getName());
        this.tutorNameField.setText(contract.getSecondParty().getName());
        this.subjectNameField.setText(contract.getSubject().getName());
        this.lessonDayTimeField.setText(contract.getDayTime());
        this.rateField.setText(contract.getAdditionalInfo().getString("rate"));
        this.expiryField.setText(Utility.sdf2.format(contract.getExpiryDate()));
    }

    private void initComponents() {
        setContentPane(panel);
        pack();
    }

    public JButton getSignBtn() {
        return signBtn;
    }
}
