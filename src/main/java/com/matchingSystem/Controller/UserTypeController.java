package com.matchingSystem.Controller;

import com.matchingSystem.Constant;
import com.matchingSystem.Model.UserFactory;
import com.matchingSystem.View.DashboardView;
import com.matchingSystem.View.UserTypeView;
import com.matchingSystem.UserCookie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserTypeController {
    private UserTypeView view;

    public UserTypeController(UserTypeView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // IsStudentBtn OnClick listener
        view.getIsStudentBtn().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                UserCookie.getInstance().setUser(Constant.IS_STUDENT);
                userTypeSelected();
                view.dispose();
            }
        });

        // IsTutorBtn OnClick listener
        view.getIsTutorBtn().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("ASDASFwrdghWDGFSDGASRGsef");
                UserCookie.getInstance().setUser(Constant.IS_TUTOR);
                userTypeSelected();
                view.dispose();
            }
        });
    }

    private void userTypeSelected() {
        // TODO: create dashboard MVC here
        // Create dashboard model

        // Create dashboard view
        DashboardView dashboardView = new DashboardView();
        // Create dashboard controller
    }

}
