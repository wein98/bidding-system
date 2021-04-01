package com.bidding;

import org.json.JSONObject;

import static com.bidding.APIManager.POSTRequest;

public class Login {

    private static UserCookie userCookie;

    // This function will call /user/login to login the user with username and password
    public static void userLogin(String username, String password) {
        String route = "/user/login?jwt=true";
        String param = String.format(
                "{\n" + "\"userName\": \"%s\",\r\n" +
                "\"password\": \"%s\"\r\n" + "}", username, password);
        String response = POSTRequest(route, param);
        JSONObject jsonObject = new JSONObject(response);

        userCookie = UserCookie.getInstance();
        userCookie.setJwtToken(jsonObject.getString("jwt"));

//        System.out.println(jsonObject.getString("jwt"));
    }
}
