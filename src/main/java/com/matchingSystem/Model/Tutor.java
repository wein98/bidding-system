package com.matchingSystem.Model;

public class Tutor extends User {
    public Tutor(String id, String givenName, String familyName, String userName) {
        super(id, givenName, familyName, userName);
    }

    public Tutor() {
        super();
    }

    // TODO: student should also have the function to reply to a message that is sent by a tutor on a close bid

    public int getCompetencyLvlFromSubject(Subject s) {
        for (Competency c: competencies) {
            if (c.getSubject().equals(s)) {
                return c.getLevel();
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Tutor{" + "id='" + id + '\'' + ", givenName='" + givenName + '\'' + ", familyName='" + familyName + '\'' + ", userName='" + userName + '\'' + ", competencies=" + competencies + ", qualifications=" + qualifications + ", contracts=" + contracts + '}';
    }

}
