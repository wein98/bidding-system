package com.matchingSystem.Model;

import org.json.JSONObject;

public interface StudentActions {
    void setInitiatedBid();
    Bid getInitiatedBid();

    /**
     * Student can post a bid by calling Bid.create() api
     * @param bidType   type of the bid, "open" or "close"
     * @param subjectId the subject id of the bid
     * @param addInfo   lesson info of the bid
     */
    void postBid(String bidType, String subjectId, JSONObject addInfo);

}
