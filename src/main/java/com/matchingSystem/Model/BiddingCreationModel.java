package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.BiddingSystem.Competency;
import com.matchingSystem.BiddingSystem.Subject;
import com.matchingSystem.LoginSystem.Qualification;
import com.matchingSystem.LoginSystem.Student;
import com.matchingSystem.LoginSystem.UserCookie;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;

public class BiddingCreationModel extends Observable {

    private ArrayList<Subject> subjects;
    private ArrayList<Qualification> qualifications;

    public void updateDDL() {

        ArrayList<Competency> userCompetencies = UserCookie.getUser().getCompetencies();
        ArrayList<Subject> userSubjects = new ArrayList<>();
        for (Competency competency : userCompetencies) {
            userSubjects.add(competency.getSubject());
        }
        this.subjects = userSubjects;
        this.qualifications = APIFacade.getAllQualifications();
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

    /**
     * initiate a new Bid
     * @param jsonObj details of bid
     */
    public void initiateBid(JSONObject jsonObj) {
        Student studentObj = (Student) UserCookie.getUser();
        studentObj.postBid(
                jsonObj.getString("type"),
                getSubjectId(jsonObj.getInt("subjectIndex")),
                jsonObj.getJSONObject("additionalInfo")
        );
    }
}
