package com.matchingSystem.BiddingSystem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingSystem.LoginSystem.UserCookie;
import org.json.JSONArray;
import org.json.JSONObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LessonInfo {
    @JsonProperty("duration")
    private String lessonDuration;

    @JsonProperty("numOfLesson")
    private String numOfLesson;

    @JsonProperty("dayNight")
    private String dayNight;

    @JsonProperty("rate")
    private String rate;

    @JsonProperty("prefDay")
    private String prefDay;

    @JsonProperty("time")
    private String time;

    public LessonInfo() {};

    public LessonInfo(String lessonDuration, String numOfLesson, String dayNight, String prefDay, String time) {
        this.lessonDuration = lessonDuration;
        this.numOfLesson = numOfLesson;
        this.dayNight = dayNight;
        this.prefDay = prefDay;
        this.time = time;
    }

    public LessonInfo(String lessonDuration, String numOfLesson, String dayNight, String rate, String prefDay, String time) {
        this.lessonDuration = lessonDuration;
        this.numOfLesson = numOfLesson;
        this.dayNight = dayNight;
        this.rate = rate;
        this.prefDay = prefDay;
        this.time = time;
    }

    public String getLessonDuration() {
        return lessonDuration;
    }

    public String getNumOfLesson() {
        return numOfLesson;
    }

    public String getDayNight() {
        return dayNight;
    }

    public String getRate() {
        return rate;
    }

    public String getPrefDay() {
        return prefDay;
    }

    public String getTime() {
        return time;
    }

    public String getDayTime() {
        String retVal = getPrefDay() + ",";
        retVal += getTime();
        retVal += getDayNight();
        return retVal;

    }

    /**
     * Returns a JSON object of the class variables that is needed for creating bid.
     * @param subjectIndex index of the subject in the user's subject list
     * @param type type of this bid to create
     * @return JSON object of the parsed data
     */
    public JSONObject getBiddingCreationJSONObj(int subjectIndex, String type) {
        JSONObject retVal = new JSONObject();

        retVal.put("subjectIndex", subjectIndex);
        retVal.put("type", type);

        JSONObject additionalInfo = new JSONObject();
        additionalInfo = putLessonInfo(additionalInfo);

        int competencyLevel = UserCookie.getUser().getCompetencies().get(subjectIndex).getLevel();
        additionalInfo.put("competencyLevel", competencyLevel);
        additionalInfo.put("bidOffers",new JSONArray()); // list of bid offers

        retVal.put("additionalInfo", additionalInfo);

        return retVal;
    }

    /**
     * Returns a JSON object of the class variables that is needed for buying out a bid.
     * @param tutorId tutor's id who bought out this bid
     * @param compLvl tutor's competency lvl of the subject of this bid
     * @return JSON object of the parsed data
     */
    public JSONObject getBuyOutBidJSONObj(String tutorId, int compLvl) {
        JSONObject bidInfo = new JSONObject();
        bidInfo.put("offerTutorId", tutorId);
        bidInfo.put("tutorName", UserCookie.getUser().getFullName());
        bidInfo.put("tutorCompLvl",compLvl);

        return putLessonInfo(bidInfo);
    }

    /**
     * Put all the class variables into o.
     * @param o the JSONObject to carry the class variables
     * @return JSON object of the parsed data
     */
    public JSONObject putLessonInfo(JSONObject o) {
        o.put("duration", getLessonDuration());
        o.put("numOfLesson", getNumOfLesson());
        o.put("prefDay", getPrefDay());
        o.put("time", getTime());
        o.put("dayNight", getDayNight());
        o.put("rate", getRate());

        return o;
    }

    /**
     * Returns a JSON object of the class variables that is needed for contract.
     * @return JSON object of the parsed data
     */
    public JSONObject getContractLessonInfo() {
        JSONObject lessonInfo = new JSONObject();
        lessonInfo = putLessonInfo(lessonInfo);
        lessonInfo.remove("rate");

        return lessonInfo;
    }
}
