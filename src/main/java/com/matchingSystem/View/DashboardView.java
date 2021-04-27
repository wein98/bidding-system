package com.matchingSystem.View;

import com.matchingSystem.Model.Contract;
import com.matchingSystem.Model.DashboardModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DashboardView extends javax.swing.JFrame implements Observer {
    // Profile section UI components
    private JLabel userTypeLabel;
    private JLabel usernameLabel;
    private JLabel familyNameLabel;
    private JLabel givenNameLabel;
    private JPanel profileSection;
    private JPanel window2;
    private JPanel contractsPanel;
    private JButton ContractRefreshBtn;
    private JButton BidActionBtn;
    private JScrollPane scrollPane;
    private JPanel panel1;


    private DashboardModel model;

    public DashboardView(DashboardModel model) {
        this.model = model;
        model.addObserver(this);    // subscribe to observable
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

    public JButton getBidActionBtn() {
        return BidActionBtn;
    }

    private void setContractsPanel(ArrayList<Contract> contracts) {
//        for (int i=0; i<=4; i++) {
        for (Contract c: contracts) {
            JPanel panel = new JPanel();
            panel.getInsets().set(20, 20, 20, 20);
            JTable table = getTable(contracts.get(0));
            // resize table columns
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getColumnModel().getColumn(0).setPreferredWidth(150);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);

            panel.add(table);
            panel.add(new JButton("Sign"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 0, 10, 0);
            panel1.add(panel, gbc, 0);
        }
    }

    private JTable getTable(Contract c) {
        String[][] rec = {
                {"Tutor name", c.getSecondParty().getGivenName()},
                {"Subject name", c.getSubject().getName()},
                // TODO: add c.getAdditionalInfo()
        };
        String[] col = {"", ""};
        return new JTable(rec, col);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof DashboardModel) {
            userTypeLabel.setText(model.getUserType());
            usernameLabel.setText(model.getUsername());
            familyNameLabel.setText(model.getFamilyName());
            givenNameLabel.setText(model.getGivenName());



            setContractsPanel(model.getContractArrayList());
//            textArea1.setText(model.getTesting());
        }

    }
}
