package com.matchingSystem;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Subject {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;

}
