package com.matchingSystem.BiddingSystem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    public String getName() {
        return givenName + ", " + familyName;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Given name: " + this.givenName + "\nFamily Name: " + this.familyName;
    }
}
