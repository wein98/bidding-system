package com.matchingSystem.View;

import com.matchingSystem.API.UserAPI;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestingForm extends javax.swing.JFrame implements ActionListener {
    private JTextField textField1;
    private JButton button1;
    public JPanel TestingFormPanel;

    private UserAPI userAPI = new UserAPI();

    public TestingForm() {
        button1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//            JSONObject response = userAPI.getById(textField1.getText());
            JSONArray response1 = userAPI.getAll();
            System.out.println(response1.get(0).toString());
//            if (response != null && response.has("userName")) {
//
//            } else {
//                JOptionPane.showMessageDialog(null, response.getString("message"), "Login Error.", 2);
//            }

    }
}
