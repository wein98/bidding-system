package com.matchingSystem.View;

import com.matchingSystem.ContractDev.Contract;
import com.matchingSystem.LoginSystem.Tutor;
import com.matchingSystem.Model.RenewContractModel;
import com.matchingSystem.Utility;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RenewContractView extends JFrame  {
    private JTextField rateInput;
    private JComboBox timeValOptions;
    private JComboBox prefDayOptions;
    private JComboBox numOfLessonOptions;
    private JComboBox dayNightOptions;
    private JComboBox durationOptions;
    private JButton renewBtn;
    private JComboBox newTutorOptions;
    private JLabel newTutorLabel;
    private JLabel reuseLabel;
    private JScrollPane reuseContractsScroll;
    private JPanel window;
    private JPanel panel;
    private JComboBox contractDurationOptions;
    private JLabel subjectNameLabel;
    private JLabel subjectDescLabel;

    private final RenewContractModel model;

    public RenewContractView(RenewContractModel model) {
        this.model = model;
        this.setVisible(true);
        initComponents();
    }

    private void initComponents() {
        if(model.isNewTutor()) {
            reuseLabel.setVisible(false);
            reuseContractsScroll.setVisible(false);
        } else {
            newTutorLabel.setVisible(false);
            newTutorOptions.setVisible(false);
        }
        setContentPane(window);
        updateFieldsOptions();
        pack();
    }

    public void setReuseContractPanel(ArrayList<Contract> contracts) {
        panel.removeAll();
        reuseContractsScroll.getViewport().setView(panel);

        for (Contract c: contracts) {
            JPanel panel1 = new JPanel();
            panel1.getInsets().set(20, 20, 20, 20);
            panel1.setLayout(new GridBagLayout());
            GridBagConstraints gbc1 = new GridBagConstraints();

            JTable table = getTable(c);

            // resize table columns
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.getColumnModel().getColumn(0).setPreferredWidth(150);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            gbc1.gridy = 0;
            panel1.add(table, gbc1);

            JButton reuseBtn = new JButton("Reuse");
            reuseBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO: load this contract details to the input areas
                    Contract reuseContract = model.getContract(contracts.indexOf(c));
                    System.out.println(reuseContract);
                    loadInputs(reuseContract);
                }
            });
            gbc1.gridy = 1;
            panel1.add(reuseBtn, gbc1);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 0, 10, 0);
            panel.add(panel1, gbc, 0);

        }
    }

    public void setMatchingTutors(ArrayList<Tutor> tutors) {
        System.out.println(tutors);
        for (Tutor t: tutors) {
            newTutorOptions.addItem(t.getFullName() + ", Competency level: " + t.getCompetencyLvlFromSubject(model.getSubject()));
        }
    }

    private void updateFieldsOptions() {
        for (String item: Utility.timeVals) {
            this.timeValOptions.addItem(item);
        }
        for (String item: Utility.dayNight) {
            this.dayNightOptions.addItem(item);
        }
        for (String item: Utility.numsForLesson) {
            this.numOfLessonOptions.addItem(item);
        }
        for (String item: Utility.days) {
            this.prefDayOptions.addItem(item);
        }
        for (String item: Utility.duration) {
            this.durationOptions.addItem(item);
        }
        for (String item: Utility.contractDuration) {
            this.contractDurationOptions.addItem(item);
        }
    }

    private void loadInputs (Contract c) {
        JSONObject lessonInfo = c.getLessonInfo();
        JSONObject addInfo = c.getAdditionalInfo();

        timeValOptions.setSelectedItem(lessonInfo.getString("time"));
        dayNightOptions.setSelectedItem(lessonInfo.getString("dayNight"));
        prefDayOptions.setSelectedItem(lessonInfo.getString("prefDay"));
        numOfLessonOptions.setSelectedItem(lessonInfo.getString("numOfLesson"));
        durationOptions.setSelectedItem(lessonInfo.getString("duration"));
        rateInput.setText(addInfo.getString("rate"));
    }

    private JTable getTable(Contract c) {
        String [][] rec = null;

        JSONObject lessonInfo = c.getLessonInfo();
        JSONObject addInfo = c.getAdditionalInfo();

        rec = new String[][] {
                {"Tutor name", c.getSecondParty().getName()},
//                {"Tutor competency level", String.valueOf(b.getTutorCompLvl())},
                {"No. of sessions", lessonInfo.getString("numOfLesson")},
                {"Day & Time", c.getDayTime()},
                {"Duration (hours per session)", lessonInfo.getString("duration")},
                {"Rate (per hour)", addInfo.getString("rate")},
                {"Contract duration (months)", addInfo.getString("duration")}
        };

        String[] col = {"", ""};
        return new JTable(rec, col);
    }

    public JButton getRenewBtn() {
        return renewBtn;
    }

    public JLabel getSubjectNameLabel() {
        return subjectNameLabel;
    }

    public JLabel getSubjectDescLabel() {
        return subjectDescLabel;
    }
}
