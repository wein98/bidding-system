package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchingSystem.API.APIFacade;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class User {

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

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return givenName + " " + familyName;
    }

    public void setCompetencies() {
        competencies = APIFacade.getUserCompetenciesById(getId());
    }

    public ArrayList<Competency> getCompetencies() {
        return competencies;
    }

    public void setQualifications() {
        qualifications = APIFacade.getUserQualificationsById(getId());
    }

    public ArrayList<Qualification> getQualifications() {
        return qualifications;
    }

    public void setContracts() {
        contracts = APIFacade.getContractsByUserId(getId());
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

}
