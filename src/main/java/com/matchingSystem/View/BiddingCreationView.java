package com.matchingSystem.View;

import com.matchingSystem.BiddingSystem.LessonInfo;
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
    private JComboBox subjects;
    private JTextField inputRate;
    private JRadioButton openBiddingRadioButton;
    private JRadioButton closeBiddingRadioButton;
    private JButton startBiddingButton;
    private JComboBox timeVal;
    private JComboBox days;
    private JComboBox numOfLesson;
    private JComboBox dayNight;
    private JComboBox duration;

    private final BiddingCreationModel model;

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
        LessonInfo lessonInfo = new LessonInfo(
                (String)duration.getSelectedItem(),
                (String)numOfLesson.getSelectedItem(),
                (String)dayNight.getSelectedItem(),
                inputRate.getText(),
                (String)days.getSelectedItem(),
                (String)timeVal.getSelectedItem()
        );

        if(openBiddingRadioButton.isSelected()){
            return lessonInfo.getBiddingCreationJSONObj(subjects.getSelectedIndex(), "open");
        }else{
            return lessonInfo.getBiddingCreationJSONObj(subjects.getSelectedIndex(), "close");
        }
    }

    public JButton getBtnInitiateBiddingSubmit() {
        return startBiddingButton;
    }

    /**
     * extract the subject names from an arraylist of subjects
     * @param subjects arraylist of Subject
     * @return arraylist of name representation of the Subjects
     */
    private ArrayList<String> extractSubjectNames(ArrayList<Subject> subjects){
        ArrayList<String> subjectNames = new ArrayList<>();
        for(Subject subject: subjects){
            subjectNames.add(subject.getName());
        }

        return subjectNames;
    }
}
