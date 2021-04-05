package com.matchingSystem.Model;

import com.matchingSystem.Model.User;

public class Tutor extends User {
    public Tutor(String id, String givenName, String familyName, String userName) {
        super(id, givenName, familyName, userName);
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "id='" + id + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", userName='" + userName + '\'' +
                ", competencies=" + competencies +
                ", qualifications=" + qualifications +
                '}';
    }
}
