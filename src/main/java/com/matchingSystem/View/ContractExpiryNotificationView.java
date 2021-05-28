package com.matchingSystem.View;

import com.matchingSystem.ContractDev.ContractExpiryIterator;

import javax.swing.*;
import java.awt.*;

public class ContractExpiryNotificationView extends javax.swing.JFrame{
    private JLabel titleLabel;
    private JPanel window1;
    private JScrollPane scrollPanel;
    private JPanel panel1;
    private JLabel message;

    public ContractExpiryNotificationView(ContractExpiryIterator iterator) {
        setContractsNotifications(iterator);
        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setContentPane(window1);
        pack();
    }

    private void setContractsNotifications(ContractExpiryIterator iterator){
        panel1.removeAll();
        scrollPanel.getViewport().setView(panel1);

        if (iterator.isEmpty()) {
            this.message.setText("No contract.");
        } else {
            int count = 0;
            while (iterator.hasNext()){

                JPanel itemPanel = (JPanel) iterator.next();
                if (iterator.isLast() && count == 0 && itemPanel== null){
                    this.message.setText("No contract is expiring in a month.");
                }

                if(itemPanel!= null){
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridwidth = GridBagConstraints.REMAINDER;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    gbc.insets = new Insets(10, 0, 10, 0);
                    panel1.add(itemPanel, gbc, 0);
                    count ++;
                }

            }
        }
    }

}
