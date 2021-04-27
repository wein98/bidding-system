package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    protected Bid initiatedBid;

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

    public ArrayList<Competency> getCompetencies() {
        return competencies;
    }

    public void addCompetency(Competency c) {
        competencies.add(c);
    }

    public ArrayList<Qualification> getQualifications() {
        return qualifications;
    }

    public void addQualification(Qualification q) {
        qualifications.add(q);
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public void addContract(Contract c) {
        contracts.add(c);
    }

    public Bid getInitiatedBid() {
        return initiatedBid;
    }

    public void setInitiatedBid(Bid initiatedBid) {
        this.initiatedBid = initiatedBid;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

}
