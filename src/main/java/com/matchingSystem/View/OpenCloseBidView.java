package com.matchingSystem.View;

import com.matchingSystem.Constant;
import com.matchingSystem.Controller.CloseBidMsgController;
import com.matchingSystem.Controller.ContractCreationController;
import com.matchingSystem.LoginSystem.UserCookie;
import com.matchingSystem.Model.*;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OpenCloseBidView extends BiddingsView {
    private final int bidViewType;

    private DashboardModel parentModel;

    public OpenCloseBidView(int bidViewType){
        this.bidViewType = bidViewType;
        initComponents();
        this.setVisible(true);
    }

    public OpenCloseBidView(BiddingsModel model, int bidViewType, DashboardModel parentModel) {
        this.parentModel = parentModel;
        this.bidViewType = bidViewType;
        this.model = model;
        initComponents();
        this.setVisible(true);
    }

    @Override
    protected void initComponents() {
        if (bidViewType == Constant.OPEN_BIDDING_VIEW) {
            titleLabel.setText("Open Biddings");
        } else if (bidViewType == Constant.CLOSE_BIDDING_VIEW){
            titleLabel.setText("Close Biddings");
        } else if (bidViewType == Constant.TUTOR_OPEN_BIDDING_VIEW) {
            titleLabel.setText("Open Bidders");
            OpenBidDetailsPanel.setVisible(false);
            getOfferButton().setVisible(true);
        }

        setContentPane(mainPanel);
        pack();
    }

    /**
     * add in components
     * @param bidOffers
     */
    public void setPanel(ArrayList<BidOfferModel> bidOffers) {
        panel.removeAll();
        scrollPane.getViewport().setView(panel);

        if (bidOffers.size() != 0) {
            for (BidOfferModel b: bidOffers) {
                JPanel panel1 = new JPanel();
                panel1.getInsets().set(20, 20, 20, 20);
                panel1.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();

                JTable table = getTable(b);
                // resize table columns
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.getColumnModel().getColumn(0).setPreferredWidth(150);
                table.getColumnModel().getColumn(1).setPreferredWidth(150);
                c.gridheight = 2;
                c.gridx = 0;
                c.gridy = 0;
                c.weighty = 1.0;
                panel1.add(table, c);

                if (bidViewType != Constant.TUTOR_OPEN_BIDDING_VIEW) {
                    JButton btn1 = new JButton("Select offer");
                    c.gridheight = 1;
                    c.gridx = 1;
                    c.gridy = 0;
                    c.weighty = 0.5;
                    panel1.add(btn1, c);

                    // buttons action listener
                    btn1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            model.selectOffer(b);
                            parentModel.checkPostedBid();
                            // prompt the user to select contract duration
                            JSONObject details = new JSONObject();
                            details.put("studentId",UserCookie.getUser().getId());
                            details.put("tutorId", b.getOfferTutorId());
                            details.put("subjectId", model.getBid().getSubject().getId());

                            details.put("lessInfo",b.getLessonInfo().getContractLessonInfo());

                            JSONObject addInfo = new JSONObject();
                            addInfo.put("rate",b.getLessonInfo().getRate());
                            details.put("addInfo",addInfo);

                            JSONObject payInfo = new JSONObject();
                            details.put("payInfo",payInfo);
                            ContractCreationModel contractModel = new ContractCreationModel();
                            ContractCreationView contractView = new ContractCreationView(contractModel);
                            new ContractCreationController(contractView,contractModel,details);
                            dispose();
                        }
                    });

                    if (bidViewType == Constant.CLOSE_BIDDING_VIEW) {
                        JButton btn2 = new JButton("Reply message");
                        c.gridheight = 1;
                        c.gridx = 1;
                        c.gridy = 1;
                        c.weighty = 0.5;
                        panel1.add(btn2, c);

                        // buttons action listener
                        btn2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // create Reply message gui and update studentmsg
                                CloseBidMsgView msgView = new CloseBidMsgView();
                                BidOfferModel msgModel = new BidOfferModel(model.getBid(), b.getAddInfoJson());
                                CloseBidMsgController msgController = new CloseBidMsgController(msgView, msgModel);


                            }
                        });
                    }
                }

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(10, 0, 10, 0);
                panel.add(panel1, gbc, 0);
            }
        } else {
            JLabel textLabel = new JLabel("No bid offers yet. Press refresh button to update.");
            panel.add(textLabel);
        }

    }

    private JTable getTable(BidOfferModel b) {
        String [][] rec = null;
        if (bidViewType == Constant.OPEN_BIDDING_VIEW || bidViewType == Constant.TUTOR_OPEN_BIDDING_VIEW) {
            rec = new String[][] {
                {"Tutor name", b.getTutorName()},
                {"Tutor competency level", String.valueOf(b.getTutorCompLvl())},
                {"No. of sessions", b.getLessonInfo().getNumOfLesson()},
                {"Day & Time", b.getLessonInfo().getDayTime()},
                {"Duration (hours per session)", b.getLessonInfo().getLessonDuration()},
                {"Rate (per hour)", b.getLessonInfo().getRate()}};
        } else {
            // close bid offer table view
            rec = new String[][]{
                    {"Tutor name", b.getTutorName()},
                    {"Tutor competency level", String.valueOf(b.getTutorCompLvl())},
                    {"No. of sessions", b.getLessonInfo().getNumOfLesson()},
                    {"Day & Time", b.getLessonInfo().getDayTime()},
                    {"Duration (hours per session)", b.getLessonInfo().getLessonDuration()},
                    {"Rate (per hour)", b.getLessonInfo().getRate()},
                    {"Tutor message", b.getMsg().getContent()},
                    {"Your reply", b.getMsg().getStudentReply()}
            };
        }

        String[] col = {"", ""};
        return new JTable(rec, col);
    }

    public void setBidDetailsPanel(String subjectName, String compLvl) {
        subjectLabel.setText("Subject: " + subjectName);
        compLvlLabel.setText("Competency level: " + compLvl);
    }

    public int getBidViewType() {
        return bidViewType;
    }
}
