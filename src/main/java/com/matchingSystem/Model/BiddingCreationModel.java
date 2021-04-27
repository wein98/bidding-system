package com.matchingSystem.Model;

import com.matchingSystem.API.APIAdapters.BidAPI;
import com.matchingSystem.API.APIAdapters.QualificationAPI;
import com.matchingSystem.API.APIAdapters.SubjectAPI;
import com.matchingSystem.API.ClientInterfaces.BidAPIInterface;
import com.matchingSystem.API.ClientInterfaces.QualificationAPIInterface;
import com.matchingSystem.API.ClientInterfaces.SubjectAPIInterface;
import com.matchingSystem.UserCookie;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;

public class BiddingCreationModel extends Observable {
    private final BidAPIInterface bidAPI = BidAPI.getInstance();
    private final SubjectAPIInterface subjectAPI = SubjectAPI.getInstance();
    private final QualificationAPIInterface qualificationAPI = QualificationAPI.getInstance();

    private ArrayList<Subject> subjects;
    private ArrayList<Qualification> qualifications;
    private JSONObject initiator;

    private static String[] timeVals = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
            "12"};
    private static String[] dayNight = {"AM", "PM"};
    private static String[] duration = {"1","1.5","2","2.5","3","3.5"};
    private static String[] numsForLesson = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private static String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday", "Sunday"};

    public void updateDDL() {

        String userId = UserCookie.getUser().getId();
        ArrayList<Competency> userCompetencies = UserCookie.getUser().getCompetencies();
        ArrayList<Subject> userSubjects = new ArrayList<>();
        for (Competency competency : userCompetencies) {
            userSubjects.add(competency.getSubject());
        }
        this.subjects = userSubjects;
        this.qualifications = (ArrayList<Qualification>) qualificationAPI.getAll();
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

    public static String[] getTimeVals() {
        return timeVals;
    }

    public static String[] getDayNight() {
        return dayNight;
    }

    public static String[] getDays() {
        return days;
    }

    public static String[] getNumsForLesson() {
        return numsForLesson;
    }

    public static String[] getDuration() {
        return duration;
    }
    public void initiateBid(JSONObject jsonObj) {
        JSONObject additionalInfo = jsonObj.getJSONObject("additionalInfo");
        int competencyLevel = UserCookie.getUser().getCompetencies().get(jsonObj.getInt("subjectIndex")).getLevel();
        additionalInfo.put("competencyLevel", competencyLevel);

        Student studentObj = (Student) UserCookie.getUser();
        studentObj.postBid(
                jsonObj.getString("type"),
                getSubjectId(jsonObj.getInt("subjectIndex")),
                additionalInfo
        );
    }
}
