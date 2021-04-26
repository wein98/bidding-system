package com.matchingSystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.Poster;
import org.json.JSONObject;

import java.sql.Timestamp;
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
    public String getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("additionalInfo")
    private void unpackAdditionalInfo(Map<String,Object> addInfo) {
        this.additionalInfo = new JSONObject(addInfo);
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("lessonInfo")
    private void unpackLessonInfo(Map<String, Object> lessonInfo) {
        this.lessonInfo = new JSONObject(lessonInfo);

    }

//    public void printNested(){
//        System.out.println(this.additionalInfo.toString());
//        System.out.println(this.lessonInfo.toString());
//    }
    @Override
    public String toString() {
        return super.toString();
    }

    public void setDateSigned(Timestamp dateTime){
        this.dateSigned = dateTime;
    }
}