package com.matchingSystem.Controller;

import com.matchingSystem.Model.UserFactory;
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
                UserCookie.getInstance().setUser(UserFactory.IS_STUDENT);
                userTypeSelected();
            }
        });

        // IsTutorBtn OnClick listener
        view.getIsStudentBtn().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                UserCookie.getInstance().setUser(UserFactory.IS_TUTOR);
                userTypeSelected();
            }
        });
    }

    private void userTypeSelected() {
        // TODO: create dashboard MVC here
        // Create dashboard model

        // Create dashboard view

        // Create dashboard controller
    }

}
