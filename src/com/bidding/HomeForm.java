package com.bidding;

import javax.swing.*;

public class HomeForm extends javax.swing.JFrame{
    private static String TAG = "Home_Form: ";
    private JLabel HomeLabel;
    private JTextArea textArea1;

    public HomeForm() {
        initComponents();
    }

    public void initComponents() {
        UserCookie userCookie = UserCookie.getInstance();

        HomeLabel.setText(userCookie.getJwtToken());
        textArea1.setText(userCookie.getJwtToken());
        System.out.println(TAG + userCookie.getJwtToken());
    }
}
