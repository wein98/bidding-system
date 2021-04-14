package com.matchingSystem.UI;

import com.matchingSystem.UserCookie;
import com.matchingSystem.Utility;
import org.json.JSONObject;

import javax.swing.*;

public class UserTypeView extends javax.swing.JFrame {
    boolean isStudent = false;
    boolean isTutor = false;

    // UI components
    private JPanel window;
    private JButton isStudentBtn;
    private JButton isTutorBtn;
    private JLabel UserTypeViewLabel;

    public UserTypeView() {
        JSONObject jwtObject = Utility.decodeJWT(UserCookie.getInstance().getJwtToken());
        System.out.println(jwtObject.getBoolean("isStudent"));

        if (jwtObject.getBoolean("isStudent")) {
            isStudent = true;
        }

        if (jwtObject.getBoolean("isTutor")) {
            isTutor = true;
        }

        initComponents();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        if (isStudent) {
            isStudentBtn.setVisible(true);
        }

        if (isTutor) {
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
