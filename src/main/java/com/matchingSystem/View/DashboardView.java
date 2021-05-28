package com.matchingSystem.View;

import com.matchingSystem.ContractDev.ContractLayoutIterator;
import com.matchingSystem.Model.DashboardModel;

import javax.swing.*;
import java.awt.*;


public class DashboardView extends javax.swing.JFrame {
    // Profile section UI components
    private JLabel userTypeLabel;
    private JLabel usernameLabel;
    private JLabel fullNameLabel;
    private JPanel window2;
    private JButton ContractRefreshBtn;
    private JButton BidActionBtn;
    private JScrollPane scrollPane;
    private JPanel panel1;
    private JButton subscribeBidsBtn;

    public DashboardView() {
        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        scrollPane.getViewport().setView(panel1);
        setContentPane(window2);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public void setContractsPanel(ContractLayoutIterator iterator) {
        panel1.removeAll();
        scrollPane.getViewport().setView(panel1);

        if (iterator.isEmpty()) {
            JLabel textLabel = new JLabel("You have no current or previous contract.");
            panel1.add(textLabel);
        } else {
            while (iterator.hasNext()) {
                JPanel contractItemPanel = (JPanel) iterator.next();

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(10, 0, 10, 0);
                JLabel textLabel = new JLabel("You have no current or previous contract.");

                panel1.add(contractItemPanel, gbc, 0);
            }
        }
    }

    public JLabel getUserTypeLabel() {
        return userTypeLabel;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public JLabel getFullNameLabel() {
        return fullNameLabel;
    }

    public JButton getContractRefreshBtn() {
        return ContractRefreshBtn;
    }

    public JButton getBidActionBtn() {
        return BidActionBtn;
    }

    public JButton getSubscribeBidsBtn() {
        return subscribeBidsBtn;
    }
}
