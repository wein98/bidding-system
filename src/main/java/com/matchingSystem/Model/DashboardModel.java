package com.matchingSystem.Model;

import com.matchingSystem.UserCookie;

import java.util.ArrayList;
import java.util.Observable;

public class DashboardModel extends Observable {
    private ArrayList<Contract> contractArrayList;
    private String username;
    private String userType;
    private String familyName;
    private String givenName;

//    private ArrayList<Observer> observers;

    public ArrayList<Contract> getContractArrayList() {
        return contractArrayList;
    }

    public void setProfile() {
        UserCookie userCookie = UserCookie.getInstance();

        // set userType
        if (userCookie.getUser() instanceof Student) {
            userType = "Student";
        } else if (userCookie.getUser() instanceof Tutor) {
            userType = "Tutor";
        } else {
            userType = "unknown";
        }

        // set username
        username = userCookie.getUser().getUserName();
        familyName = userCookie.getUser().getFamilyName();
        givenName = userCookie.getUser().getGivenName();

        // notify observers
        setChanged();
        notifyObservers();
    }

    public String getUsername() {
        return username;
    }

    public String getUserType() {
        return userType;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }
}
