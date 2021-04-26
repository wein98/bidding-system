package com.matchingSystem.Model;

import com.matchingSystem.Constant;
import com.matchingSystem.UserCookie;
import java.util.ArrayList;
import java.util.Observable;

public class DashboardModel extends Observable {

    private ArrayList<Contract> contractArrayList = new ArrayList<>();
    private User user;
    private String username;
    private String userType;
    private String familyName;
    private String givenName;
    private String testing;

    public ArrayList<Contract> getContractArrayList() {
        return contractArrayList;
    }

    public void setProfile() {

        // set userType
        if (UserCookie.getUserType() == Constant.IS_STUDENT) {
            userType = "Student";
        } else if (UserCookie.getUserType()  == Constant.IS_TUTOR) {
            userType = "Tutor";
        } else {
            userType = "unknown";
        }

        user = UserCookie.getUser();

        // set username
        username = user.getUserName();
        familyName = user.getFamilyName();
        givenName = user.getGivenName();
        testing = user.getCompetencies().toString();

        setContractArrayList();

        // notify observers
        setChanged();
        notifyObservers();
    }

    private void setContractArrayList() {
        // TODO: filter out getAll contracts that matches current user and update to model.contractList
        contractArrayList = user.getContracts();
    }

    public String getTesting() {
        return testing;
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

    public int getBidType() {
        if (user.getInitiatedBid() != null) {
            if (user.getInitiatedBid().getType().equals(Constant.OPENBID_S)) {
                return Constant.OPENBID;
            } else {
                return Constant.CLOSEBID;
            }
        }
        return 2;
    }
}
