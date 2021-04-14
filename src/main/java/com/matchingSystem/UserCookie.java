package com.matchingSystem;

import com.matchingSystem.Model.Student;
import com.matchingSystem.Model.Tutor;
import com.matchingSystem.Model.User;
import com.matchingSystem.Model.UserFactory;
import org.json.JSONObject;

public class UserCookie {
    private static UserCookie ourInstance;
    UserFactory userFactory = new UserFactory();

    public static UserCookie getInstance() {

        if (ourInstance == null) {
            ourInstance = new UserCookie();
        }
        return ourInstance;
    }

    private String jwtToken = null;
    private User user = null;
//    private Student isStudent = null;
//    private Tutor isTutor = null;

    private UserCookie() {

    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
//        setUser();
    }

//    private void setUser() {
//        JSONObject userObj = Utility.decodeJWT(jwtToken);
//
//        // user is a Student
//        if (userObj.getBoolean("isStudent")) {
//            isStudent = new Student(
//                userObj.getString("sub"),
//                userObj.getString("givenName"),
//                userObj.getString("familyName"),
//                userObj.getString("username"));
//        }
//
//        // user is a Tutor
//        if (userObj.getBoolean("isTutor")) {
//            isTutor = new Tutor(
//                userObj.getString("sub"),
//                userObj.getString("givenName"),
//                userObj.getString("familyName"),
//                userObj.getString("username"));
//        }
//    }

    // Function called to set usercookie
    public void setUser(int userType) {
        // decode jwt
        JSONObject userObj = Utility.decodeJWT(jwtToken);

        // reconstruct user from decoded jwt
        String userInfo = userObj.toString();
        userInfo = userInfo.replace("sub", "id");
        userInfo = userInfo.replace("username", "userName");

        if (userType == Constant.IS_STUDENT) {
            user = (Student) userFactory.createUser(userInfo, userType);
        } else if (userType == Constant.IS_TUTOR) {
            user = (Tutor) userFactory.createUser(userInfo, userType);
        }

    }

    // get user as a Student
    public User getUser() {
        return user;
    }

    // get user as a Tutor
//    public User getTutor() {
//        return isTutor;
//    }

    public String getJwtToken() {
        return jwtToken;
    }
}
