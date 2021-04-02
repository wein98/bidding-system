package com.matchingSystem;

import java.util.ArrayList;

public abstract class User {
    private String id;
    private String givenName;
    private String familyName;
    private String userName;
    private ArrayList<Competency> competencies;
    private ArrayList<Qualification> qualifications;

    public User(String id, String givenName, String familyName, String userName) {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
        this.competencies = new ArrayList<Competency>();
        this.qualifications = new ArrayList<Qualification>();
    }

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

    public ArrayList<Qualification> getQualifications() {
        return qualifications;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
