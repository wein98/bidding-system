package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Constant;
import org.json.JSONArray;
import org.json.JSONObject;

public class Student extends User implements StudentActions {
    protected Bid initiatedBid = null;
    public Student(String id, String givenName, String familyName, String userName) {
        super(id, givenName, familyName, userName);
    }

    public Student(){
        super();
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

    @Override
    public Bid getInitiatedBid() {
        return initiatedBid;
    }

    @Override
    public void postBid(String bidType, String subjectId, JSONObject addInfo) {
        StringBuilder jsonParams = APIFacade.getBidAPI().parseToJsonForCreate(bidType, getId(), subjectId, addInfo);
        APIFacade.getBidAPI().create(jsonParams);
    }

    @Override
    public void setInitiatedBid() {
        JSONObject response = (JSONObject) APIFacade.getUserAPI().getById(getId(), Constant.INITIATEDBIDS_S);
        JSONArray arr = response.getJSONArray("initiatedBids");

        if (arr.length() != 0) {

            BidFactory bidFactory = new BidFactory();
            this.initiatedBid = bidFactory.createBid(arr.getJSONObject(0));

        }
    }

}
