package com.matchingSystem.ContractDev;

import com.matchingSystem.Constant;
import com.matchingSystem.Controller.RenewContractController;
import com.matchingSystem.Controller.SignContractController;
import com.matchingSystem.LoginSystem.User;
import com.matchingSystem.LoginSystem.UserCookie;
import com.matchingSystem.Model.RenewContractModel;
import com.matchingSystem.View.RenewContractView;
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
        GridBagConstraints gbc = new GridBagConstraints();

        JTable table = getTable(c);

        // resize table columns
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        gbc.gridheight = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        panel.add(table, gbc);

        if (UserCookie.getUserType() == Constant.IS_STUDENT && c.isExpired()) {
            gbc.gridheight = 1;
            gbc.gridx = 1;
            gbc.gridy = 0;
            panel.add(getRenewSameTutorBtn(c), gbc);

            gbc.gridheight = 1;
            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(getRenewNewTutorBtn(c), gbc);

        } else if (UserCookie.getUserType() == Constant.IS_TUTOR && c.getDateSigned() == null) {
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

    /**
     * Checks if the student is having 5 active bids.
     * @return
     */
    public boolean isFull() {
        lazyload();

        int count = 0;

        for (Contract c: contracts) {
            if (!c.isExpired()) {
                count += 1;
            }
        }

        return count > 5;
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

    /**
     * Function that creates a renew contract with same tutor button for this contract
     * @param c contract
     * @return the renew button
     */
    private JButton getRenewSameTutorBtn(Contract c) {
        JButton btn = new JButton();
        btn.setText("Renew same tutor");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // opens edit new contract view
                RenewContractModel model = new RenewContractModel(false, c);
                RenewContractView view = new RenewContractView(model);
                new RenewContractController(view, model);
            }
        });

        return btn;
    }

    /**
     * Function that creates a renew contract with new tutor button for this contract
     * @param c contract
     * @return the renew button
     */
    private JButton getRenewNewTutorBtn(Contract c) {
        JButton btn = new JButton();
        btn.setText("Renew new tutor");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // opens edit new contract view
                RenewContractModel model = new RenewContractModel(true, c);
                RenewContractView view = new RenewContractView(model);
                new RenewContractController(view, model);
            }
        });

        return btn;
    }
}
