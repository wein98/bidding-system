package com.matchingSystem.Model;

import com.matchingSystem.Model.User;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class Student extends User implements StudentActions {
    protected Bid bid = null;
    public Student(String id, String givenName, String familyName, String userName) {
        super(id, givenName, familyName, userName);
    }

    public Student(){
        super();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", userName='" + userName + '\'' +
                ", competencies=" + competencies +
                ", qualifications=" + qualifications +
                '}';
    }

    @Override
    public Bid getBid() {
        return null;
    }

    @Override
    public void postBid() {

    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }
}
