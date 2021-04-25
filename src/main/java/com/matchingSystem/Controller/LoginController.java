package com.matchingSystem.Controller;

import com.matchingSystem.Model.LoginModel;
import com.matchingSystem.View.LoginView;
import com.matchingSystem.View.UserTypeView;
import com.matchingSystem.UserCookie;
import org.json.JSONObject;

import javax.jws.soap.SOAPBinding;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView view;
    private LoginModel model;

    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {
        // Initialise action listeners in view to interpret user input.
        getView().getBtnLoginSubmit().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                getUserAction();
            }
        });
    }

    private void getUserAction() {
        JSONObject response = this.model.getUserLogin(getUsernameFromLogin(), getPasswordFromLogin());

        if (response.has("jwt")) {
            loginSuccess(response.getString("jwt"));
            this.view.dispose();
        } else {
            // TODO: prompt error message
        }
    }

    private void loginSuccess(String jwtCode) {
        // Create user cookie
        UserCookie userCookie = UserCookie.getInstance();
//        UserCookie.jwtToken = jwtCode;
        UserCookie.setJwtToken(jwtCode);
//        userCookie.setJwtToken(jwtCode);

        // Create userType view
        UserTypeView userTypeView = new UserTypeView();

        // Create userType controller
        UserTypeController userTypeController = new UserTypeController(userTypeView, jwtCode);
    }

    private String getUsernameFromLogin() {
        return getView().getTextFieldUsername().getText();
    }

    private String getPasswordFromLogin() {
        return String.valueOf(getView().getPasswordField().getPassword());
    }

    public LoginView getView() {
        return this.view;
    }

}
