package com.matchingSystem.ContractDev;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.BiddingSystem.Subject;
import com.matchingSystem.BiddingSystem.Poster;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

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

    @Override
    public String toString() {
        return super.toString();
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

    public Timestamp getDateSigned() {
        return dateSigned;
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

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public String getDateCreated() {
        return new SimpleDateFormat("dd/MM/yyyy").format(dateCreated);
    }

    public Subject getSubject() {
        return subject;
    }
}