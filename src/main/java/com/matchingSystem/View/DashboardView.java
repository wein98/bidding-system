package com.matchingSystem.View;

import com.matchingSystem.Constant;
import com.matchingSystem.Controller.SignContractController;
import com.matchingSystem.Model.Contract;
import com.matchingSystem.Model.DashboardModel;
import com.matchingSystem.UserCookie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        // disable creation of new Bid request if student already have more than 5 contracts
//        if (this.model.getUserType().equals("Student") && this.model.getContractArrayList().size() == 5){
//            this.BidActionBtn.setVisible(false);
//        }else{
//            this.BidActionBtn.setVisible(true);
//        }
        scrollPane.getViewport().setView(panel1);
        setContentPane(window2);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public JButton getBidActionBtn() {
        return BidActionBtn;
    }

    private void setContractsPanel(ArrayList<Contract> contracts) {
        panel1.removeAll();
//        for (int i=0; i<=4; i++) {
//         disable creation of new Bid request if student already have more than 5 contracts
        if (this.model.getUserType().equals("Student") && this.model.getContractArrayList().size() == 5){
            this.BidActionBtn.setVisible(false);
        }else{
            this.BidActionBtn.setVisible(true);
        }
        for (Contract c: contracts) {
            JPanel panel = new JPanel();
            panel.getInsets().set(20, 20, 20, 20);
            JTable table = getTable(c);
            // resize table columns
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getColumnModel().getColumn(0).setPreferredWidth(150);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);

            panel.add(table);
            if(UserCookie.getUserType() == Constant.IS_TUTOR && c.getDateSigned() == null) {
                JButton signBut = new JButton();
                signBut.setText("Sign");
                signBut.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // when the sign button is hit, trigger the sign contract view and controller
                        SignContractView signView = new SignContractView(c,"sign");
                        SignContractController controller = new SignContractController(signView, c.getId());
                    }
                });
                panel.add(signBut);
            }else if(UserCookie.getUserType() == Constant.IS_TUTOR && c.getDateSigned() != null){
                JButton signBut = new JButton();
                signBut.setText("View");
                signBut.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // when the sign button is hit, trigger the sign contract view and controller
                        SignContractView signView = new SignContractView(c,"view");
                        SignContractController controller = new SignContractController(signView, c.getId());
                    }
                });
                panel.add(signBut);
            }
            else{
                JButton signBut = new JButton();
                signBut.setText("View"); // view contract
                signBut.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // when the sign button is hit, trigger the sign contract view and controller
                        SignContractView signView = new SignContractView(c,"view");
                        SignContractController controller = new SignContractController(signView, c.getId());
                    }
                });
                panel.add(signBut);
            }
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 0, 10, 0);
            panel1.add(panel, gbc, 0);
        }
    }

    private JTable getTable(Contract c) {
        String[][] rec = {
                {"Tutor name", c.getSecondParty().getName()},
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
