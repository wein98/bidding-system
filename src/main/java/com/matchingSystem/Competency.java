package com.matchingSystem;

public class Competency {
    private String id;
    private User owner;
    private Subject subject;
    private int level;

    public Competency(String id, User owner, Subject subject, int level) {
        this.id = id;
        this.owner = owner;
        this.subject = subject;
        this.level = level;
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
}
