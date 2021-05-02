package com.matchingSystem.BiddingSystem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.LoginSystem.Student;
import com.matchingSystem.LoginSystem.Tutor;
import com.matchingSystem.LoginSystem.User;

import java.util.Map;

public class Competency {
    @JsonProperty("id")
    private String id;

    @JsonProperty("owner")
    private User owner;

    @JsonProperty("subject")
    private Subject subject;

    @JsonProperty("level")
    private int level;

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

    public User getOwner() {
        return owner;
    }

    public Subject getSubject() {
        return subject;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Competency{" +
                "id='" + id + '\'' +
                ", owner=" + owner +
                ", subject=" + subject +
                ", level=" + level +
                '}';
    }
}
