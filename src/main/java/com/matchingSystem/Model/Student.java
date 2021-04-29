package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Constant;
import org.json.JSONArray;
import org.json.JSONObject;

public class Student extends User {
    protected Bid initiatedBid = null;

    public Student(String id, String givenName, String familyName, String userName) {
        super(id, givenName, familyName, userName);
    }

    public Student(){
        super();
    }

    /**
     * Student can post a bid by calling Bid.create() api
     * @param bidType   type of the bid, "open" or "close"
     * @param subjectId the subject id of the bid
     * @param addInfo   lesson info of the bid
     */
    public void postBid(String bidType, String subjectId, JSONObject addInfo) {
        StringBuilder jsonParams = APIFacade.getBidAPI().parseToJsonForCreate(bidType, getId(), subjectId, addInfo);
        APIFacade.getBidAPI().create(jsonParams);
    }

    /**
     * Store student's initiated bid if there exists.
     */
    public void setInitiatedBid() {
        JSONObject response = (JSONObject) APIFacade.getUserAPI().getById(getId(), Constant.INITIATEDBIDS);
        JSONArray arr = response.getJSONArray("initiatedBids");

        if (arr.length() != 0) {

            BidFactory bidFactory = new BidFactory();
            this.initiatedBid = bidFactory.createBid(arr.getJSONObject(0));

        }
    }

    public Bid getInitiatedBid() {
        return initiatedBid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "bid=" + initiatedBid +
                ", id='" + id + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", userName='" + userName + '\'' +
                ", competencies=" + competencies +
                ", qualifications=" + qualifications +
                ", contracts=" + contracts +
                '}';
    }
}
