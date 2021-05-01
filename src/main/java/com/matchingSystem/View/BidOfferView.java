package com.matchingSystem.View;

import com.matchingSystem.Model.BidOfferModel;
import com.matchingSystem.Utility;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class BidOfferView extends javax.swing.JFrame implements Observer {
    private JPanel panel;
    private JComboBox numOfLessonDDL;
    private JComboBox dayDDL;
    private JComboBox timeDDL;
    private JComboBox durationDDL;
    private JTextField rateText;
    private JButton makeOfferButton;
    private JLabel numOfLessonLabel;
    private JLabel dayLabel;
    private JLabel timeLabel;
    private JLabel durationLabel;
    private JLabel rateLabel;
    private JComboBox dayNightDDL;
    private JTextField messageText;
    private JLabel messageLabel;
    private JLabel titleLabel;

    private BidOfferModel model;
    private String type;

    public BidOfferView(BidOfferModel model, String type) {
        this.model = model;
        model.addObserver(this);

        this.type = type;
        if (type.equals("open")) {
            this.messageLabel.setVisible(false);
            this.messageText.setVisible(false);
        } else if (type.equals("close")) {
            this.titleLabel.setText("Reply Bid"); // not working
            this.makeOfferButton.setText("Send message");
            this.messageLabel.setVisible(true);
            this.messageText.setVisible(true);
        }
        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setContentPane(panel);
        pack();
    }

    @Override
    public void update(Observable o, Object arg) {
        for (String item : Utility.timeVals) {
            this.timeDDL.addItem(item);
        }
        for (String item : Utility.dayNight) {
            this.dayNightDDL.addItem(item);
        }
        for (String item : Utility.numsForLesson) {
            this.numOfLessonDDL.addItem(item);
        }
        for (String item : Utility.days) {
            this.dayDDL.addItem(item);
        }
        for (String item : Utility.duration) {
            this.durationDDL.addItem(item);
        }
    }

    public JButton getMakeOfferButton() { return this.makeOfferButton; }

    public JSONObject getFields() {
        JSONObject fields = new JSONObject();
        fields.put("type",this.type);
        fields.put("time", (String) this.timeDDL.getSelectedItem());
        fields.put("dayNight", (String) this.dayNightDDL.getSelectedItem());
        fields.put("prefDay", (String) this.dayDDL.getSelectedItem());
        fields.put("numOfLesson", (String) this.numOfLessonDDL.getSelectedItem());
        fields.put("rate", (String) this.rateText.getText());
        fields.put("duration", (String) this.durationDDL.getSelectedItem());
        if (this.type.equals("close")){
            fields.put("message",this.messageText.getText());
        }
        return fields;
    }
}
