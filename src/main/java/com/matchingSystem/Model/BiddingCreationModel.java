package com.matchingSystem.Model;

import com.matchingSystem.API.APIAdapters.BidAPI;
import com.matchingSystem.UserCookie;
import org.json.JSONObject;

import java.sql.Timestamp;

public class BiddingCreationModel {
    private BidAPI bidAPI = BidAPI.getInstance();

    User user = UserCookie.getInstance().getUser();

    public JSONObject createBid(JSONObject params){
        bidAPI.parseToJsonForCreate(
                params.getString("type"),
                user.getId(),
                )
    }

    private String getSubjectId(String subjectName)
}
