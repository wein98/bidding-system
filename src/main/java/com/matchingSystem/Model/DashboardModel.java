package com.matchingSystem.Model;

import com.matchingSystem.Constant;
import com.matchingSystem.ContractDev.ContractLayoutIterator;
import com.matchingSystem.LoginSystem.Student;
import com.matchingSystem.LoginSystem.User;
import com.matchingSystem.LoginSystem.UserCookie;
import java.util.Observable;

public class DashboardModel extends Observable {

    private User user;
    private String username;
    private String userType;
    private String fullName;
    private ContractLayoutIterator iterator;

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
        iterator = new ContractLayoutIterator();
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

    public ContractLayoutIterator getIterator() {
        return iterator;
    }
}
