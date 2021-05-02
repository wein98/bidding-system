package com.matchingSystem.View;

import com.matchingSystem.ContractDev.Contract;

import javax.swing.*;
import java.text.SimpleDateFormat;

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
    private JLabel titleLabel;
    private JLabel numOfLessonLabel;
    private JLabel numOfLessonField;
    private JLabel dateSignedLabel;
    private JLabel dateSignedField;

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
            if(contract.getDateSigned() != null) {
                this.dateSignedField.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(contract.getDateSigned()));
            }else{
                this.dateSignedField.setText("none");
            }
            this.dateSignedField.setVisible(true);
        }else if(type.equals("sign")){
            this.dateSignedField.setVisible(false);
            this.dateSignedLabel.setVisible(false);
            this.signBtn.setVisible(true);
        }
        this.studentNameField.setText(contract.getFirstParty().getName());
        this.tutorNameField.setText(contract.getSecondParty().getName());
        this.subjectNameField.setText(contract.getSubject().getName());
        this.lessonDayTimeField.setText(contract.getDayTime());
        this.rateField.setText(contract.getAdditionalInfo().getString("rate"));
        this.numOfLessonField.setText(contract.getLessonInfo().getString("numOfLesson"));
        String formattedTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(contract.getExpiryDate());
        this.expiryField.setText(formattedTime);
    }

    private void initComponents() {
        setContentPane(panel);
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public JButton getSignBtn() {
        return signBtn;
    }
}
