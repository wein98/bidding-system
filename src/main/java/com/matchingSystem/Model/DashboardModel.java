package com.matchingSystem.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final UserCookie userCookie = UserCookie.getInstance();
    private final UserAPIInterface userAPI = UserAPI.getInstance();


    private ArrayList<Contract> contractArrayList;
    private String username;
    private String userType;
    private String familyName;
    private String givenName;

    public ArrayList<Contract> getContractArrayList() {
        return contractArrayList;
    }

    public void setProfile() {

        // set userType
        if (userCookie.getUser() instanceof Student) {
            userType = "Student";
        } else if (userCookie.getUser() instanceof Tutor) {
            userType = "Tutor";
        } else {
            userType = "unknown";
        }

        // set username
        username = userCookie.getUser().getUserName();
        familyName = userCookie.getUser().getFamilyName();
        givenName = userCookie.getUser().getGivenName();

        setCompetencies();
        setQualifications();

        // notify observers
        setChanged();
        notifyObservers();
    }

    private void setCompetencies() {
        // Get list of competencies for this user
        JSONObject response = (JSONObject) userAPI.getById(userCookie.getUser().getId(), Constant.COMPETENCIES_SUBJECT_S);;
        JSONArray competencyArr = response.getJSONArray("competencies");

        // Update the list of competencies to usercookie
        if (competencyArr.length() != 0) {
            for (int i = 0; i < competencyArr.length(); i++) {
                try {
                    JSONObject competencyObj = competencyArr.getJSONObject(i);
                    userCookie.getUser().addCompetency(objMapper.readValue(competencyObj.toString(), Competency.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setQualifications() {
        // Get list of competencies for this user
        JSONObject response = (JSONObject) userAPI.getById(userCookie.getUser().getId(), Constant.QUALIFICATIONS_S);;
        JSONArray qualArr = response.getJSONArray("qualifications");

        // Update the list of qualifications to usercookie
        if (qualArr.length() != 0) {
            for (int i = 0; i < qualArr.length(); i++) {
                try {
                    JSONObject qualObj = qualArr.getJSONObject(i);
                    userCookie.getUser().addQualification(objMapper.readValue(qualObj.toString(), Qualification.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setContracts() {
        // TODO: filter out getAll contracts that matches current user and update to model.contractList
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
