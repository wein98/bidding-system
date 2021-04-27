package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Constant;
import com.matchingSystem.UserCookie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class User extends Observable {

    @JsonProperty("id")
    protected String id;

    @JsonProperty("givenName")
    protected String givenName;

    @JsonProperty("familyName")
    protected String familyName;

    @JsonProperty("userName")
    protected String userName;

    protected ArrayList<Competency> competencies = new ArrayList<Competency>();
    protected ArrayList<Qualification> qualifications = new ArrayList<Qualification>();
    protected ArrayList<Contract> contracts = new ArrayList<Contract>();

    protected ObjectMapper objectMapper = new ObjectMapper();

    public User(String id, String givenName, String familyName, String userName) {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
    }

    public User() {}

    public String getId() {
        return id;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setCompetencies() {
        JSONObject response = (JSONObject) APIFacade.getUserAPI().getById(this.getId(), Constant.COMPETENCIES_SUBJECT_S);
        JSONArray arr = response.getJSONArray("competencies");

        // Update the list of competencies to UserCookie
        if (arr.length() != 0) {
            for (int i = 0; i < arr.length(); i++) {
                try {
                    JSONObject obj = arr.getJSONObject(i);
                    addCompetency(objectMapper.readValue(obj.toString(), Competency.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<Competency> getCompetencies() {
        return competencies;
    }

    public void addCompetency(Competency c) {
        competencies.add(c);
    }

    public void setQualifications() {
        JSONObject response = (JSONObject) APIFacade.getUserAPI().getById(UserCookie.getUser().getId(), Constant.QUALIFICATIONS_S);
        JSONArray arr = response.getJSONArray("qualifications");

        // Update the list of qualifications to UserCookie
        if (arr.length() != 0) {
            for (int i = 0; i < arr.length(); i++) {
                try {
                    JSONObject obj = arr.getJSONObject(i);
                    addQualification(objectMapper.readValue(obj.toString(), Qualification.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<Qualification> getQualifications() {
        return qualifications;
    }

    public void addQualification(Qualification q) {
        qualifications.add(q);
    }

    public void setContracts() {}

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public void addContract(Contract c) {
        contracts.add(c);
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

}
