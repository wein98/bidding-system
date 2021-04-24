package com.matchingSystem.View;

import com.matchingSystem.Model.Qualification;
import com.matchingSystem.Model.Subject;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;

public class BiddingCreationView extends javax.swing.JFrame {
    private JPanel bidCreationForm;
    private JLabel subjectLabel;
    private JLabel qualificationLabel;
    private JComboBox subjects;
    private JLabel dayLabel;
    private JLabel timeLabel;
    private JTextField inputRate;
    private JLabel numOfLessonLabel;
    private JLabel rateLabel;
    private JRadioButton openBiddingRadioButton;
    private JComboBox qualifications;
    private JRadioButton closeBiddingRadioButton;
    private JButton startBiddingButton;
    private JComboBox timeVal;
    private JComboBox days;
    private JComboBox numOfLesson;
    private JComboBox dayNight;
    private JLabel typeLabel;

    public BiddingCreationView() {
//        btnLoginSubmit.addActionListener(this);
        initComponents();
        this.setVisible(true);
    }

    private ArrayList<String> retrieveQualificationList(ArrayList<Qualification> qualifications){
        ArrayList<String> qualificationNames = new ArrayList<>();
        for(Qualification qualification: qualifications){
            qualificationNames.add(qualification.getTitle());
        }
        return qualificationNames;
    }
    private ArrayList<String> retrieveSubjectList(ArrayList<Subject> subjects){
        ArrayList<String> subjectNames = new ArrayList<>();
        for(Subject subject: subjects){
            subjectNames.add(subject.getName());
        }

        return subjectNames;
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        //TODO: Have to retrieve and arrange the list of qualifications and subjects from DB other than hardcoding
        ArrayList<String> subjects = retrieveSubjectList();
        String[] qualifications = { "PT3", "SPM", "STPM", "Matriculation", "Diploma", "Degree", "Master" , "Doctoral"};
        // JComboBox is generic, and does not support primitive type int, use Integer
        String[] timeVals = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        String[] dayNight = {"AM","PM"};
        String[] numsForLesson = {"1","2","3","4","5","6","7","8","9","10"};
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        this.qualifications = new JComboBox(qualifications);
        this.subjects = new JComboBox(subjects);
        this.timeVal = new JComboBox(timeVals);
        this.dayNight = new JComboBox(dayNight);
        this.days = new JComboBox(days);
        this.numOfLesson = new JComboBox(numsForLesson);

        setContentPane(bidCreationForm);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    /**
     * Retrieve all user selection in form and transfer into a JSONObject
     * @return the json object including all field values
     */
    public JSONObject getFields(){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("subject", (String)this.subjects.getSelectedItem());
        jsonObj.put("qualification", (String)this.qualifications.getSelectedItem());
        if(openBiddingRadioButton.isSelected()){
            jsonObj.put("type","open");
        }else{
            jsonObj.put("type","close");
        }
        jsonObj.put("time",(String)this.timeVal.getSelectedItem());
        jsonObj.put("dayNight",(String)this.dayNight.getSelectedItem());
        jsonObj.put("prefDay",(String)this.days.getSelectedItem());
        jsonObj.put("numOfLesson",(String)this.numOfLesson.getSelectedItem());
        jsonObj.put("rate",(String)this.inputRate.getText());
        return jsonObj;
    }

    public JButton getBtnInitiateBiddingSubmit() {
        return startBiddingButton;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
