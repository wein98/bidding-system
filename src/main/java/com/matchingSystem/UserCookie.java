package com.matchingSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIAdapters.ContractAPI;
import com.matchingSystem.API.APIAdapters.UserAPI;
import com.matchingSystem.API.ClientInterfaces.ContractAPIInterface;
import com.matchingSystem.API.ClientInterfaces.UserAPIInterface;
import com.matchingSystem.Model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserCookie {
    private static final UserAPIInterface userAPI = UserAPI.getInstance();
    private static final ContractAPIInterface contractAPI = ContractAPI.getInstance();
    private static final ObjectMapper objMapper = new ObjectMapper();
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

    public static void init(int userType, String jwtCode) {
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

        System.out.println(userInfo);

        if (_userType == Constant.IS_STUDENT) {
            user = (Student) userFactory.createUser(userInfo, _userType);
            userType = Constant.IS_STUDENT;
        } else if (_userType == Constant.IS_TUTOR) {
            user = (Tutor) userFactory.createUser(userInfo, _userType);
            userType = Constant.IS_TUTOR;
        }

        setCompetencies();
        setQualifications();
        setContracts();
        setInitiatedBid();
    }

    private static void setCompetencies() {
        // Get list of competencies for this user
        JSONObject response = (JSONObject) userAPI.getById(UserCookie.getUser().getId(), Constant.COMPETENCIES_SUBJECT_S);;
        JSONArray competencyArr = response.getJSONArray("competencies");

        // Update the list of competencies to UserCookie
        if (competencyArr.length() != 0) {
            for (int i = 0; i < competencyArr.length(); i++) {
                try {
                    JSONObject competencyObj = competencyArr.getJSONObject(i);
                    user.addCompetency(objMapper.readValue(competencyObj.toString(), Competency.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void setQualifications() {
        // Get list of competencies for this user
        JSONObject response = (JSONObject) userAPI.getById(UserCookie.getUser().getId(), Constant.QUALIFICATIONS_S);;
        JSONArray qualArr = response.getJSONArray("qualifications");

        // Update the list of qualifications to UserCookie
        if (qualArr.length() != 0) {
            for (int i = 0; i < qualArr.length(); i++) {
                try {
                    JSONObject qualObj = qualArr.getJSONObject(i);
                    user.addQualification(objMapper.readValue(qualObj.toString(), Qualification.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
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
