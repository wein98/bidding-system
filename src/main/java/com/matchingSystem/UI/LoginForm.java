package com.matchingSystem.UI;

import com.matchingSystem.API.UserAPI;
import com.matchingSystem.Utility;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends javax.swing.JFrame implements ActionListener {

    private JTextField textFieldUsername;
    private JPasswordField passwordField1;
    private JButton btnLoginSubmit;
    public JPanel loginForm;
    private JLabel JLabelLogin;

    private UserAPI userAPI = new UserAPI();

    public LoginForm() {
        btnLoginSubmit.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JSONObject response = userAPI.userLogin(textFieldUsername.getText(), String.valueOf(passwordField1.getText()));
        if (response.has("jwt")) {
            System.out.println(response.getString("jwt"));
            Utility.decodeJWT(response.getString("jwt"));
        } else {
            JOptionPane.showMessageDialog(null, response.getString("message"), "Login Error.", 2);
        }
    }
}
