package com.matchingSystem.LoginSystem;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.BiddingSystem.Bid;
import org.json.JSONObject;

public class Student extends User {
    protected Bid initiatedBid = null; // the currently active bid

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
        initiatedBid = APIFacade.createBid(bidType, getId(), subjectId, addInfo);
    }

    /**
     * Store student's initiated bid if there exists.
     */
    public void setInitiatedBid() {
        System.out.println("Retrieving active bid...");
        Bid b = APIFacade.getUserInitiatedBidById(getId());
        if (b != null && !b.isExpired() &&b.getDateClosedDown() == null) {
            this.initiatedBid = b;
        }else{
            this.initiatedBid = null;
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
