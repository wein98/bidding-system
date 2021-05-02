package com.matchingSystem.Controller;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.View.LoginView;
import com.matchingSystem.View.UserTypeView;
import com.matchingSystem.LoginSystem.UserCookie;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
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
        JSONObject response = APIFacade.userLogin(getUsernameFromLogin(), getPasswordFromLogin());

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
        UserCookie.setJwtToken(jwtCode);

        // Create userType view
        UserTypeView userTypeView = new UserTypeView();

        // Create userType controller
        new UserTypeController(userTypeView);
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
