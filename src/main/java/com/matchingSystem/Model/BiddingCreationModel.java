package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.UserCookie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;

public class BiddingCreationModel extends Observable {

    private ArrayList<Subject> subjects;
    private ArrayList<Qualification> qualifications;
    private JSONObject initiator;

    public void updateDDL() {

        ArrayList<Competency> userCompetencies = UserCookie.getUser().getCompetencies();
        ArrayList<Subject> userSubjects = new ArrayList<>();
        for (Competency competency : userCompetencies) {
            userSubjects.add(competency.getSubject());
        }
        this.subjects = userSubjects;
        // TODO: change this dummy data
        this.qualifications = (ArrayList<Qualification>) APIFacade.getQualificationAPI().getAll();
        // notify observers
        setChanged();
        notifyObservers();
    }

    public ArrayList<Subject> getSubjects() {
        return this.subjects;
    }

    public ArrayList<Qualification> getQualifications() {
        return this.qualifications;
    }

    private String getSubjectId(int index) {
        return this.subjects.get(index).getId();
    }


    public void initiateBid(JSONObject jsonObj) {
        JSONObject additionalInfo = jsonObj.getJSONObject("additionalInfo");
        int competencyLevel = UserCookie.getUser().getCompetencies().get(jsonObj.getInt("subjectIndex")).getLevel();
        additionalInfo.put("competencyLevel", competencyLevel);
        additionalInfo.put("bidOffers",new JSONArray()); // list of bid offers

        Student studentObj = (Student) UserCookie.getUser();
        studentObj.postBid(
                jsonObj.getString("type"),
                getSubjectId(jsonObj.getInt("subjectIndex")),
                additionalInfo
        );
    }
}
