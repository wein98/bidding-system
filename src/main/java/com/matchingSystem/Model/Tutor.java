package com.matchingSystem.Model;

import com.matchingSystem.API.APIFacade;
import com.matchingSystem.Constant;
import com.matchingSystem.Model.User;
import org.json.JSONArray;
import org.json.JSONObject;

public class Tutor extends User {
    public Tutor(String id, String givenName, String familyName, String userName) {
        super(id, givenName, familyName, userName);
    }

    public Tutor() {
        super();
    }

    @Override
    public String toString() {
        return "Tutor{" + "id='" + id + '\'' + ", givenName='" + givenName + '\'' + ", familyName='" + familyName + '\'' + ", userName='" + userName + '\'' + ", competencies=" + competencies + ", qualifications=" + qualifications + ", contracts=" + contracts + '}';
    }

    /**
     * Function to send an offer for Open Bidding
     * @param bidId
     * @param additionalObject
     */
    public void makeOfferToOpenBidding(String bidId, JSONObject additionalObject) {
        OpenBid bid = (OpenBid) APIFacade.getBidAPI().getById(bidId, Constant.NONE_S);
        JSONObject additionalInfo = bid.getAdditionalInfo();
        if (additionalInfo.has("openBidOffers")) {
            JSONArray bidOffersJson = additionalInfo.getJSONArray("openBidOffers");
            additionalInfo.remove("openBidOffers");
            bidOffersJson.put(additionalObject);
            additionalInfo.put("openBidOffers", bidOffersJson);
        } else {
            JSONArray bidOffersJson = new JSONArray();
            bidOffersJson.put(additionalObject);
            additionalInfo.put("openBidOffers", bidOffersJson);
        }
        StringBuilder params = APIFacade.getBidAPI().parseToJsonForPartialUpdate(additionalInfo);
//        APIFacade.getBidAPI().updatePartialById(bidId, params);
    }

    // TODO: student should also have the function to reply to a message that is sent by a tutor on a close bid

    /**
     * Function to send a message for Close Bidding
     * @param bidId
     * @param messageContent
     */
    public void sendMessage(String bidId, JSONObject messageContent) {

    }
}
