package com.matchingSystem.View;

import com.matchingSystem.ContractDev.Contract;

import javax.swing.*;

public class SignContractView extends JFrame {
    private JButton signBtn;
    private JPanel panel;
    private JLabel studentNameField;
    private JLabel tutorNameField;
    private JLabel subjectNameField;
    private JLabel lessonDayTimeField;
    private JLabel rateField;
    private JLabel expiryField;
    private JLabel titleLabel;
    private JLabel numOfLessonField;
    private JLabel dateSignedLabel;
    private JLabel dateSignedField;
    private JLabel statusField;

    // type indicate whether to VIEW or SIGN a contract
    public SignContractView(Contract contract, String type) {
        setContent(contract, type);
        initComponents();
        this.setVisible(true);
    }

    private void setContent(Contract contract, String type){
        if(type.equals("view")){
            this.titleLabel.setText("View Contract");
            this.signBtn.setVisible(false);
            this.dateSignedLabel.setVisible(true);
            this.dateSignedField.setText(contract.getDateSigned());
            this.dateSignedField.setVisible(true);

        }else if(type.equals("sign")){
            this.dateSignedField.setVisible(false);
            this.dateSignedLabel.setVisible(false);
            this.signBtn.setVisible(true);
        }

        this.statusField.setText(contract.getStatus());
        this.studentNameField.setText(contract.getStudentName());
        this.tutorNameField.setText(contract.getTutorName());
        this.subjectNameField.setText(contract.getSubject().getName());
        this.lessonDayTimeField.setText(contract.getDayTime());
        this.rateField.setText(contract.getRate());
        this.numOfLessonField.setText(contract.getNumOfLesson());
        this.expiryField.setText(contract.getExpiryDate());
    }

    private void initComponents() {
        setContentPane(panel);
        pack();
    }

    public JButton getSignBtn() {
        return signBtn;
    }
}
