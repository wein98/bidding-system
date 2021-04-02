package com.matchingSystem;

import org.json.JSONObject;

public class UserCookie {
    private static UserCookie ourInstance;

    public static UserCookie getInstance() {

        if (ourInstance == null) {
            ourInstance = new UserCookie();
        }
        return ourInstance;
    }

    private String jwtToken = null;
    private Student isStudent = null;
    private Tutor isTutor = null;

    private UserCookie() {

    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
        setUser();
    }

    private void setUser() {
        JSONObject userObj = Utility.decodeJWT(jwtToken);

        // user is a Student
        if (userObj.getBoolean("isStudent")) {
            isStudent = new Student(
                userObj.getString("sub"),
                userObj.getString("givenName"),
                userObj.getString("familyName"),
                userObj.getString("username"));
        }

        // user is a Tutor
        if (userObj.getBoolean("isTutor")) {
            isTutor = new Tutor(
                userObj.getString("sub"),
                userObj.getString("givenName"),
                userObj.getString("familyName"),
                userObj.getString("username"));
        }
    }

    // get user as a Student
    public User getStudent() {
        return isStudent;
    }

    // get user as a Tutor
    public User getTutor() {
        return isTutor;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
