package com.matchingSystem;

import com.matchingSystem.API.APIAdapters.ContractAPI;
import com.matchingSystem.API.ClientInterfaces.ContractAPIInterface;
import com.matchingSystem.Model.*;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserCookie {
    private static final ContractAPIInterface contractAPI = ContractAPI.getInstance();
    private static final UserFactory userFactory = new UserFactory();

    private static UserCookie ourInstance;
    private static int userType;
    public static String jwtToken = null;
    private static User user = null;

    public static UserCookie getInstance() {

        if (ourInstance == null) {
            ourInstance = new UserCookie();
        }
        return ourInstance;
    }

    private UserCookie() {}

    public static void init(int userType) {
        setUser(userType);
    }

    // Function called to set usercookie
    private static void setUser(int _userType) {
        // decode jwt
        JSONObject userObj = Utility.decodeJWT(jwtToken);

        // reconstruct user from decoded jwt
        String userInfo = userObj.toString();
        userInfo = userInfo.replace("sub", "id");
        userInfo = userInfo.replace("username", "userName");

        user = userFactory.createUser(userInfo, _userType);
        if (_userType == Constant.IS_STUDENT) {
            userType = Constant.IS_STUDENT;
            Student studentObj = (Student) user;
            studentObj.setInitiatedBid();
        } else if (_userType == Constant.IS_TUTOR) {
            userType = Constant.IS_TUTOR;
        }

        user.setCompetencies();
        user.setQualifications();
        user.setContracts();
    }

//    private static void setContracts() {
//        ArrayList<Contract> contractArr = (ArrayList<Contract>) contractAPI.getAll();
//
//        for (Contract c: contractArr) {
//            user.addContract(c);
//        }
//    }

    // return user object
    public static User getUser() {
        return user;
    }

    public static int getUserType() {
        return userType;
    }

    public static String getJwtToken() {
        return jwtToken;
    }

    public static void setJwtToken(String jwtcode) {
        jwtToken = jwtcode;
    }
}
