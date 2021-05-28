package com.matchingSystem.ContractDev;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.API.APIFacade;
import com.matchingSystem.BiddingSystem.Subject;
import com.matchingSystem.BiddingSystem.Poster;
import com.matchingSystem.LoginSystem.Tutor;
import com.matchingSystem.LoginSystem.UserCookie;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contract {
    @JsonProperty("id")
    private String id;
    @JsonProperty("firstParty")
    private Poster firstParty;
    @JsonProperty("secondParty")
    private Poster secondParty;
    @JsonProperty("subject")
    private Subject subject;
    @JsonProperty("dateCreated")
    private Timestamp dateCreated;
    @JsonProperty("expiryDate")
    private Timestamp expiryDate;
    @JsonProperty("paymentInfo")
    private JSONObject paymentInfo;
    @JsonProperty("lessonInfo")
    private JSONObject lessonInfo;
    @JsonProperty("additionalInfo")
    private JSONObject additionalInfo;
    private Timestamp dateSigned;

    public Contract () {}

    public Contract (Contract target) {
        if (target != null) {
            this.firstParty = target.firstParty;
            this.subject = target.subject;
            this.paymentInfo = target.paymentInfo;
            this.lessonInfo = target.lessonInfo;
            this.additionalInfo = target.additionalInfo;
        }
    }

    /**
     * Function that checks if this contract has expired
     * @return true if expired false otherwise.
     */
    public boolean isExpired() {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        int comparision = now.compareTo(expiryDate);

        if (comparision >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get a list of tutors that matches this subject and student's competency level
     * @return List of tutors
     */
    public ArrayList<Tutor> getTutors() {
        ArrayList<Tutor> tutors = new ArrayList<>();
        tutors = APIFacade.getAllTutorsByCompetencySubject(UserCookie.getUser().getCompetencyBySubject(getSubject()));

        return tutors;
    }

    /**
     * Get the id of this Contract
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Unpack the additional info json
     * @param addInfo additionalInfo json object
     */
    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackAdditionalInfo(Map<String,Object> addInfo) {
        if(addInfo.size() > 0) {
            this.additionalInfo = new JSONObject(addInfo);
        }else{
            this.additionalInfo = new JSONObject();
        }
    }

    /**
     * Unpack the lesson info json
     * @param lessonInfo lessonInfo json object
     */
    @SuppressWarnings("unchecked")
    @JsonProperty("lessonInfo")
    private void unpackLessonInfo(Map<String, Object> lessonInfo) {
        if(lessonInfo.size() > 0) {
            this.lessonInfo = new JSONObject(lessonInfo);
        }else{
            this.lessonInfo = new JSONObject();
        }
    }

    /**
     * Set the dateSigned of this Contract
     * @param dateTime the timestamp
     */
    public void setDateSigned(Timestamp dateTime){
        this.dateSigned = dateTime;
    }

    /**
     * Set the contract duration selected
     * @param months month signed
     */
    // the duration in additional info is the Contract Duration, the duration in lesson info is the session duration
    public void setContractDuration(String months){
        this.additionalInfo.put("duration",months);
    }

    public String getDateSigned() {
        if (dateSigned != null) {
            Date date = new Date();
            date.setTime(dateSigned.getTime());
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        } else {
            return "Contract not signed";
        }
    }

    public Poster getFirstParty() {
        return firstParty;
    }

    public Poster getSecondParty() {
        return secondParty;
    }

    public JSONObject getLessonInfo() {
        return this.lessonInfo;
    }

    public String getDayTime() {
        if(lessonInfo!=null) {
            String retVal = lessonInfo.getString("prefDay") + ",";
            retVal += lessonInfo.getString("time");
            retVal += lessonInfo.getString("dayNight");
            return retVal;
        }
        return null;
    }

    public JSONObject getAdditionalInfo() {
        return this.additionalInfo;
    }

    public String getTutorName() {
        return getSecondParty().getName();
    }

    public String getStudentName() {
        return getFirstParty().getName();
    }

    public String getTime() {
        return getLessonInfo().getString("time");
    }

    public String getDayNight() {
        return getLessonInfo().getString("dayNight");
    }

    public String getPrefDay() {
        return getLessonInfo().getString("prefDay");
    }

    public String getNumOfLesson() {
        return getLessonInfo().getString("numOfLesson");
    }

    public String getLessonDuration() {
        return getLessonInfo().getString("duration");
    }

    public String getRate() {
        return getAdditionalInfo().getString("rate");
    }

    public String getContractDuration() {
        return getAdditionalInfo().getString("duration");
    }

    public Timestamp getExpiryTimestamp() { return this.expiryDate; }
    public String getExpiryDate() {
        Date date = new Date();
        date.setTime(expiryDate.getTime());
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public String getStatus() {
        if (isExpired()) {
            return "Expired";
        } else if (dateSigned == null) {
            return "Pending to sign";
        } else {
            return "Active";
        }
    }

    public String getDateCreated() {
        return new SimpleDateFormat("dd/MM/yyyy").format(dateCreated);
    }

    public Subject getSubject() {
        return subject;
    }

    /**
     * Prototype design pattern to clone a Contract object
     * @return a cloned Contract
     */
    public Contract clone() {
        return new Contract(this);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id='" + id + '\'' +
                ", firstParty=" + firstParty +
                ", secondParty=" + secondParty +
                ", subject=" + subject +
                ", dateCreated=" + dateCreated +
                ", expiryDate=" + expiryDate +
                ", paymentInfo=" + paymentInfo +
                ", lessonInfo=" + lessonInfo +
                ", additionalInfo=" + additionalInfo +
                ", dateSigned=" + dateSigned +
                '}';
    }
}