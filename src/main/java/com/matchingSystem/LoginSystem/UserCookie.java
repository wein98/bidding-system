package com.matchingSystem.LoginSystem;

import com.matchingSystem.Constant;
import com.matchingSystem.Utility;
import org.json.JSONObject;

public class UserCookie {
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

    // Function called to set usercookie
    public static void init(int _userType) {
        // decode jwt
        JSONObject userObj = Utility.decodeJWT(jwtToken);

        // reconstruct user from decoded jwt
        String userInfo = userObj.toString();
        userInfo = userInfo.replace("sub", "id");
        userInfo = userInfo.replace("username", "userName");

        user = userFactory.createUser(userInfo, _userType);
        if (_userType == Constant.IS_STUDENT) {
            userType = Constant.IS_STUDENT;
            ((Student) user).setInitiatedBid();
        } else if (_userType == Constant.IS_TUTOR) {
            userType = Constant.IS_TUTOR;
        }

        user.setCompetencies();
        user.setQualifications();
        user.setContracts();
    }

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
