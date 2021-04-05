package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Qualification {
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("verified")
    private boolean verified;

    @JsonProperty("owner")
    private User owner;

    // mapping nested User object
    @SuppressWarnings("unchecked")
    @JsonProperty("owner")
    private void unpackNested(Map<String,Object> owner) {
        if ((boolean) owner.get("isStudent")) {
            this.owner = new Student(
                    (String) owner.get("id"),
                    (String) owner.get("givenName"),
                    (String) owner.get("familyName"),
                    (String) owner.get("userName"));
        } else {
            this.owner = new Tutor(
                    (String) owner.get("id"),
                    (String) owner.get("givenName"),
                    (String) owner.get("familyName"),
                    (String) owner.get("userName"));
        }
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isVerified() {
        return verified;
    }

    public User getOwner() {
        return owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "Qualification{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", verified=" + verified +
                ", owner=" + owner +
                '}';
    }
}
