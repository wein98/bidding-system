package com.matchingSystem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Poster {
    @JsonProperty("id")
    private String id;
    @JsonProperty("givenName")
    private String givenName;
    @JsonProperty("familyName")
    private String familyName;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("isStudent")
    private boolean isStudent;
    @JsonProperty("isTutor")
    private String isTutor;

    public String getGivenName() {
        return givenName;
    }

    @Override
    public String toString() {
        return "Given name: " + this.givenName + "\nFamily Name: " + this.familyName;
    }
}
