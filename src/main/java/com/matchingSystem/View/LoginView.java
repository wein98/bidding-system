package com.matchingSystem.View;

import javax.swing.*;

public class LoginView extends javax.swing.JFrame {

    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JButton btnLoginSubmit;
    public JPanel loginForm;
    private JLabel JLabelLogin;

    public LoginView() {
//        btnLoginSubmit.addActionListener(this);
        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
//        loginForm = new JPanel();
//        textFieldUsername = new JTextField();
//        passwordField = new JPasswordField();
//        btnLoginSubmit = new JButton();

        setContentPane(loginForm);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        JSONObject response = userAPI.userLogin(textFieldUsername.getText(), String.valueOf(passwordField1.getPassword()));
//        if (response.has("jwt")) {
//            System.out.println(response.getString("jwt"));
//            Utility.decodeJWT(response.getString("jwt"));
//        } else {
//            JOptionPane.showMessageDialog(null, response.getString("message"), "Login Error.", 2);
//        }
//    }

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
