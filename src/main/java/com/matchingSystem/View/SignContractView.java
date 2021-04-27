package com.matchingSystem.View;

import javax.swing.*;

public class SignContractView extends JFrame {
    private JButton signBtn;
    private JPanel panel;

    public SignContractView() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        setContentPane(panel);
        pack();
    }

    public JButton getSignBtn() {
        return signBtn;
    }
}
