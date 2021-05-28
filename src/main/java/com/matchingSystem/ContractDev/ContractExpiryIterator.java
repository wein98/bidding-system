package com.matchingSystem.ContractDev;

import com.matchingSystem.Constant;
import com.matchingSystem.LoginSystem.UserCookie;
import com.matchingSystem.Utility;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ContractExpiryIterator implements Iterator {
    private int currentPosition = 0;
    private List<Contract> contracts = new ArrayList<>();

    public ContractExpiryIterator() {}

    private void lazyLoad() {
        if (contracts.size() == 0){
            contracts = UserCookie.getUser().getContracts();
        }
    }

    @Override
    public boolean hasNext() {
        lazyLoad();
        return currentPosition < contracts.size();
    }

    public boolean isLast() {
        lazyLoad();
        return currentPosition == contracts.size()-1;
    }

    public Object next() {
        try {
            if (!hasNext()) {
                return null;
            }

            Contract c = contracts.get(currentPosition);

            if (c.isExpired()) {
                currentPosition++;
                return null;
            }
            // check if the contract is expiring in one month or less
            LocalDateTime oneMonth = LocalDate.now().plusMonths(1).atTime(0, 0);
            Timestamp oneMonthAhead = Timestamp.valueOf(oneMonth);
            Timestamp expiry = c.getExpiryTimestamp();

            int isExpiringInOneMonth = oneMonthAhead.compareTo(expiry);

            JPanel panel = new JPanel();
            panel.getInsets().set(20, 20, 20, 20);
            panel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // contract is not expired and is expiring in one month or less
            if (isExpiringInOneMonth >= 0) {

                JTable table = getTable(c);
                // resize table columns
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.getColumnModel().getColumn(0).setPreferredWidth(150);
                table.getColumnModel().getColumn(1).setPreferredWidth(150);
                gbc.gridheight = 3;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weighty = 1.0;
                panel.add(table, gbc);
            }

            currentPosition++;
            return panel;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private JTable getTable(Contract c){
        String[][] rec = {
                {"Tutor name", c.getTutorName()},
                {"Subject name", c.getSubject().getName()},
                {"Expiring on", c.getExpiryDate()},
        };

        // show student if user is a tutor
        if (UserCookie.getUserType() == Constant.IS_TUTOR){
            rec[0][0] = "Student name";
            rec[0][1] = c.getStudentName();
        }

        String[] col = {"",""};
        return new JTable(rec, col);
    }

    public boolean isEmpty() {
        lazyLoad();
        return contracts.isEmpty();
    }
}
