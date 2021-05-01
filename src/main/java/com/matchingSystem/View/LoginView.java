package com.matchingSystem.View;

import javax.swing.*;

public class LoginView extends javax.swing.JFrame {

    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JButton btnLoginSubmit;
    public JPanel loginForm;
    private JLabel JLabelLogin;

    public LoginView() {
        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setContentPane(loginForm);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public JTextField getTextFieldUsername() {
        return textFieldUsername;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getBtnLoginSubmit() {
        return btnLoginSubmit;
    }
}
