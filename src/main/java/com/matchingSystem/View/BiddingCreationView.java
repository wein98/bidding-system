package com.matchingSystem.View;

import com.matchingSystem.Model.BiddingCreationModel;
import com.matchingSystem.LoginSystem.Qualification;
import com.matchingSystem.BiddingSystem.Subject;
import com.matchingSystem.Utility;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class BiddingCreationView extends javax.swing.JFrame implements Observer {
    private JPanel bidCreationForm;
    private JLabel subjectLabel;
    private JComboBox subjects;
    private JLabel dayLabel;
    private JLabel timeLabel;
    private JTextField inputRate;
    private JLabel numOfLessonLabel;
    private JLabel rateLabel;
    private JRadioButton openBiddingRadioButton;
//    private JComboBox qualifications;
    private JRadioButton closeBiddingRadioButton;
    private JButton startBiddingButton;
    private JComboBox timeVal;
    private JComboBox days;
    private JComboBox numOfLesson;
    private JComboBox dayNight;
    private JLabel typeLabel;
    private JLabel durationLabel;
    private JComboBox duration;

    private BiddingCreationModel model;

    public BiddingCreationView(BiddingCreationModel model) {
        this.model = model;
        model.addObserver(this);
        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setContentPane(bidCreationForm);
        pack();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof BiddingCreationModel) {
            System.out.println("---> Updating all fields in form");
//            ArrayList<String> qualificationTitles = extractQualificationNames(model.getQualifications());
//            for (String item: qualificationTitles) {
//                this.qualifications.addItem(item);
//            }
            ArrayList<String> subjectNames = extractSubjectNames(model.getSubjects());
            for (String item: subjectNames) {
                this.subjects.addItem(item);
            }
            for (String item: Utility.timeVals) {
                this.timeVal.addItem(item);
            }
            for (String item: Utility.dayNight) {
                this.dayNight.addItem(item);
            }
            for (String item: Utility.numsForLesson) {
                this.numOfLesson.addItem(item);
            }
            for (String item: Utility.days) {
                this.days.addItem(item);
            }
            for (String item: Utility.duration) {
                 this.duration.addItem(item);
            }
            System.out.println("---> Updated all fields in form");
        }
    }

    /**
     * Retrieve all user selection in form and transfer into a JSONObject
     * @return the json object including all field values
     */
    public JSONObject getFields(){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("subjectIndex", this.subjects.getSelectedIndex());
//        jsonObj.put("qualification", (String)this.qualifications.getSelectedItem());
        if(openBiddingRadioButton.isSelected()){
            jsonObj.put("type","open");
        }else{
            jsonObj.put("type","close");
        }

        JSONObject additionalInfo = new JSONObject();
        additionalInfo.put("time",(String)this.timeVal.getSelectedItem());
        additionalInfo.put("dayNight",(String)this.dayNight.getSelectedItem());
        additionalInfo.put("prefDay",(String)this.days.getSelectedItem());
        additionalInfo.put("numOfLesson",(String)this.numOfLesson.getSelectedItem());
        additionalInfo.put("rate",(String)this.inputRate.getText());
        additionalInfo.put("duration", (String)this.duration.getSelectedItem());

        jsonObj.put("additionalInfo", additionalInfo);
        return jsonObj;
    }

    public JButton getBtnInitiateBiddingSubmit() {
        return startBiddingButton;
    }

    private ArrayList<String> extractQualificationNames(ArrayList<Qualification> qualifications){
        ArrayList<String> qualificationNames = new ArrayList<>();
        for(Qualification qualification: qualifications){
            qualificationNames.add(qualification.getTitle());
        }
        return qualificationNames;
    }

    private ArrayList<String> extractSubjectNames(ArrayList<Subject> subjects){
        ArrayList<String> subjectNames = new ArrayList<>();
        for(Subject subject: subjects){
            subjectNames.add(subject.getName());
        }

        return subjectNames;
    }
}
