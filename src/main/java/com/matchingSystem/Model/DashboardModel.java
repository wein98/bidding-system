package com.matchingSystem.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIAdapters.UserAPI;
import com.matchingSystem.API.ClientInterfaces.UserAPIInterface;
import com.matchingSystem.Constant;
import com.matchingSystem.UserCookie;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Observable;

public class DashboardModel extends Observable {
    private final ObjectMapper objMapper = new ObjectMapper();
    private final UserAPIInterface userAPI = UserAPI.getInstance();


    private ArrayList<Contract> contractArrayList;
    private User user;
    private String username;
    private String userType;
    private String familyName;
    private String givenName;
    private String testing;

    public ArrayList<Contract> getContractArrayList() {
        return contractArrayList;
    }

    public void setProfile() {

        // set userType
        if (UserCookie.getUserType() == Constant.IS_STUDENT) {
            userType = "Student";
        } else if (UserCookie.getUserType() == Constant.IS_TUTOR) {
            userType = "Tutor";
        } else {
            userType = "unknown";
        }

        user = UserCookie.getUser();

        // set username
        username = user.getUserName();
        familyName = user.getFamilyName();
        givenName = user.getGivenName();
        testing = user.getCompetencies().toString();

        // notify observers
        setChanged();
        notifyObservers();
    }

    private void setCompetencies() {
    }

    private void setQualifications() {
    }

    private void setBid() {
        JSONObject response = (JSONObject) userAPI.getById(UserCookie.getUser().getId(), Constant.INITIATEDBIDS_S);;
        JSONArray bidArr = response.getJSONArray("initiatedBids");
        // TODO: get bid object and store to student.bid for bid button
//        if (bidArr.length() != 0) {
//            try {
//                JSONObject qualObj = bidArr.getJSONObject(0);
//                if (UserCookie.getUserType() == Constant.IS_STUDENT) {
//                    Student userObj = (Student) user;
//                    userObj.setBid(objMapper.readValue(qualObj.toString(), Qualification.class));
//                }
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void setContracts() {
        // TODO: filter out getAll contracts that matches current user and update to model.contractList
    }

    public String getTesting() {
        return testing;
    }

    public String getUsername() {
        return username;
    }

    public String getUserType() {
        return userType;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }
}
