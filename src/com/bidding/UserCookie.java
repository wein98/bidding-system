package com.bidding;

public class UserCookie {
    private static UserCookie ourInstance;

    public static UserCookie getInstance() {

        if (ourInstance == null) {
            ourInstance = new UserCookie();
        }
        return ourInstance;
    }

    private String jwtToken = null;

    private UserCookie() {

    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
