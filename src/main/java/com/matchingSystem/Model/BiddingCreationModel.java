package com.matchingSystem.Model;

import com.matchingSystem.API.APIAdapters.BidAPI;
import com.matchingSystem.API.APIAdapters.QualificationAPI;
import com.matchingSystem.API.APIAdapters.SubjectAPI;
import com.matchingSystem.UserCookie;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;

public class BiddingCreationModel extends Observable {
    private BidAPI bidAPI = BidAPI.getInstance();
    private SubjectAPI subjectAPI = SubjectAPI.getInstance();
    private QualificationAPI qualificationAPI = QualificationAPI.getInstance();

    private ArrayList<Subject> subjects;
    private ArrayList<Qualification> qualifications;
    private JSONObject initiator;

    private static String[] timeVals = {"1","2","3","4","5","6","7","8","9","10","11","12"};
    private static String[] dayNight = {"AM","PM"};
    private static String[] numsForLesson = {"1","2","3","4","5","6","7","8","9","10"};
    private static String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    User user = UserCookie.getInstance().getUser();

    public void updateDDL(){

        this.subjects = subjectAPI.getAll();
        this.qualifications = qualificationAPI.getAll();
        // notify observers
        setChanged();
        notifyObservers();
    }

    public ArrayList<Subject> getSubjects(){
        return this.subjects;
    }

    public ArrayList<Qualification> getQualifications(){
        return this.qualifications;
    }

    private String getSubjectId(int index){
        return this.subjects.get(index).getId();
    }

    public static String[] getTimeVals() { return timeVals; }

    public static String[] getDayNight() { return dayNight; }

    public static String[] getDays() { return days; }

    public static String[] getNumsForLesson() { return numsForLesson; }

    public void initiateBid(JSONObject jsonObj){
        String type = jsonObj.getString("type");
        String initiatorId = user.getId();
        String subjectId = getSubjectId(jsonObj.getInt("subjectIndex"));
        JSONObject additionalInfo = jsonObj.getJSONObject("additionalInfo");
        StringBuilder jsonParams = bidAPI.parseToJsonForCreate(type, initiatorId, subjectId, additionalInfo);
        System.out.println("Get all params for bid creation");
        System.out.println(jsonParams.toString());
        bidAPI.create(jsonParams);
    }
}
