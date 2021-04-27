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
        setContracts();
    }

    private static void setContracts() {
        ArrayList<Contract> contractArr = (ArrayList<Contract>) contractAPI.getAll();

        for (Contract c: contractArr) {
            user.addContract(c);
        }
    }

    private static void setInitiatedBid() {
        // Get list of competencies for this user
        JSONObject response = (JSONObject) userAPI.getById(UserCookie.getUser().getId(), Constant.INITIATEDBIDS_S);
        JSONArray bidArr = response.getJSONArray("initiatedBids");

        // Update the list of qualifications to UserCookie
        if (bidArr.length() != 0) {
            JSONObject obj = bidArr.getJSONObject(0);
            BidFactory bidFactory = new BidFactory();
            Bid bid = bidFactory.createBid(obj.toString(),obj.getString("type"));
            user.setInitiatedBid(bid);
//            if (obj.getString("type").equals("open")) {
//                OpenBidFactory openBid = new OpenBidFactory();
//                user.setInitiatedBid(openBid.createBid(obj.toString()));
//            } else {
//                CloseBidFactory closeBid = new CloseBidFactory();
//                user.setInitiatedBid(closeBid.createBid(obj.toString()));
//            }
        }
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
