package com.matchingSystem.View;

import com.matchingSystem.LoginSystem.UserCookie;
import com.matchingSystem.Utility;
import org.json.JSONObject;

import javax.swing.*;

public class UserTypeView extends javax.swing.JFrame {
    private boolean is_tutor = false;
    private boolean is_student = false;

    // UI components
    private JPanel window;
    private JButton isStudentBtn;
    private JButton isTutorBtn;

    public UserTypeView() {
        JSONObject jwtObject = Utility.decodeJWT(UserCookie.getJwtToken());

        if (jwtObject.getBoolean("isStudent")) {
            is_student = true;
        }

        if (jwtObject.getBoolean("isTutor")) {
            is_tutor = true;
        }

        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        if (is_student) {
            isStudentBtn.setVisible(true);
        }

        if (is_tutor) {
            isTutorBtn.setVisible(true);
        }

        setContentPane(window);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public JButton getIsStudentBtn() {
        return isStudentBtn;
    }

    public JButton getIsTutorBtn() {
        return isTutorBtn;
    }
}
