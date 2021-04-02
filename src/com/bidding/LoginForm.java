package com.bidding;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.bidding.Login.userLogin;

public class LoginForm extends javax.swing.JFrame implements ActionListener {

    private JButton BtnLoginSubmit;
    private JTextField textField_username;
    private JTextField textField_password;
    private JPanel Panel_login;
    public JPanel panelMain;

    public LoginForm() {
        BtnLoginSubmit.addActionListener(this);

//        BtnLoginSubmit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                GETRequest("/user");
//            }
//        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new LoginForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        userLogin(textField_username.getText(), textField_password.getText());
        UserCookie userCookie = UserCookie.getInstance();
        System.out.println("KEK" + userCookie.getJwtToken());
        if (userCookie.getJwtToken() != null) {
            HomeForm homeForm = new HomeForm();
            homeForm.setVisible(true);
            homeForm.pack();
            homeForm.setLocationRelativeTo(null);

            // close the current login form
            this.dispose();
        } else {
            // user login failed error message
//            JOptionPane.showMessageDialog(null, "", "Login Error.", 2);
        }
    }
}
