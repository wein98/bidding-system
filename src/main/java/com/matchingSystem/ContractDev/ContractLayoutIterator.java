package com.matchingSystem.ContractDev;

import com.matchingSystem.Constant;
import com.matchingSystem.Controller.SignContractController;
import com.matchingSystem.LoginSystem.UserCookie;
import com.matchingSystem.View.SignContractView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContractLayoutIterator implements Iterator {
    private int currentPosition = 0;
    private List<Contract> contracts = new ArrayList<>();

    public ContractLayoutIterator() {}

    private void lazyload() {
        if (contracts.size() == 0) {
            UserCookie.getUser().setContracts();
            contracts = UserCookie.getUser().getContracts();
        }
    }

    @Override
    public boolean hasNext() {
        lazyload();
        return currentPosition < contracts.size();
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            return null;
        }

        Contract c = contracts.get(currentPosition);

        JPanel panel = new JPanel();
        panel.getInsets().set(20, 20, 20, 20);
        panel.setLayout(new GridBagLayout());

        JTable table = getTable(c);

        // resize table columns
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);

        panel.add(table);

        if (UserCookie.getUserType() == Constant.IS_TUTOR && c.getDateSigned() == null) {
            // Only Tutor has to sign contract if the contract is not signed
            panel.add(getSignBtn(c));
        } else {
            panel.add(getViewBtn(c));
        }

        currentPosition++;
        return panel;
    }

    public boolean isEmpty() {
        lazyload();
        return contracts.isEmpty();
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

    private JButton getSignBtn(Contract c) {
        JButton btn = new JButton();
        btn.setText("Sign");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // when the sign button is hit, trigger the sign contract view and controller
                SignContractView signView = new SignContractView(c,"sign");
                new SignContractController(signView, c.getId());
            }
        });

        return btn;
    }

    private JButton getViewBtn(Contract c) {
        JButton btn = new JButton();
        btn.setText("View");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // when the sign button is hit, trigger the sign contract view and controller
                SignContractView signView = new SignContractView(c,"view");
                new SignContractController(signView, c.getId());
            }
        });

        return btn;
    }
}
