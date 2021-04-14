package com.matchingSystem.View;

import com.matchingSystem.Constant;
import com.matchingSystem.UserCookie;
import com.matchingSystem.Utility;
import org.json.JSONObject;

import javax.swing.*;

public class UserTypeView extends javax.swing.JFrame {
    int userType;

    // UI components
    private JPanel window;
    private JButton isStudentBtn;
    private JButton isTutorBtn;
    private JLabel UserTypeViewLabel;

    public UserTypeView() {
        JSONObject jwtObject = Utility.decodeJWT(UserCookie.getInstance().getJwtToken());

        if (jwtObject.getBoolean("isStudent")) {
            userType = Constant.IS_STUDENT;
        }

        if (jwtObject.getBoolean("isTutor")) {
            userType = Constant.IS_TUTOR;
        }

        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        if (userType == Constant.IS_STUDENT) {
            isStudentBtn.setVisible(true);
        }

        if (userType == Constant.IS_TUTOR) {
            isTutorBtn.setVisible(true);
        }

        setContentPane(window);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public int getUserType() {
        return userType;
    }

    public JButton getIsStudentBtn() {
        return isStudentBtn;
    }

    public JButton getIsTutorBtn() {
        return isTutorBtn;
    }
}
