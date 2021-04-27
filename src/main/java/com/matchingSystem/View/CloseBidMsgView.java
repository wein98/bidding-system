package com.matchingSystem.View;

import javax.swing.*;

public class CloseBidMsgView extends JFrame{
    private JTextField messageTextField;
    private JButton sendBtn;
    private JPanel panel;

    public CloseBidMsgView() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        setContentPane(panel);
        pack();
    }

    public JTextField getMessageTextField() {
        return messageTextField;
    }

    public JButton getSendBtn() {
        return sendBtn;
    }
}
