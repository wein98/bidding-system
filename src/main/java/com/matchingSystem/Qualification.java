package com.matchingSystem;

public class Qualification {
    private String id;
    private String title;
    private String description;
    private boolean verified;
    private User owner;

    public Qualification(String id, String title, String description, boolean verified, User owner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.verified = verified;
        this.owner = owner;
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
}
