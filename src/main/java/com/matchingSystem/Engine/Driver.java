package com.matchingSystem.Engine;

import com.matchingSystem.Controller.LoginController;
import com.matchingSystem.Model.LoginModel;
import com.matchingSystem.View.LoginView;

public class Driver {
    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
        LoginController loginController = new LoginController(loginView, loginModel);
    }
}
