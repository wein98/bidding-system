package com.matchingSystem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subject {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

//    public Subject(String id, String name, String description) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
