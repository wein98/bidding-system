package com.matchingSystem.Controller;

import com.matchingSystem.Constant;
import com.matchingSystem.Model.DashboardModel;
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
                UserCookie.getInstance().setUser(Constant.IS_TUTOR);
                userTypeSelected();
                view.dispose();
            }
        });
    }

    private void userTypeSelected() {
        // Create dashboard model
        DashboardModel dashboardModel = new DashboardModel();
        // Create dashboard view
        DashboardView dashboardView = new DashboardView(dashboardModel);
        // Create dashboard controller
        DashboardController dashboardController = new DashboardController(dashboardView, dashboardModel);
    }

}
