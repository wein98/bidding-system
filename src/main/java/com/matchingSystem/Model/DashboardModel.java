package com.matchingSystem.Model;

import com.matchingSystem.Constant;
import com.matchingSystem.ContractDev.Contract;
import com.matchingSystem.LoginSystem.Student;
import com.matchingSystem.LoginSystem.User;
import com.matchingSystem.LoginSystem.UserCookie;
import java.util.ArrayList;
import java.util.Observable;

public class DashboardModel extends Observable {

    private ArrayList<Contract> contractArrayList = new ArrayList<>();
    private User user;
    private String username;
    private String userType;
    private String fullName;

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
        fullName = user.getFullName();
        updateContractList();

        // notify observers
        setChanged();
        notifyObservers();
    }

    public void checkPostedBid() {
        ((Student) user).setInitiatedBid();
        setChanged();
        notifyObservers();
    }

    public void updateContractList() {
        user.setContracts();
        contractArrayList = user.getContracts();
        setChanged();
        notifyObservers();
    }

    public String getUsername() {
        return username;
    }

    public String getUserType() {
        return userType;
    }

    public String getFullName() {
        return fullName;
    }

    public int getBidType() {
        Student studentObj = (Student) user;
        if (studentObj.getInitiatedBid() != null) {
            if (studentObj.getInitiatedBid().getType().equals(Constant.OPENBID_S)) {
                return Constant.OPENBID;
            } else {
                return Constant.CLOSEBID;
            }
        }
        return 2;
    }
}
