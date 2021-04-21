package com.matchingSystem.View;

import javax.swing.*;

public class BidCreationView extends javax.swing.JFrame {
    private JPanel bidCreationForm;
    private JLabel subject;
    private JLabel qualification;
    private JComboBox subjects;
    private JLabel day;
    private JLabel time;
    private JTextField inputRate;
    private JTextField inputNumLesson;
    private JLabel numOfLesson;
    private JLabel rate;
    private JTextField inputDay;
    private JTextField inputTime;
    private JRadioButton openBiddingRadioButton;
    private JComboBox qualifications;
    private JRadioButton closeBiddingRadioButton;
    private JButton startBiddingButton;

    public BidCreationView() {
//        btnLoginSubmit.addActionListener(this);
        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        String subjects[] = { "English", "Chinese", "Malay", "Maths", "Biology", "Chemistry", "Physics", "History", "Geography"};
        String qualifications[] = { "PT3", "SPM", "STPM", "Matriculation", "Diploma", "Degree", "Master" , "Doctoral"};

        this.qualifications = new JComboBox(qualifications);
        this.subjects = new JComboBox(subjects);
        setContentPane(bidCreationForm);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }
}
